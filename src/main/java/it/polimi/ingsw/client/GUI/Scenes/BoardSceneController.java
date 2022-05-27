package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BoardSceneController extends SceneController{

    private final GUI gui;

    /**constructor which decides how many boards to display according to the number of players*/
    public BoardSceneController(GUI gui) {
        this.gui=gui;
        try {
            if(gui.getGame().getPlayers().size()==3){
                pane= FXMLLoader.load(getClass().getResource("/BoardScene3.fxml"));
            }
            else{
                pane= FXMLLoader.load(getClass().getResource("/BoardScene2.fxml"));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    @Override
    public void initialize() {
        /*Button startBtn= (Button) pane.lookup("#sendButton");
        startBtn.setOnAction(event -> {
            String name = ((TextField)pane.lookup("#usernameBox")).getText();
            gui.startConnection(new ByteArrayInputStream(name.getBytes()));
            System.out.println("oooooooooooooooooo");

        });*/
    }

}
