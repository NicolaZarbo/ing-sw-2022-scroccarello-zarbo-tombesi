package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.client.ClientMessage;
import it.polimi.ingsw.messages.server.ErrorMessageForClient;
import it.polimi.ingsw.messages.server.PlayerSetUpMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CLI.printers.*;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.objects.SimplifiedPlayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;





public class Cli implements UserInterface {

    private final CentralView game;
    private PrintWriter socketOut;
    private final InputManager inputManager;
    private final Scanner input;
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
        this.inputManager = new InputManager(this);
        game.addObserver(new MessagesFromViewHandler());
    }

    public CentralView getGame() {
        return game;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        System.out.println(TitlePrinter.print());
        Scanner socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        String socketLine="";
        try{
            socketLine = socketIn.nextLine();
            System.out.println(socketLine);
            String inputLine = input.nextLine();
            game.setName(inputLine);
            socketOut.println(inputLine);
            socketOut.flush();
            socketLine = socketIn.nextLine();
            System.out.println(socketLine);
            while (!socketLine.equals("connected to lobby")) {
                inputLine = input.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();
                socketLine = socketIn.nextLine();
                System.out.println(socketLine);
            }
            while (true){
                socketLine = socketIn.nextLine();
                game.update(MessageFactory.getMessageFromServer(socketLine));
                //System.out.println(socketLine);//print for fast checking of messages
                if(game.isYourTurn())
                    inputManager.decodeInput(input.nextLine());
            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the client side"+ e.getMessage());
        } finally {
            input.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }



    private class MessagesFromViewHandler implements Observer<ClientMessage> {
        @Override
        public void update(ClientMessage message) {
            socketOut.println(message.getJson());
            socketOut.flush();
        }
    }
    @Override
    public void showView() {
        System.out.println(GamePrinter.printGameTable(game));
    }

    @Override
    public void showHand() {
        SimplifiedPlayer player=game.getPersonalPlayer();
        System.out.println(Printer.PINK+"You can't use the cards n: "+game.getPlayedCardThisTurn()+Printer.RST);
        System.out.println(CardPrinter.print(player.getAssistantCards())+"\n coins :"+player.getCoin());
        System.out.println(Printer.PINK+"Select the card by its number"+Printer.RST);
    }

    @Override
    public void showCharacters() {
        System.out.println(CharacterPrinter.print(game));
    }

    @Override
    public void showOptionsForPersonalization(PlayerSetUpMessage message) {
        System.out.println(PersonalizationPrinter.printForColorsAndMages(message));

    }

    @Override
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
    }

    @Override
    public void askToMoveStudent() {
        System.out.println(Printer.PINK+"Choose a student in your Entrance by its color"+Printer.RST);
        System.out.println("Student moved : "+ Printer.WHITE_BKG+""+game.getStudentMoved()+""+Printer.RST);
    }

    @Override
    public void askToMoveMother() {
        System.out.println(Printer.PINK+"Chose a number of steps, max =" +(game.getCardYouPlayed()+2)/2+""+Printer.RST);
    }


    @Override
    public void showClouds() {
        System.out.println(CloudPrinter.print(game));
        System.out.println("Choose a cloud from which you will replenish your students ");
    }

    public void askWhereToMove(){
        System.out.println(Printer.PINK+"Choose a present island or type anything else to move into the DiningRoom"+Printer.RST);
        inputManager.decodeInput(input.nextLine());
    }

    /** shows the player an error message(client side) and waits for another input*/
    public void askToRetry(String error){
        System.out.println(error);
        inputManager.decodeInput(input.nextLine());
    }
}
