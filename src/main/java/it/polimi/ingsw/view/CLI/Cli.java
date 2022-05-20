package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.client.ServerConnection;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.client.ClientMessage;
import it.polimi.ingsw.messages.server.PlayerSetUpMessage;
import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CLI.printers.*;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.objects.SimplifiedPlayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;





public class Cli implements UserInterface {

    private final CentralView game;
    private PrintWriter socketOut;
    private final InputManagerCli inputManagerCli;
    private boolean usedCharacter;
    private final Scanner input;
    private String ip="127.0.0.1";
    private int port=12345;

    public Cli(){
        this.game = new CentralView(this);
        this.input = new Scanner(System.in);
        this.inputManagerCli = new InputManagerCli(this);


    }

    public CentralView getGame() {
        return game;
    }

    public void run() throws IOException {
        ServerConnection connection = new ServerConnection(new Scanner(System.in), game, inputManagerCli);
        game.addObserver( connection.setMessageHandler());
        connection.run();
    }
    @Override
    public void showView() {
        System.out.println(GamePrinter.printGameTable(game));
    }

    @Override
    public void showHand() {
        SimplifiedPlayer player=game.getPersonalPlayer();
        List<Integer> played=game.getPlayedCardThisTurn().stream().map(s->s+1).toList();
        System.out.println(Printer.PINK+"You can't use the cards n: "+played+Printer.RST);
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
        if(!game.isEasy())
            System.out.println(Printer.WHITE_BKG+Printer.BLACK+"write C to get character selection"+Printer.RST);
        System.out.println(Printer.PINK+"Choose a student in your Entrance by its color"+Printer.RST);
        System.out.println("Student moved : "+ Printer.WHITE_BKG+""+game.getStudentMoved()+""+Printer.RST);
    }

    @Override
    public void askToMoveMother() {
        if(!game.isEasy())
            System.out.println(Printer.WHITE_BKG+Printer.BLACK+"write C to get character selection"+Printer.RST);
        System.out.println(Printer.PINK+"Chose a number of steps, max =" +(game.getCardYouPlayed()+2)/2+""+Printer.RST);
    }

    @Override
    public void showClouds() {
        System.out.println(CloudPrinter.print(game));
        System.out.println("Choose a cloud from which you will replenish your students ");
    }

    public void askWhereToMove(){
        System.out.println(Printer.PINK+"Choose a present island or type anything else to move into the DiningRoom"+Printer.RST);
        inputManagerCli.decodeStringInput(input.nextLine());
    }

    /** shows the player an error message(client side) and waits for another input*/
    public void askToRetry(String error){
        System.out.println(Printer.RED+error+Printer.RST);
        inputManagerCli.decodeStringInput(input.nextLine());
    }
}
