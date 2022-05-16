package it.polimi.ingsw.view.Gui;

import it.polimi.ingsw.messages.server.PlayerSetUpMessage;
import it.polimi.ingsw.view.CLI.Cli;
import it.polimi.ingsw.view.CLI.InputManager;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.util.Scanner;

public class GUI extends Application implements UserInterface {
    private  CentralView game;
    private PrintWriter socketOut;
    private final Scanner input;
    private String ip="127.0.0.1";
    private int port=12345;

    public GUI(){
        this.game = new CentralView(this);
        this.input = new Scanner(System.in);


    }

    @Override
    public void start(Stage stage) throws Exception {
        game = new CentralView(this);

        stage.setTitle("Eriantys");
       // Platform.runLater(() -> Transition.setPrimaryStage(stage));

    }

    public CentralView getGame() {
        return game;
    }
    @Override
    public void showView() {

    }

    @Override
    public void showHand() {

    }

    @Override
    public void showCharacters() {

    }

    @Override
    public void showOptionsForPersonalization(PlayerSetUpMessage message) {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void askToMoveStudent() {

    }

    @Override
    public void askToMoveMother() {

    }

    @Override
    public void showClouds() {

    }
}
