package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.ServerConnection;
import it.polimi.ingsw.enumerations.GameState;
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
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**The Graphical User Interface. It shows the game to the player by alternating different scenes. The scenes are created with javafx, but they are also filled with graphical resources. User interaction is mainly realized with mouse actions (e.g. mouse click, mouse over) and in minimal part with text typing (e.g. username selection).*/
public class GUI extends Application implements UserInterface {
    private  CentralView game;
    GuiInputManager inputManager ;
    private ServerConnection connection;
    private Map<SceneEnum,Scene> scenes;
    public static Stage mainStage;
    private SceneEnum currentScene;

    /**It creates the base instance of the class.*/
    public GUI(){
    }

    /**It runs the Graphical User Interface.*/
    public static void main(String [] args){
        launch(args);
    }

    /**It creates the user's central view and shows the first scene of the game.*/
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

    /**It opens the connection with the server and sends the selected nickname. It also runs the connection handler on a different thread.
     * @param inputString an input stream with the name of the player*/
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

    /**It interacts with the server to load information about lobby in a string format.*/
    public void setLobbyRules(int numberPlayer, boolean easy){
        String e;
        if(easy)
            e="y";
        else e="n";
        InputStream rules = new ByteArrayInputStream((numberPlayer+"\n"+e).getBytes());
        connection.writeTxtForLobby(rules);
    }

    /**It initializes the scene and sets it on the queue of scenes to load.
     * @param sceneName the name related to the scene*/
    public void setScene(SceneEnum sceneName){
        initScene(sceneName);
        currentScene=sceneName;
        Platform.runLater(()->mainStage.setScene(scenes.get(sceneName)));
    }

    /**It loads the scene into a pane, then it creates the key-value association with its scene name.
     * @param sceneName the name related to the fxml scene
     * @exception RuntimeException if some I/O errors occur*/
    public void initScene(SceneEnum sceneName){
        Pane pane ;
        try {
            pane=FXMLLoader.load(getClass().getResource(sceneName.getPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scenes.put(sceneName,new Scene(pane));
    }

    public CentralView getGame() {
        return game;
    }

    @Override
    public void refresh() {
        if(game.isYourTurn() || game.getState()== GameState.setupPlayers)
           return;
        setScene(currentScene);
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

    /**@return the input manager of the Graphical User Interface*/
    public GuiInputManager getInputManager(){
        return inputManager;
    }

    /**It stops the javafx thread (default called by javafx on window close).
     */
    public void stop(){
        System.out.println("closed the application");
        connection.closeConnection();
        System.exit(0);
    }


}
