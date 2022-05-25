package it.polimi.ingsw.view.Gui.Scenes;
import it.polimi.ingsw.view.Gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FirstSceneController  {
    Pane scene;
    public FirstSceneController(GUI gui) {
        try {
            scene= FXMLLoader.load(getClass().getResource("/usernameScene.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane getPane() {
        return scene;
    }
}
