package it.polimi.ingsw.client.GUI.Scenes;

import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public abstract class SceneController {
    protected Pane pane;
    protected List<Pane> containerList;

    /** Starts or refresh the scene's stuff*/
    public abstract void initialize();
    /** Used when a clickable element is covered by other Panels, preventing it from being activated*/
    protected void setOthersContainerMouseTransparent(Pane containerClickable){
        for (Pane container:containerList) {
            pane.setMouseTransparent(true);
        }
        containerClickable.setMouseTransparent(false);
    }
    protected void setContainerList(List<Pane> containers){
        containers=new ArrayList<>();
        containerList=containers;
    }
    protected void addContainerToList(Pane container){
        containerList.add(container);
    }


}
