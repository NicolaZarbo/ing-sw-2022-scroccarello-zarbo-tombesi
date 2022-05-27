package it.polimi.ingsw.client.GUI.Scenes;
import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class FirstSceneController extends SceneController {
    private final GUI gui;
    public FirstSceneController(GUI gui) {
        this.gui=gui;
        try {
            pane= FXMLLoader.load(getClass().getResource("/usernameScene.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

   @Override
    public void initialize() {
        Button  startBtn= (Button) pane.lookup("#sendButton");
        startBtn.setOnAction(event -> {
                    String name = ((TextField)pane.lookup("#usernameBox")).getText();
            System.out.println("oooooooooooooooooo");
                    gui.startConnection(new ByteArrayInputStream(name.getBytes()));


                });
    }

}
