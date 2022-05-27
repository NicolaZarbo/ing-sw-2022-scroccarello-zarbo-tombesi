package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LobbySceneController extends SceneController  {
    public CheckBox easySelector;
    public ChoiceBox<Integer> nPlayers;
    public Button createLobbyButton;
    private final GUI gui;

    @FXML
    public void initialize() {
        nPlayers.setItems(FXCollections.observableArrayList(2,3,4));
        createLobbyButton.setOnAction(event->{
            gui.setLobbyRules(nPlayers.getValue(), easySelector.isSelected());
        });

    }

    public LobbySceneController() {
       this.gui= GuiInputManager.getGui();
    }

    public void goButton(javafx.event.ActionEvent actionEvent) {
        System.out.println("lmao");

    }
}

