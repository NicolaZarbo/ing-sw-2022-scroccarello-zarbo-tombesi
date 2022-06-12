package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.printers.*;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.client.InputManager;


import java.util.Locale;
import java.util.Scanner;

public class InputManagerCli  extends InputManager {

    private final CentralView game;
    private final Cli cli;
    private boolean somethingSelected;
    private int targetColor;
    private final CharacterInputManager characterInputManager;
    private boolean isInteger;
    private int inputInteger;
    private String[] multipleInput;

    /** Decodes an input string from the user into a meaningful action on the view*/
    public void decodeStringInput(String input){
        if(!game.isYourTurn() )
            return;
        multipleInput=input.toLowerCase().split("\s");
        if(multipleInput[0].equalsIgnoreCase("show") && game.getState()!= GameState.setupPlayers) {
            showManager();
            return;
        }
        try{
            inputInteger=Integer.parseInt(multipleInput[0]);
            isInteger=true;
        }
        catch (NumberFormatException e){
            isInteger=false;
            inputInteger=-1;
        }
        stateSwitch(game.getState());
    }
    /** switches the right manager based on the state of the game*/
    protected void stateSwitch(GameState state){
        switch (state){
            case setupPlayers -> caseSetupPlayers();
            case planPlayCard -> casePlanPlayCard();
            case actionMoveMother -> caseActionMoveMother();
            case actionMoveStudent -> caseActionMoveStudent();
            case actionChooseCloud -> caseActionChooseCloud();
        }
    }
    /**Used to manage the show command */
    private void showManager(){
        System.out.println("Select the on you need to see" +
                Cli.IMP+"\n Islands | Boards |  Cards |  Characters |  Clouds | Used Card | Active Effect"+ Cli.RST);
        String input=new Scanner(System.in).nextLine().toLowerCase();
        System.out.println(switch (input){
            case "active effect"->CharacterPrinter.printUsedCharacterEffect(game);
            case "used cards"-> UsedCardsPrinter.printUsedAssistant(game);
            case "islands"-> IslandsPrinter.print(game);
            case "boards"-> BoardsPrinter.print(game);
            case "cards"-> CardPrinter.print(game.getPersonalPlayer().getAssistantCards());
            case "characters"-> CharacterPrinter.print(game);
            case "clouds" -> CloudPrinter.print(game);
            default -> "not available";
        });
        System.out.println("press enter to return");
        new Scanner(System.in).nextLine();
        seeAction();
        decodeInput();
    }
    /**Shows the user contextual information based of the state of the game
     * Used to bring the player focus back to the task at hand*/
    public void seeAction() {
        if(!game.isYourTurn())
            return;
        switch (game.getState()) {
            case planPlayCard -> cli.showHand();
            case actionMoveMother -> cli.askToMoveMother();
            case actionMoveStudent -> cli.askToMoveStudent();
            case actionChooseCloud -> cli.showClouds();
        }
    }
    /** Used when the player choose to activate a character
     * @see CharacterInputManager class for more info on the
     * managing of text input into meaningful action*/
    public void charactersDecode(String input){
        int nChar;
        try{
            nChar=Integer.parseInt(input);
            characterInputManager.characterManagerCall(nChar);
        }catch (IllegalMoveException e){
            System.out.println(e.getMessage());
            seeAction();
            decodeInput();
        }catch (NumberFormatException e){
            cli.askToRetry("not a card");
        }catch(CharacterErrorException e){
            seeAction();
            decodeInput();
        }
    }
    /** Used to start decoding the user text commands from keyboard*/
    @Override
    public void decodeInput() {
        decodeStringInput(new Scanner(System.in).nextLine());
    }

