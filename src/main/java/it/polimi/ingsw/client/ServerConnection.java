package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.client.ClientMessage;
import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CLI.printers.Printer;
import it.polimi.ingsw.view.CLI.printers.TitlePrinter;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.InputManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ServerConnection {
    private final PrintWriter socketOut;
    private final Scanner socketIn;
    private final Scanner input;
    private final CentralView game;
    private final InputManager inputManager;
    private final String ip="127.0.0.1";
    private final int port=12345;

    /** @param input a scanner object to read some string needed for the lobby setup*/
    public ServerConnection( Scanner input, CentralView view, InputManager inputManager) throws IOException {
        Socket socket = new Socket(ip, port);
        inputManager.printToScreen("Connection established");
        //(Printer.WHITE_BKG+ TitlePrinter.print()+Printer.RST+Printer.PINK);
        socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        this.inputManager=inputManager;
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
            while (!socketLine.equals("connected to lobby")) {
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
