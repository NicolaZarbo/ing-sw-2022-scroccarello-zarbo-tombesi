package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.Scenes.*;
import it.polimi.ingsw.client.ServerConnection;
import it.polimi.ingsw.enumerations.SceneEnum;
import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GUI extends Application implements UserInterface {
    private  CentralView game;
    GuiInputManager inputManager ;
    private ServerConnection connection;
    private Map<SceneEnum,Scene> scenes;
    public static Stage mainStage;


    public GUI(){
    }
    public void start(Stage stage) throws IOException {
        game = new CentralView(this);
        inputManager= new GuiInputManager(game);
        stage.setTitle("Eriantys");
        mainStage=stage;
        setScenes();


        stage.setScene(scenes.get(SceneEnum.FirstScene));

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
        if(!this.inputManager.isLobbyAvailable()) {
            mainStage.setScene(scenes.get(SceneEnum.LobbyScene));//there are possibly better way to change scene and to update it at the same time
        }
        else{
            // mainStage.setScene();
        }
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
        scenes.put(SceneEnum.FirstScene,new Scene(new FirstSceneController(this).getPane()));
        //scenes.put("MapScene",new Scene(new MapSceneController().getPane())); cant initialize it now, there is no view yet
        scenes.put(SceneEnum.LobbyScene,new Scene(new LobbySceneController().getPane()));
        //scenes.put("BoardSecene",new Scene(new  BoardSceneController(this).getPane()));
    }
    public void setScene(SceneEnum sceneName, Scene scene){
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
