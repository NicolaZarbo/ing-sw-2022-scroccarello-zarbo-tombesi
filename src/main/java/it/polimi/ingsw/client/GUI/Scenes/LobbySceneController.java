package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.SceneEnum;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
/** scene used during setup*/
public class LobbySceneController extends SceneController  {
    public CheckBox easySelector;
    public ChoiceBox<Integer> nPlayers;
    public Button createLobbyButton;
    private final GUI gui;

    @FXML
    public void initialize() {
        nPlayers.setItems(FXCollections.observableArrayList(1,2,3,4));//fixme 1 is here for test purposes

    }

    public LobbySceneController() {
       this.gui= GuiInputManager.getGui();
    }

    public void createLobby(ActionEvent actionEvent) {
        gui.setLobbyRules(nPlayers.getValue(), easySelector.isSelected());
    }
}

