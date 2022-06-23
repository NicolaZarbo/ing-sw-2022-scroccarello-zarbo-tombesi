package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.clientmessages.ClientMessage;
import it.polimi.ingsw.messages.servermessages.ServerMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CentralView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**The handler of the connection with the server. It exchanges string messages with the server through the socket.*/
public class ServerConnection {
    private final PrintWriter socketOut;
    private final Scanner socketIn;
    private Scanner input;
    private final CentralView game;
    private final InputManager inputManager;
    public static String ip="127.0.0.1";
    public static int port=50000;
    private volatile boolean canWrite;

    /**It is used to connect to the server and to manage responses through an InputManager and a MessageHandler
     *  @param input a scanner object to read some strings needed for the lobby setup
     *  @param view the view of the client
     *  @param inputManager the handler of client input*/
    public ServerConnection( Scanner input, CentralView view, InputManager inputManager) throws IOException {
        if(!checkSocketOptions()){
            throw new IllegalArgumentException("bad connection arguments");
        }
        Socket socket = new Socket(ip, port);
        inputManager.printToScreen("Connection established");
        socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        this.inputManager=inputManager;
        canWrite= !(inputManager instanceof GuiInputManager);
        this.game=view;
        this.input=input;
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
        String socketLine="";
        try {
            socketLine = socketIn.nextLine();
            inputManager.printToScreen(socketLine);
            String inputLine = input.nextLine();
            game.setName(inputLine);
            socketOut.println(inputLine);
            socketOut.flush();
            socketLine = socketIn.nextLine();
            inputManager.printToScreen(socketLine);
            while (!socketLine.equals("connected to lobby") ) {
                while (!canWrite) {//fixme test with synchronized block
                    Thread.onSpinWait();
                }
                inputLine = input.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();
                socketLine = socketIn.nextLine();
                inputManager.printToScreen(socketLine);
            }
            while (true) {
                socketLine = socketIn.nextLine();
                ServerMessage message = MessageFactory.getMessageFromServer(socketLine);
                game.update(message);
                if (game.isYourTurn())
                    inputManager.decodeInput();
            }
        } catch (NoSuchElementException e) {
            inputManager.printToScreen("Connection closed from the client side" + e.getMessage());
        } finally {
            socketIn.close();
        }
    }

    /**It is used to close the socket connections.*/
    public void closeConnection(){
        socketOut.println("close_connection");
        socketOut.flush();
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
            socketOut.println(message.getJson());
            socketOut.flush();
        }
    }}
