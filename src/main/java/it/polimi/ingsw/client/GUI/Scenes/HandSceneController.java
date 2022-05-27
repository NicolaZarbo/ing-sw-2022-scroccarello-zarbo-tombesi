package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HandSceneController {
    Pane scene;
    public HandSceneController() {
        try {
            scene= FXMLLoader.load(getClass().getResource("/HandScene.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane getPane() {
        return scene;
    }
}
