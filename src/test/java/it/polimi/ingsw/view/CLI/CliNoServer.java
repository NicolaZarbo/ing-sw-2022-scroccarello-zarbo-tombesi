package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.messages.server.ErrorMessageForClient;
import it.polimi.ingsw.messages.server.PlayerSetUpMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CLI.printers.*;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.objects.SimplifiedPlayer;

import java.io.IOException;
import java.util.Scanner;

public class CliNoServer extends Cli implements Observer<ErrorMessageForClient> {
    private final CentralView game;
    private final InputManager inputManager;
    private final Scanner input;
    public int nPlayer;
    boolean easy;


    public CentralView getView(){
        return game;
    }
    public void setUpGameAlone(){
        System.out.println("Yo name?");
        String in = input.nextLine().toLowerCase();
        game.setName(in);
        System.out.println("play alone");
        nPlayer = 1;
        System.out.println("easy? y/n");
        easy = input.nextLine().equals("y");
    }


    public CliNoServer(){
        game= new CentralView(this);
        inputManager= new InputManager(this);
        input= new Scanner(System.in);
    }
    @Override
    public void run() throws IOException {

    }

    @Override
    public void showView() {
        System.out.println(GamePrinter.printGameTable(game));
        inputManager.decodeStringInput(input.nextLine());
    }

    @Override
    public void showHand() {
        SimplifiedPlayer player=game.getPersonalPlayer();
        System.out.println(Printer.PINK+"You can't use the cards n: "+game.getPlayedCardThisTurn()+Printer.RST);
        System.out.println(CardPrinter.print(player.getAssistantCards())+"\n coins :"+player.getCoin());
        System.out.println(Printer.PINK+"Select the card by its number"+Printer.RST);
        inputManager.decodeStringInput(input.nextLine());
    }

    @Override
    public void showCharacters() {
        System.out.println(CharacterPrinter.print(game));
        inputManager.decodeStringInput(input.nextLine());
    }


    public void showOptionsForPersonalization(PlayerSetUpMessage message) {
        System.out.println(PersonalizationPrinter.printForColorsAndMages(message));
        inputManager.decodeStringInput(input.nextLine());
    }

    @Override
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
        inputManager.decodeStringInput(input.nextLine());
    }

    @Override
    public void askToMoveStudent() {
        super.askToMoveStudent();
        inputManager.decodeStringInput(input.nextLine());
    }

    @Override
    public void askToMoveMother() {
        System.out.println(Printer.PINK+"Chose a number of steps, max =" +(game.getCardYouPlayed()+2)/2+""+Printer.RST);
        inputManager.decodeStringInput(input.nextLine());
    }

    @Override
    public void showClouds() {
        System.out.println(CloudPrinter.print(game));
        inputManager.decodeStringInput(input.nextLine());
    }

    @Override
    public void askWhereToMove() {
        super.askWhereToMove();
    }

    @Override
    public void askToRetry(String error) {
        super.askToRetry(error);
    }

    @Override
    public void update(ErrorMessageForClient message) {
        System.out.println(message.getErrorInfo());
    }
}
