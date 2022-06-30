package it.polimi.ingsw.client.GUI.Scenes;

import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

/**The generic controller of fxml scene. It manages all the elements of the scene and the actions the user can do on them.*/
public abstract class SceneController {
    protected Pane pane;
    protected List<Pane> containerList;

    /**It starts or refreshes the scene.*/
    public abstract void initialize();

    /**It is used when a clickable element is covered by other Panels, preventing it from being activated.*/
    protected void setOthersContainerMouseTransparent(Pane containerClickable){
        for (Pane container:containerList) {
            container.setMouseTransparent(true);
        }
        containerClickable.setMouseTransparent(false);
    }

    /**It sets the container list of the scene. Containers are pane(s) and its variances.
     * @param containers the list of containers*/
    protected void setContainerList(List<Pane> containers){
        containerList=containers;
    }



}
