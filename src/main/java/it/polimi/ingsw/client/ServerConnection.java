package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.exceptions.TimeOutConnectionException;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.clientmessages.ClientMessage;
import it.polimi.ingsw.messages.servermessages.ServerMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CentralView;

import javax.naming.TimeLimitExceededException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**The handler of the connection with the server. It exchanges string messages with the server through the socket.*/
public class ServerConnection {
    private final PrintWriter socketOut;
    private Scanner input;
    private final CentralView view;
    private final InputManager inputManager;

    public static String ip="127.0.0.1";
    public static int port=50000;
    private  boolean canWrite;
    private final Socket socket;

    /**It is used to connect to the server and to manage responses through an InputManager and a MessageHandler
     *  @param input a scanner object to read some strings needed for the lobby setup
     *  @param view the view of the client
     *  @param inputManager the handler of client input*/
    public ServerConnection( Scanner input, CentralView view, InputManager inputManager) throws IOException {
        if(!checkSocketOptions()){
            throw new IllegalArgumentException("bad connection arguments");
        }
        socket = new Socket(ip, port);
        inputManager.printToScreen("Connection established");
        socketOut = new PrintWriter(socket.getOutputStream());
        this.inputManager=inputManager;
        canWrite= !(inputManager instanceof GuiInputManager);
        this.view =view;
        this.input=input;
        socket.setSoTimeout(1000*50);
    }

    /**It is used to control if the port and ip chosen are acceptable.*/
    private boolean checkSocketOptions() {
        if (port < 65536 && port>49152)
            return true;
        String[] ipv4St = ip.split("\\.", 4);
        int[] ipv4 = new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                ipv4[i] = Integer.parseInt(ipv4St[i]);
                if (ipv4[i] < 0 || ipv4[i] > 254) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }

        }
        return true;
    }

    /** It starts using the socket connection to read and send messages,
     *  received messages are handled by the input manager and
     *  the sending of messages is done through the observers of ClientMessage of MessageHandler.*/
    public void run() {
        Thread pinger =new Thread(this::ping);
        pinger.start();
        String socketLine="";
        try {
            socketLine = readFromServer();
            inputManager.printToScreen(socketLine);
            String inputLine = input.nextLine();
            view.setName(inputLine.toLowerCase());
            sendToServer(inputLine);
            socketLine = readFromServer();
            inputManager.printToScreen(socketLine);
            if(!socketLine.equals("connected to lobby"))
                lobbyHandler(socketLine);
            while (true) {
                socketLine = readFromServer();
                ServerMessage message = MessageFactory.getMessageFromServer(socketLine);
                view.update(message);
                if (view.isYourTurn())
                    inputManager.decodeInput();
            }
        } catch (NoSuchElementException e) {
            inputManager.printToScreen("Connection closed from the client side" + e.getMessage());
        }
        finally {
            canWrite=false;
        }
    }
    private void lobbyHandler(String sockLine){
        String read=sockLine;
        String inputLine;
        while (!read.equals("connected to lobby") ) {
            while (!canWrite) {
                Thread.onSpinWait();
            }
            inputLine = input.nextLine();
            sendToServer(inputLine);
            read = readFromServer();
            inputManager.printToScreen(read);
        }
    }
    /** Used by readFromServer()
     * Necessary to notice disconnections through timeout*/
    private String readSocketIn(){
        StringBuilder builder= new StringBuilder();
        int read=2;
        try {
            read=  socket.getInputStream().read();
            while((char)read!='\n'){
                builder.append((char)read);
                read= socket.getInputStream().read();
            }
        }catch (SocketTimeoutException timeoutException){
            throw new TimeOutConnectionException();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
        String out=builder.toString();
        if(read==0 || read==-1) {
            closeConnection();
            throw new TimeOutConnectionException();
        }
            return out.substring(0, out.length() - 1);
    }
    /** Used to read a string from the server*/
    private String readFromServer(){
        String ret;
        ret=readSocketIn();
        while (ret.equalsIgnoreCase("pong")) {
            ret = readSocketIn();
        }
        return ret;
    }
    private synchronized void sendToServer(String out){
        socketOut.println(out);
        socketOut.flush();
    }
    public void ping(){
        while (true){
            try {
                Thread.sleep(40*1000);
                sendToServer("ping");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**It is used to close the socket connections.*/
    public void closeConnection(){
        sendToServer("close_connection");
        socketOut.close();
        System.out.println("Connection Closed");
    }

    /**It is used to modify the scanner used at the start of the connection when creating a lobby (it is
     * used only for GUI before the creation of the game).
     * @param rules string values containing the parameters the server needs to create the lobby*/
    public void writeTxtForLobby(InputStream rules){
        input = new Scanner(rules);
        canWrite=true;
    }

    /**It is used when setting the message handler as the observer of the central view.*/
    public MessagesFromViewHandler setMessageHandler(){
        return new MessagesFromViewHandler();
    }

    /**The handler of messages that is notified by the central view when it needs to send to the server a message.*/
    public class MessagesFromViewHandler implements Observer<ClientMessage> {
        @Override
        public void update(ClientMessage message) {
            sendToServer(message.getJson());
        }
    }}