    /**Used before the start of a game during lobby creation to show to the user informations*/
    @Override
    public void printToScreen(String string) {
        System.out.println(Cli.IMP+string+ Cli.RST);
     }
     /** After every command ensures the player that it was sent and that the server is processing a response*/
     private void showWait(){
        System.out.println(Printer.BLACK+Printer.BR_WHITE_BKG+"Please Wait...."+Printer.RST);
     }
    public void caseSetupPlayers() {
        if(multipleInput.length!=2){
            cli.askToRetry("please select a mage value followed by the initial of the color");
            return;
        }
        switch (multipleInput[1]) {
            case "black", "gray", "white", "b", "g", "w" -> {
                int colorInt = convertTowerColorToInteger(multipleInput[1]);
                if(isInteger) {
                    game.choosePlayerCustom(colorInt, inputInteger - 1);
                    showWait();
                }

                else cli.askToRetry("please select the mage by its number");}
            default -> cli.askToRetry("please select the color by its name or initial");
        }
    }

    public void casePlanPlayCard() {
        if(isInteger)
            try {
                game.useAssistantCard(inputInteger - 1);
                showWait();
            }catch (CardNotFoundException | ArrayIndexOutOfBoundsException e){
                cli.askToRetry(e.getMessage() +"please select an available card");
            }
        else cli.askToRetry("please select a card");
    }
    private boolean canPlayCharacter(){
        return multipleInput[0].equalsIgnoreCase("c") && !game.isEasy() && game.getActiveCharacter()==0;
    }
    public void caseActionMoveMother() {
        if(canPlayCharacter()) {
            cli.showCharacters();
            charactersDecode(new Scanner(System.in).nextLine());
            return;
        }
        if(isInteger) {
            game.moveMother(inputInteger);
            showWait();
        }
        else cli.askToRetry("please select a number of steps, max" +(game.getCardYouPlayed()+2)/2);

    }

    public void caseActionMoveStudent() {
        if(canPlayCharacter()) {
            cli.showCharacters();
            charactersDecode(new Scanner(System.in).nextLine());
            return;
        }
        if(!somethingSelected) {
            int color=convertStudentColorToInteger(multipleInput[0]);
            if(game.getPersonalPlayer().getBoard().hasStudentOfColorInEntrance(color)){
                targetColor = color;
                somethingSelected = true;
                cli.askWhereToMove();
            }
            else cli.askToRetry(Printer.PINK+"please select an available student color ");
        }
        else {
            if(isIsland(multipleInput[0]) && isInteger) {
                somethingSelected=false;
                try {
                    game.moveStudentToIsland(targetColor, inputInteger);
                    showWait();
                }catch (IllegalMoveException e){
                    cli.askToRetry(Printer.PINK+"island : " +e.getMessage()+1 +" not available, select another");
                }
            }
            else {
                somethingSelected=false;
                game.moveStudentToHall(targetColor);
                showWait();
            }
        }
    }

    public void caseActionChooseCloud() {
        if(isInteger && inputInteger<=game.getClouds().size() && inputInteger>0) {
            game.chooseCloud(inputInteger - 1);
            showWait();
        }
        else cli.askToRetry(Printer.PINK+"please select an available cloud id"+Printer.RST);
    }

    /** Creates the instance of the InputManager with a reference to the cli class*/
    public InputManagerCli(Cli cli){
        this.cli=cli;
        game=cli.getGame();
        this.characterInputManager= new CharacterInputManager(game);
    }

    /**Checks if a string wrote by the user represent an existing island */
    private boolean isIsland(String input){
        try {
            return Integer.parseInt(input) < game.getIslands().size() && Integer.parseInt(input) >= 0;
        }catch (NumberFormatException e){
            return false;
        }
    }
    /**Used during the personalisation of the player
     * Parses the text input into an integer representing the color of tower*/
    public static int convertTowerColorToInteger(String color){
        String cc=color.toLowerCase(Locale.ROOT);
        return switch (cc){
            default -> -1;
            case "black","b"-> 0;
            case "white","w"-> 1;
            case "gray","g"->2;
        };
    }
    /** Parses the text input into an integer representing the color of student*/
    public static int convertStudentColorToInteger(String color){
        return switch (color){
            default -> -1;
            case "red", "r" -> 0;
            case "yellow","y"->1;
            case "green","g" -> 2;
            case "blue","b"-> 3;
            case "pink","p"-> 4;
        };
    }
}
