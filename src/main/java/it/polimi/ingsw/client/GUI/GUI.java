package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.Scenes.*;
import it.polimi.ingsw.client.ServerConnection;
import it.polimi.ingsw.enumerations.SceneEnum;
import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
            Thread serverThread= new Thread(()->connection.run());
            serverThread.start();
        }catch (IOException e){
            throw new RuntimeException("connection failed");
        }
        game.addObserver(connection.setMessageHandler());
        if(!this.inputManager.isLobbyAvailable()) {//todo thread stuff
            //mainStage.setScene(scenes.get(SceneEnum.LobbyScene));//there are possibly better way to change scene and to update it at the same time
            setScene(SceneEnum.LobbyScene);
        }
        else{
            goToWizardSelection();
        }
    }
    private void goToWizardSelection(){
        initScene(SceneEnum.SetupScene);
        mainStage.setScene(scenes.get(SceneEnum.SetupScene));
        //mainStage.setResizable(false);
        //mainStage.show();
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
        if(!scenes.containsKey(sceneName))
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
    public void showHand() {//todo have these methods call this class setScene method inside
        Platform.runLater(() -> {
            mainStage.setScene(scenes.get(SceneEnum.HandScene));
        });
    }

    @Override
    public void showCharacters() {

    }

    @Override
    public void showOptionsForPersonalization(PlayerSetUpMessage message) {
        goToWizardSelection();
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
