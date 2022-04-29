package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.messages.MassageManager;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.View;

import java.util.Scanner;




public class Cli implements View {

    private CentralView game;
    private InputManager inputManager;
    private  Scanner input;
    private  Printer printer;
    private MassageManager massageManager;

    //Turn state is an integer used to represent the state of the game, here are the meaning of the values:
// 0: pre game
// 1: initialisation of the game
// 2: player's turn
// 3: another player's turn
    private int turnState;

    public CLI(){
        this.game = new CentralView();
        this.input = new Scanner(System.in);
        this.printer = new Printer();
        this.inputManager = new InputManager(game);

    }

    public void runCLI() {


    }
}
