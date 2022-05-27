package it.polimi.ingsw.client.GUI.Scenes;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class FirstSceneController extends SceneController {
    public Button sendButton;
    public TextField usernameBox;
    private GUI gui;
    public FirstSceneController() {
    }

   @Override@FXML
    public void initialize() {
        this.gui= GuiInputManager.getGui();
        /*
        sendButton.setOnAction(event -> {
                    String name = usernameBox.getText();
            System.out.println("ooooobreakPooooooooooooo");
                    gui.startConnection(new ByteArrayInputStream(name.getBytes()));
                });

         */
    }


    public void doAction(javafx.event.ActionEvent actionEvent) {
        String name = usernameBox.getText();
        System.out.println("ooooobreakPooooooooooooo");
        gui.startConnection(new ByteArrayInputStream(name.getBytes()));
    }
}
