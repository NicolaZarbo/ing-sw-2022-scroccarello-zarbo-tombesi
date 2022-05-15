package it.polimi.ingsw.view.Gui;

import it.polimi.ingsw.messages.server.PlayerSetUpMessage;
import it.polimi.ingsw.view.CLI.Cli;
import it.polimi.ingsw.view.CLI.InputManager;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;

import java.io.PrintWriter;
import java.util.Scanner;

public class GUI implements UserInterface {
    private final CentralView game;
    private PrintWriter socketOut;

    private String ip="127.0.0.1";
    private int port=12345;

    public GUI(){
        this.game = new CentralView(this);



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
