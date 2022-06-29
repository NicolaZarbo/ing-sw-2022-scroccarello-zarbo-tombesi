package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**The handler of lobby creation scene.*/
public class LobbySceneController extends SceneController  {
    public CheckBox easySelector;
    public ChoiceBox<Integer> nPlayers;
    public Button createLobbyButton;
    public Pane mainpanel;
    public Text waitingtext;

    private final GUI gui;
    public Pane root;

    @Override
    public void initialize() {
        root.setStyle("-fx-background-image: url(images/wallpapers/LowerQualityLobby.png); -fx-background-size: 1236 863");
        nPlayers.setItems(FXCollections.observableArrayList(1,2,3,4));//fixme 1 is here for test purposes
        playerSetter();
    }

    /**It sets the number of players selection.*/
    private void playerSetter(){
        mainpanel.setOnKeyPressed(keyEvent -> {
            int nPlayer;
            switch (keyEvent.getCode()){
                case DIGIT1 -> nPlayer=1;
                case DIGIT2 -> nPlayer=2;
                case DIGIT3 -> nPlayer=3;
                case DIGIT4 -> nPlayer=4;
                default -> {
                    return;
                }
            }
            gui.setLobbyRules(nPlayer, easySelector.isSelected());
        });
    }

    /**It creates the instance of the lobby scene controller.*/
    public LobbySceneController() {
       this.gui= GuiInputManager.getGui();
    }

    /**It triggers lobby creation based on user's inputs.*/
    public void createLobby(ActionEvent actionEvent) {
        gui.setLobbyRules(nPlayers.getValue(), easySelector.isSelected());
        setWaitingScreen();
    }

    /**It sets a waiting message for the player who has created the lobby.*/
    public void setWaitingScreen(){
        mainpanel.setOpacity(0.5);
        waitingtext.setText("Waiting for players to join..");
    }
}

