package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.printers.*;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.CharacterErrorException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.client.InputManager;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**The handler of client inputs for the Command Line Interface. It translates commands from client into actions and messages for server, also into error messages for client.*/
public class InputManagerCli  extends InputManager {

    private final CentralView game;
    private final Cli cli;
    private boolean somethingSelected;
    private int targetColor;
    private final CharacterInputManager characterInputManager;
    private boolean isInteger;
    private int inputInteger;
    private String[] multipleInput;

    /** It decodes an input string from the user into a meaningful action on the view.*/
    public void decodeStringInput(String input){
        if(!game.isYourTurn() )
            return;
        multipleInput=input.toLowerCase().split("\s");
        if(multipleInput[0].equalsIgnoreCase("show") && game.getState()!= GameState.setupPlayers) {
            showManager();
            return;
        }
        if(multipleInput[0].equalsIgnoreCase("close")){
            cli.close();
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

    /**It switches the right manager based on the state of the game.
     * @param state the state of the game*/
    protected void stateSwitch(GameState state){
        switch (state){
            case setupPlayers -> caseSetupPlayers();
            case planPlayCard -> casePlanPlayCard();
            case actionMoveMother -> caseActionMoveMother();
            case actionMoveStudent -> caseActionMoveStudent();
            case actionChooseCloud -> caseActionChooseCloud();
        }
    }

    /**It is used to show things based on command from the user.*/
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

    /**It shows the user contextual information based on the state of the game.
     * It is used to make the player focus back to the task at hand.*/
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

    /**It is used when the player chooses to activate a character.
     * @param input the input string from the user
     * @see CharacterInputManager*/
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
        }
    }

    @Override
    public void decodeInput() {
        decodeStringInput(new Scanner(System.in).nextLine());
    }

    @Override
    public void printToScreen(String string) {
        System.out.println(Cli.IMP+string+ Cli.RST);
     }

     /**It ensures the player that his command was sent and the server is processing a response.*/
     private void showWait(){
        System.out.println(Printer.BLACK+Printer.BR_WHITE_BKG+"Please Wait...."+Printer.RST);
     }

     /**It is used to print on screen information during setup phase.*/
    public void caseSetupPlayers() {
        boolean isTeamSecondPlayer=game.isTeamPlay() && game.getAvailableColor().size()==0;
        if(multipleInput.length!=2 && !isTeamSecondPlayer){
            cli.askToRetry("please select a mage value followed by the initial of the color");
            return;
        }
        if(isTeamSecondPlayer){
            if(isInteger) {
                if(!game.getAvailableMages().contains(inputInteger-1)){
                    cli.askToRetry("please select the mage by its number");
                    return;
                }
                game.choosePlayerCustom(0, inputInteger - 1);
                showWait();
                return;
            }else cli.askToRetry("please select the mage by its number");
        }
        switch (multipleInput[1]) {
            case "black", "gray", "white", "b", "g", "w" -> {
                int colorInt = convertTowerColorToInteger(multipleInput[1]);
                if(isInteger) {
                    if(!game.getAvailableMages().contains(inputInteger))
                        return;
                    game.choosePlayerCustom(colorInt, inputInteger - 1);
                    showWait();
                }

                else cli.askToRetry("please select the mage by its number");}
            default -> cli.askToRetry("please select the color by its name or initial");
        }
    }

    /**It is used to print on screen information during planning phase.*/
    public void casePlanPlayCard() {
        if(isInteger)
            try {
                if(!canUseCard(inputInteger-1))
                    throw new CardNotFoundException("this card can't be used");
                game.useAssistantCard(inputInteger - 1);
                showWait();
            }catch (CardNotFoundException | ArrayIndexOutOfBoundsException e){
                cli.askToRetry(e.getMessage() +"please select an available card");
            }
        else cli.askToRetry("please select a card");
    }
    private boolean canUseCard(int cardNumber){
        List<Integer> usedCard=game.getPlayers().stream().map(s->game.getPlayedCardThisTurnByPlayerId(s.getId())).toList();
        boolean atLeastOneAvailable =false;
        int i=0;
        for (Boolean card:game.getPersonalPlayer().getAssistantCards()) {
            if(!usedCard.contains(i) && card)
                atLeastOneAvailable=true;
            i++;
        }
        return !(atLeastOneAvailable && usedCard.contains(cardNumber));
    }

    /**It states if a player can activate a character effect or not (expert mode only).*/
    private boolean canPlayCharacter(){
        return multipleInput[0].equalsIgnoreCase("c") && !game.isEasy() && game.getActiveCharacter()==0;
    }

    /**It is used to print on screen information during move mother phase.*/
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

    /**It is used to print on screen information during move student phase.*/
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

    /**It is used to print on screen information during choose cloud phase.*/
    public void caseActionChooseCloud() {
        if(isInteger && inputInteger<=game.getClouds().size() && inputInteger>0) {
            try{
            game.chooseCloud(inputInteger - 1);
            showWait();
            closeAble();
            }catch (RuntimeException ignore){
                System.out.println(Cli.IMP+"please select a good cloud"+ Cli.RST);
                decodeInput();
            }
        }
        else cli.askToRetry(Printer.PINK+"please select an available cloud id"+Printer.RST);
    }

    /**It accepts close message during other players turn.*/
    private void closeAble(){
        String close=new Scanner(System.in).nextLine();
        if(close.equalsIgnoreCase("close"))
            cli.close();
    }

    /** It creates the instance of the input manager with a reference to the CLI.
     * @param cli the command line interface of the user*/
    public InputManagerCli(Cli cli){
        this.cli=cli;
        game=cli.getGame();
        this.characterInputManager= new CharacterInputManager(game);
    }

    /**It checks if a string wrote by the user represents an existing island.
     * @param input the user input*/
    private boolean isIsland(String input){
        try {
            return game.getIslands().contains(Integer.parseInt(input));
        }catch (NumberFormatException e){
            return false;
        }
    }

    /**It is used during the personalisation of the player.
     * It parses the text input into an integer representing the color of tower.
     * @param color the tower color in string format*/
    public static int convertTowerColorToInteger(String color){
        String cc=color.toLowerCase(Locale.ROOT);
        return switch (cc){
            default -> -1;
            case "black","b"-> 0;
            case "white","w"-> 1;
            case "gray","g"->2;
        };
    }

    /**It parses the text input into an integer representing the color of student.
     * @param color the token color in string format*/
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
