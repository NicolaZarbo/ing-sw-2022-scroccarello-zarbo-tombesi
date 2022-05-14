package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.view.CentralView;

import java.util.Locale;
import java.util.Scanner;

public class InputManager {

    private final CentralView game;
    private final Cli cli;
    private int colorInt;
    private boolean somethingSelected;
    private int nStudentsMoved;
    private int targetColor;
    boolean isInteger;
    int inputInteger;

    public InputManager(Cli cli){

        this.cli=cli;
        game=cli.getGame();
    }
    public void decodeInput(String input){
        if(!game.isYourTurn() )
            return;
        String[] multipleInput=input.toLowerCase().split("\s");
        String inLo=multipleInput[0];
        try{
            inputInteger=Integer.parseInt(inLo);
            isInteger=true;
        }
        catch (NumberFormatException e){
            isInteger=false;
            inputInteger=-1;
        }
        switch (game.getState()){
            case setupPlayers -> {
                if(multipleInput.length!=2){
                    cli.askToRetry("please select a mage value followed by the initial of the color");
                    return;
                }
                switch (multipleInput[1]) {
                    case "black", "gray", "white", "b", "g", "w" -> colorInt = convertTowerColorToInteger(multipleInput[1]);
                    default -> cli.askToRetry("please select the color by its name or initial");
                }
                    if(isInteger)
                        game.choosePlayerCustom(colorInt, inputInteger);
                    else cli.askToRetry("please select the mage by its number");
            }
            case planPlayCard ->{
                if(isInteger)
                    try {
                        game.useAssistantCard(inputInteger - 1);
                    }catch (CardNotFoundException e){
                        cli.askToRetry(e.getMessage());
                    }
                else cli.askToRetry("please select a card");
            }
            case actionMoveMother -> {
                if(isInteger)
                    game.moveMother(inputInteger);
                else cli.askToRetry("please select a number of steps, max" +(game.getCardYouPlayed()+2)/2);
            }
            case actionMoveStudent -> {
                if(!somethingSelected) {
                    int color=convertStudentColorToInteger(inLo);
                    if(game.getPersonalPlayer().getBoard().hasStudentOfColor(color)){
                        targetColor = color;
                        somethingSelected = true;
                        cli.askWhereToMove();
                    }
                    else cli.askToRetry("please select an available student color ");
                }
                else {
                    if(isIsland(inLo) && isInteger) {
                        somethingSelected=false;
                        game.moveStudentToIsland(targetColor, inputInteger);
                    }
                    else {
                        somethingSelected=false;
                        game.moveStudentToHall(targetColor);
                    }
                }
            }
            case actionChooseCloud -> {
                if(isInteger && inputInteger<=game.getClouds().size() && inputInteger>0)
                    game.chooseCloud(inputInteger-1);
                else cli.askToRetry("please select an available cloud id");
            }
        }
    }

    private boolean isIsland(String input){
        try {
            return Integer.parseInt(input) < game.getIslands().size() && Integer.parseInt(input) >= 0;
        }catch (NumberFormatException e){
            return false;
        }
    }
    private int convertTowerColorToInteger(String color){
        String cc=color.toLowerCase(Locale.ROOT);
        return switch (cc){
            default -> -1;
            case "black","b"-> 0;
            case "white","w"-> 1;
            case "gray","g"->2;
        };
    }
    private int convertStudentColorToInteger(String color){
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
