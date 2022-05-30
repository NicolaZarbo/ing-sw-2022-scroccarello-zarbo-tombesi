package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HandSceneController {
    Pane scene;
    GUI gui;
    public HandSceneController() {
        this.gui= GuiInputManager.getGui();
    }
    public void initialize(){

    }
    public void showboards(){
        gui.showBoards();
    }
    public Pane getPane() {
        return scene;
    }
}
