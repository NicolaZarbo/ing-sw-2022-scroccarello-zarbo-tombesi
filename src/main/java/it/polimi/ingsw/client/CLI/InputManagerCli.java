package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.printers.Printer;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.client.InputManager;

import java.util.Locale;
import java.util.Scanner;

public class InputManagerCli  extends InputManager {

    private final CentralView game;
    private final Cli cli;
    private int colorInt;
    private boolean somethingSelected;
    private int targetColor;
    private final CharacterInputManager characterInputManager;
    boolean isInteger;
    int inputInteger;
    String[] multipleInput;


    public void decodeStringInput(String input){
        if(!game.isYourTurn() )
            return;
        multipleInput=input.toLowerCase().split("\s");
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

    public void charactersDecode(String input){
        int nChar;
        try{
            nChar=Integer.parseInt(input);
            characterInputManager.characterManagerCall(nChar);
        }catch (IllegalMoveException e){
            cli.showView();
            if(game.getState()== GameState.actionMoveStudent)
                cli.askToMoveStudent();
            if(game.getState()==GameState.actionMoveMother)
                cli.askToMoveMother();
            cli.askToRetry("\n"+e.getMessage());
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

    @Override
    public void caseSetupPlayers() {
        if(multipleInput.length!=2){
            cli.askToRetry("please select a mage value followed by the initial of the color");
            return;
        }
        switch (multipleInput[1]) {
            case "black", "gray", "white", "b", "g", "w" -> {colorInt = convertTowerColorToInteger(multipleInput[1]);
                if(isInteger)
                    game.choosePlayerCustom(colorInt, inputInteger-1);
                else cli.askToRetry("please select the mage by its number");}
            default -> cli.askToRetry("please select the color by its name or initial");
        }
    }

    @Override
    public void casePlanPlayCard() {
        if(isInteger)
            try {
                game.useAssistantCard(inputInteger - 1);
            }catch (CardNotFoundException | ArrayIndexOutOfBoundsException e){
                cli.askToRetry(e.getMessage() +"please select an available card");
            }
        else cli.askToRetry("please select a card");
    }

    @Override
    public void caseActionMoveMother() {
        if(multipleInput[0].equalsIgnoreCase("c") && !game.isEasy()) {
            cli.showCharacters();
            charactersDecode(new Scanner(System.in).nextLine());
        }
        if(isInteger)
            game.moveMother(inputInteger);
        else cli.askToRetry("please select a number of steps, max" +(game.getCardYouPlayed()+2)/2);

    }

    @Override
    public void caseActionMoveStudent() {
        if(multipleInput[0].equalsIgnoreCase("c") && !game.isEasy()) {
            cli.showCharacters();
            charactersDecode(new Scanner(System.in).nextLine());
        }
        if(!somethingSelected) {
            int color=convertStudentColorToInteger(multipleInput[0]);
            if(game.getPersonalPlayer().getBoard().hasStudentOfColor(color)){
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
                }catch (IllegalMoveException e){
                    cli.askToRetry(Printer.PINK+"island : " +e.getMessage()+1 +" not available, select another");
                }
            }
            else {
                somethingSelected=false;
                game.moveStudentToHall(targetColor);
            }
        }
    }

    @Override
    public void caseActionChooseCloud() {
        if(isInteger && inputInteger<=game.getClouds().size() && inputInteger>0)
            game.chooseCloud(inputInteger-1);
        else cli.askToRetry(Printer.PINK+"please select an available cloud id"+Printer.RST);
    }

    public InputManagerCli(Cli cli){

        this.cli=cli;
        game=cli.getGame();
        this.characterInputManager= new CharacterInputManager(game);
    }

    private boolean isIsland(String input){
        try {
            return Integer.parseInt(input) < game.getIslands().size() && Integer.parseInt(input) >= 0;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public static int convertTowerColorToInteger(String color){
        String cc=color.toLowerCase(Locale.ROOT);
        return switch (cc){
            default -> -1;
            case "black","b"-> 0;
            case "white","w"-> 1;
            case "gray","g"->2;
        };
    }
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
    private void character1(){

    }
}
