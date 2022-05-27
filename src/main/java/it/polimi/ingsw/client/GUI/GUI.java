package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.Scenes.BoardSceneController;
import it.polimi.ingsw.client.GUI.Scenes.LobbySceneController;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUI extends Application implements UserInterface {
    private  CentralView game;
    GuiInputManager inputManager ;
    private ServerConnection connection;
    private Map<String,Scene> scenes;
    public static Stage mainStage;


    public GUI(){
    }
    public void start(Stage stage) throws IOException {
        game = new CentralView(this);
        inputManager= new GuiInputManager(game);
        stage.setTitle("Eriantys");
        mainStage=stage;
        setScenes();
        //Platform.runLater(() -> {
        stage.setScene(scenes.get("FirstScene"));
        //});
        stage.setResizable(false);
        stage.show();
    }

    public void startConnection(InputStream  inputString)  {
      Scanner starter= new Scanner(inputString);//will contain an inputStream with the name
        try {
            connection = new ServerConnection(starter, game, inputManager);
        }catch (IOException e){
            throw new RuntimeException("connection failed");
        }
        game.addObserver(connection.setMessageHandler());
        mainStage.setScene(scenes.get("LobbyScene"));//there are possibly better way to change scene and to update it at the same time
    }
    public void setLobbyRules(int numberPlayer, boolean easy){
        String e;
        if(easy)
            e="y";
        else e="n";
        InputStream rules = new ByteArrayInputStream((numberPlayer+"\n"+e).getBytes());
        connection.writeTxtForLobby(rules);
    }
    private void setScenes(){
        //creates scenes and then saves them in this.scene with their name as a key
        scenes= new HashMap<>();
        scenes.put("FirstScene",new Scene(new FirstSceneController(this).getPane()));
        //scenes.put("MapScene",new Scene(new MapSceneController().getPane())); cant initialize it now, there is no view yet
        scenes.put("LobbyScene",new Scene(new LobbySceneController().getPane()));
        //scenes.put("BoardSecene",new Scene(new  BoardSceneController(this).getPane()));
    }
    public void setScene(String sceneName, Scene scene){
        scenes.put(sceneName,scene);
    }

    public CentralView getGame() {
        return game;
    }
    @Override
    public void showView() {

    }

    @Override
    public void showHand() {
        Platform.runLater(() -> {
            mainStage.setScene(scenes.get("HandScene"));
        });
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
        Platform.runLater(() -> {
            mainStage.setScene(scenes.get("BoardScene"));
        });
    }

    @Override
    public void showIslands() {
        Platform.runLater(() -> {
            mainStage.setScene(scenes.get("MapScene"));
        });
    }

}
