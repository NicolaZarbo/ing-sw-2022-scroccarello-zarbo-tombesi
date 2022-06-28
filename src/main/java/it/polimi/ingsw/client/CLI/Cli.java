package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.printers.*;
import it.polimi.ingsw.client.ServerConnection;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.exceptions.TimeOutConnectionException;
import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**The Command Line Interface. It shows the game in pixel art style, and lets the gamer play by sending text commands.*/
public class Cli implements UserInterface {
    public static final String BKG= "";
    public static final String WRD= Printer.BR_PINK;
    public static final String IMP=Printer.CYAN;
    public static final String RST=Printer.RST+BKG;
    public static final String CLR="\u001B[2J";

    private final CentralView game;
    private final InputManagerCli inputManagerCli;
    private boolean usedCharacter;
    private final Scanner input;
    private ServerConnection connection;

    /**It builds the Command Line Interface of the client.*/
    public Cli(){
        this.game = new CentralView(this);
        this.input = new Scanner(System.in);
        this.inputManagerCli = new InputManagerCli(this);
    }

    /**@return the central view of the client*/
    public CentralView getGame() {
        return game;
    }


    /**It runs the game on the Command Line Interface.
     * @exception IOException if some I/O errors occur*/
    public void run() throws IOException {
        PrintWriter printer= new PrintWriter(System.out,true);
        inputManagerCli.printToScreen(BKG);
        printer.println(WRD+ TitlePrinter.print()+RST);

        connection = new ServerConnection(new Scanner(System.in), game, inputManagerCli);
        game.addObserver( connection.setMessageHandler());
        try {
            connection.run();
        }catch (MessageErrorException mes) {
            showError(mes.getMessage());
        }catch (TimeOutConnectionException timeoutException){
            showError("Server Connection lost");
        }
    }

    @Override
    public void refresh() {
        if(game.getState()== GameState.setupPlayers)
            return;
        showView();
    }

    @Override
    public void showView() {
        System.out.println(GamePrinter.printGameTable(game));
    }

    @Override
    public void showHand() {
        showView();
        SimplifiedPlayer player=game.getPersonalPlayer();
        List<Integer> played=new ArrayList<>();
        for (Integer playerID:game.getPlayers().stream().map(SimplifiedPlayer::getId).toList()) {
            if(game.getPlayedCardThisTurnByPlayerId(playerID)!=null)
                played.add(game.getPlayedCardThisTurnByPlayerId(playerID)+1);
        }
        if(played.size()>0)
            System.out.println(IMP+"\n\nYou can't use the cards n: "+played+RST);
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
                System.out.println(Printer.WHITE_BKG + Printer.BLACK + "\nwrite C to get character selection" + RST);
            else System.out.println(Printer.WHITE_BKG + Printer.BLACK + "Character "+game.getActiveCharacter()+" active" + RST);
        }
        System.out.println(IMP+"Choose a student in your Entrance by its color"+RST);
        System.out.println("Student moved : "+ Printer.DR_GRAY_BKG+" "+game.getStudentMoved()+" "+RST);
    }

    @Override
    public void askToMoveMother() {
        showView();
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
        showView();
        System.out.println(CloudPrinter.print(game));
        System.out.println(IMP+"Choose a cloud from which you will replenish your students "+RST);
    }

    @Override
    public void showBoards() {
        System.out.println(BoardsPrinter.print(game));
    }

    @Override
    public void showIslands() {
        System.out.println(IslandsPrinter.print(game));
    }

    @Override
    public void gameOver() {
        System.out.println(GameOverPrinter.print(game));
    }

    public void askWhereToMove(){
        System.out.println(IMP+"Choose a present island or type anything else to move into the DiningRoom"+RST);
        inputManagerCli.decodeStringInput(input.nextLine());
    }

    /**It shows to the player a client side error message and waits for another input.
     * @param error the text error to show*/
    public void askToRetry(String error){
        System.out.println(Printer.RED+error+RST);
        inputManagerCli.decodeStringInput(input.nextLine());
    }

    /**It closes the connection client side and shows to the player the last messages, then it ends the application.*/
    public void close(){
        System.out.println("Closing the game");
        connection.closeConnection();
        System.out.println("Goodbye");
        System.exit(0);
    }
}
