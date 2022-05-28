package it.polimi.ingsw.client.GUI;

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

       /* initScene(SceneEnum.WizardScene);
        mainStage.setScene(scenes.get(SceneEnum.WizardScene));
        //just to test the displaying of the image
    */

        mainStage.setResizable(false);
        mainStage.show();
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
            initScene(SceneEnum.LobbyScene);
            mainStage.setScene(scenes.get(SceneEnum.LobbyScene));
            mainStage.setResizable(false);
            mainStage.show();
        }
        else{
            mainStage.setScene(scenes.get(SceneEnum.WizardScene));
            mainStage.setResizable(false);
            mainStage.show();
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
        Platform.runLater(() -> {
            mainStage.setScene(scenes.get(SceneEnum.HandScene));
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
