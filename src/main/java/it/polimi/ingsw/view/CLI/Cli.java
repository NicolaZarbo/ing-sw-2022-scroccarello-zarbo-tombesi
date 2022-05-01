package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.messages.MassageManager;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.client.ClientMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CLI.printers.GamePrinter;
import it.polimi.ingsw.view.CLI.printers.TitlePrinter;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;




public class Cli implements UserInterface {

    private CentralView game;
    private PrintWriter socketOut;
    private InputManager inputManager;
    private  Scanner input;
    private  Printer printer;
    private MassageManager massageManager;
    private String ip="127.0.0.1";
    private int port=12345;

    //Turn state is an integer used to represent the state of the game, here are the meaning of the values:
// 0: pre game
// 1: initialisation of the game
// 2: player's turn
// 3: another player's turn
    private int turnState;

    public Cli(){
        this.game = new CentralView(this);
        this.input = new Scanner(System.in);
        this.printer = new Printer();
        this.inputManager = new InputManager(game);
    }
    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        System.out.println(TitlePrinter.print());
        Scanner socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        String socketLine="";
        try{
            socketLine = socketIn.nextLine();
            System.out.println(socketLine);
            while (!socketLine.equals("connected to lobby")) {
                String inputLine = stdin.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();
                socketLine = socketIn.nextLine();
                System.out.println(socketLine);
            }
            while (true){
                socketLine = socketIn.nextLine();
                game.update(MessageFactory.getMessageFromServer(socketLine));
                //System.out.println(socketLine);//print for fast checking of messages
                String inputLine = stdin.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();

            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the client side"+ e.getMessage());
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

    private class MessagesFromViewHandler implements Observer<ClientMessage> {
        @Override
        public void update(ClientMessage message) {
            socketOut.println(message.getJson());
        }
    }
    @Override
    public void showView() {
        System.out.println(GamePrinter.printGameTable(game));
    }

    @Override
    public void showHand() {

    }

    @Override
    public void showCharacters() {

    }

    @Override
    public void showOptionsForPersonalization() {

    }
}
