package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ServerConnection;
import it.polimi.ingsw.enumerations.SceneEnum;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.exceptions.TimeOutConnectionException;
import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
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
    public static void main(String [] args){
        launch(args);
    }

    public void start(Stage stage){
        game = new CentralView(this);
        inputManager= new GuiInputManager(game,this);
        stage.setTitle("Eriantys");
        mainStage=stage;
        scenes=new HashMap<>();
        initScene(SceneEnum.WelcomeScene);
        mainStage.setScene(scenes.get(SceneEnum.WelcomeScene));
        mainStage.setResizable(false);
        mainStage.show();
    }
    /** @param inputString an inputStream with the name of the player*/
    public void startConnection(InputStream  inputString)  {
      Scanner starter= new Scanner(inputString);
        try {
            connection = new ServerConnection(starter, game, inputManager);
            Thread serverThread = new Thread(() -> {
                try {
                    connection.run();
                } catch (MessageErrorException mes) {
                    showError(mes.getMessage());
                }catch (TimeOutConnectionException timeoutException){
                    showError("Server Connection lost");
                }
            });
            serverThread.start();
        }catch (IOException e){
            throw new RuntimeException("connection failed");
        }
        game.addObserver(connection.setMessageHandler());
        while (!GuiInputManager.isIsConnected())
        {Thread.onSpinWait();}
        if(!GuiInputManager.isLobbyAvailable()) {
            setScene(SceneEnum.LobbyScene);
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
    public void setScene(SceneEnum sceneName){
        initScene(sceneName);
        Platform.runLater(()->mainStage.setScene(scenes.get(sceneName)));
    }
    public void initScene(SceneEnum sceneName){
        Pane pane ;
        try {
            pane=FXMLLoader.load(getClass().getResource(sceneName.getPath()));//todo add to all fxml the fx:controller attribute to the right controller class
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scenes.put(sceneName,new Scene(pane));
    }

    public CentralView getGame() {
        return game;
    }

    @Override
    public void showView() {

    }

    @Override
    public void showHand() {
        setScene(SceneEnum.HandScene);
    }

    @Override
    public void showCharacters() {
        setScene(SceneEnum.CharacterScene);
    }

    @Override
    public void showOptionsForPersonalization(PlayerSetUpMessage message) {
        setScene(SceneEnum.SetupScene);
    }

    @Override
    public void showError(String errorMessage) {
        System.out.println(errorMessage);
        Platform.runLater(()->{Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            alert.setTitle("Error!");
            alert.show();});
    }

    @Override
    public void askToMoveStudent() {
        showBoards();
    }

    @Override
    public void askToMoveMother() {
        setScene(SceneEnum.MapScene);
    }

    @Override
    public void showClouds() {
        setScene(SceneEnum.MapScene);
    }

    @Override
    public void showBoards() {
        setScene(SceneEnum.BoardSceneX);
    }

    @Override
    public void showIslands() {
        setScene(SceneEnum.MapScene);
    }

    @Override
    public void gameOver() {
        setScene(SceneEnum.GameOverScene);
    }

    public GuiInputManager getInputManager(){
        return inputManager;
    }

    /**
     * Method called by default by javafx on window close
     */
    public void stop(){
        System.out.println("closed the application");
        connection.closeConnection();
        System.exit(0);
    }


}
