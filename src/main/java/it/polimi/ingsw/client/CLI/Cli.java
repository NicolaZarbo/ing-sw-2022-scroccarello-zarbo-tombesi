package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.printers.*;
import it.polimi.ingsw.client.ServerConnection;
import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.objects.SimplifiedPlayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;





public class Cli implements UserInterface {
    public static final String BKG= "";
    public static final String WRD= Printer.BR_PINK;
    public static final String IMP=Printer.CYAN;
    public static final String RST=Printer.RST+BKG;
    public static final String CLR="\u001B[2J";

    private final CentralView game;
    private PrintWriter socketOut;
    private final InputManagerCli inputManagerCli;
    private boolean usedCharacter;
    private final Scanner input;

    public Cli(){
        this.game = new CentralView(this);
        this.input = new Scanner(System.in);
        this.inputManagerCli = new InputManagerCli(this);
    }

    public CentralView getGame() {
        return game;
    }

    public void run() throws IOException {
        PrintWriter printer= new PrintWriter(System.out,true);
        inputManagerCli.printToScreen(BKG);
        printer.println(WRD+ TitlePrinter.print()+RST);
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
        System.out.println(IMP+"You can't use the cards n: "+played+RST);
        System.out.println(CardPrinter.print(player.getAssistantCards())+"\n coins :"+player.getCoin());
        System.out.println(IMP+"Select the card by its number"+RST);
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
        System.out.println(Printer.RED+errorMessage+RST);
    }

    @Override
    public void askToMoveStudent() {
        showView();
        if(!game.isEasy()) {
            if(game.getActiveCharacter()==0)
                System.out.println(Printer.WHITE_BKG + Printer.BLACK + "write C to get character selection" + RST);
            else System.out.println(Printer.WHITE_BKG + Printer.BLACK + "Character "+game.getActiveCharacter()+" active" + RST);
        }
        System.out.println(IMP+"Choose a student in your Entrance by its color"+RST);
        System.out.println("Student moved : "+ Printer.DR_GRAY_BKG+" "+game.getStudentMoved()+" "+RST);
    }

    @Override
    public void askToMoveMother() {
        System.out.println(IslandsPrinter.print(game));
        if(!game.isEasy()) {
            if(game.getActiveCharacter()==0)
                System.out.println(Printer.WHITE_BKG + Printer.BLACK + "write C to get character selection" + RST);
            else System.out.println(Printer.WHITE_BKG + Printer.BLACK + "Character "+game.getActiveCharacter()+" active" + RST);
        }
        System.out.println(IMP+"Chose a number of steps, max =" +(game.getCardYouPlayed()+2)/2+""+RST);
    }

    @Override
    public void showClouds() {
        System.out.println(CloudPrinter.print(game));
        System.out.println(IMP+"Choose a cloud from which you will replenish your students "+RST);
    }

    public void askWhereToMove(){
        System.out.println(IMP+"Choose a present island or type anything else to move into the DiningRoom"+RST);
        inputManagerCli.decodeStringInput(input.nextLine());
    }

    /** shows the player an error message(client side) and waits for another input*/
    public void askToRetry(String error){
        System.out.println(Printer.RED+error+RST);
        inputManagerCli.decodeStringInput(input.nextLine());
    }
}
