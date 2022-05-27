package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

public class LobbySceneController extends SceneController{
    private GUI gui;

    // @FXML
    //ChoiceBox<Integer> nPlayers;

    @FXML
    public void initialize() {//todo the lookup methods finds a node only if it is a child of the node, there are other ways to reference a fxml object, lookout for those
        /*Node pp=pane.lookup("#");
        if(pp==null)
            throw new RuntimeException("ooooooooooooooooo");
        ChoiceBox<Integer> nPlayers = ((ChoiceBox<Integer>)pp);
        CheckBox easy= (CheckBox) pane.lookup("#easySelector");
        Button go = (Button) pane.lookup("#createLobbyButton");
        nPlayers.setItems(FXCollections.observableArrayList(2,3,4));
        go.setOnAction(event->{
            gui.setLobbyRules(nPlayers.getValue(), easy.isSelected());
        });

         */


    }

    public LobbySceneController(GUI gui) {
        this.gui=gui;
        try {
            pane= FXMLLoader.load(getClass().getResource("/lobbyScene.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("terrible");
        //initialize();
    }
}

