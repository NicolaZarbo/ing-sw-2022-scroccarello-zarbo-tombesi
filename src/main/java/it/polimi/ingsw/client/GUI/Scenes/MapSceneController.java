package it.polimi.ingsw.client.GUI.Scenes;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MapSceneController {
    private Pane scene;

    public MapSceneController() {
        try {
            scene= FXMLLoader.load(getClass().getResource("/MapScene.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane getPane() {
        return scene;
    }
}
