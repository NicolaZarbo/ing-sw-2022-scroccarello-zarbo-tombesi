package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ServerConnection;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.client.GUI.Scenes.FirstSceneController;
import it.polimi.ingsw.client.GUI.Scenes.MapSceneController;
import it.polimi.ingsw.view.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUI extends Application implements UserInterface, Observer<GenericMessage> {
    private  CentralView game;
    private PrintWriter socketOut;
    private final Scanner input;
    private ServerConnection connection;
    private Stage mainStage;
    private Map<String,Scene> scenes;


    public GUI(){
        this.game = new CentralView(this);
        this.input = new Scanner(System.in);


    }

    @Override
    public void start(Stage stage) throws Exception {
        game = new CentralView(this);
        GuiInputManager inputManager= new GuiInputManager(game);
        Scanner starter= new Scanner(InputStream.nullInputStream());//will contain an inputStream with the name; and if needed Number of player and difficulty
        connection= new ServerConnection(starter,game,inputManager);
        stage.setTitle("Eriantys");
        mainStage=stage;
        setScenes();
        Platform.runLater(() -> {
            stage.setScene(scenes.get("FirstScene"));
        });
        stage.setResizable(false);
        stage.show();
    }
    private void setScenes(){
        //creates scenes and then saves them in this.scene with their name as a key
        scenes= new HashMap<>();
        scenes.put("FirstScene",new FirstSceneController(this).getPane().getScene());
        scenes.put("MapScene",new MapSceneController().getPane().getScene());
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
        Platform.runLater(() -> {
            mainStage.setScene(scenes.get("MapScene"));
        });
    }

    @Override
    public void showBoards() {

    }

    @Override
    public void showIslands() {
        Platform.runLater(() -> {
            mainStage.setScene(scenes.get("MapScene"));
        });
    }

    @Override
    public void update(GenericMessage message) {

    }
}
