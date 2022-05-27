package it.polimi.ingsw.client.GUI.Scenes;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

public abstract class SceneController {
    protected Pane pane;

    /** Starts or refresh the scene's stuff*/
    public abstract void initialize();

    /** used to get the scene in class Gui*/
    public Pane getPane(){
        return pane;
    }
}
