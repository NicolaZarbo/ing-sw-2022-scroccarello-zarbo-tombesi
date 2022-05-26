package it.polimi.ingsw.client.GUI.Scenes;
import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FirstSceneController extends SceneController {
    private final GUI gui;
    public FirstSceneController(GUI gui) {
        this.gui=gui;
        try {
            pane= FXMLLoader.load(getClass().getResource("/usernameScene.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void updateScene() {
        Button  startBtn= (Button) pane.lookup("#sendButton");
        startBtn.setOnAction(event -> {
                    String name = ((TextField)pane.lookup("#usernameBox")).getText();
                    gui.startConnection(new ByteArrayInputStream(name.getBytes()));
                });
    }

    public Pane getPane() {
        return pane;
    }
}
