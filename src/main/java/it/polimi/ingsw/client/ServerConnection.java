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

public class ServerConnection {
    private final PrintWriter socketOut;
    private final Scanner socketIn;
    private Scanner input;
    private final CentralView game;
    private final InputManager inputManager;
    private final String ip="127.0.0.1";
    private final int port=12345;
    private volatile boolean canWrite;

    /** @param input a scanner object to read some string needed for the lobby setup*/
    public ServerConnection( Scanner input, CentralView view, InputManager inputManager) throws IOException {
        Socket socket = new Socket(ip, port);
        inputManager.printToScreen("Connection established");
        //(Printer.WHITE_BKG+ TitlePrinter.print()+Printer.RST+Printer.PINK);
        socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        this.inputManager=inputManager;
        if(inputManager instanceof GuiInputManager)
            canWrite=false;
        else canWrite=true;
        this.game=view;
        this.input=input;
    }
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
                while (!canWrite) {
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
            socketOut.close();
        }
    }
    public void writeTxtForLobby(InputStream rules){
        input = new Scanner(rules);
        canWrite=true;
    }
    public MessagesFromViewHandler setMessageHandler(){
        return new MessagesFromViewHandler();
    }

    public class MessagesFromViewHandler implements Observer<ClientMessage> {
        @Override
        public void update(ClientMessage message) {
            socketOut.println(message.getJson());
            socketOut.flush();
        }
    }}
