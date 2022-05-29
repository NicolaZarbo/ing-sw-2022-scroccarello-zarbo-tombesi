package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class BoardSceneController extends SceneController{

    private GUI gui;

    @FXML
    private Circle hall6;


    private ArrayList<Circle> entrance;
    private ArrayList<ArrayList<Circle>> hall;

    /**constructor which decides how many boards to display according to the number of players*/
    public BoardSceneController(GUI g) {
        this.gui=g;
        this.entrance =new ArrayList<>();
        this.hall =new ArrayList<>();
    }

    @Override
    public void initialize() {

    }

    //metodo per settare tutti i cerchi, rettangoli e esagoni trasparenti poichè all'inizio la board è vuota
    public void setToStart(GUI gui){
    /*    if(gui.getGame().getPlayers().size()==3) {
            AnchorPane first = pane.lookup("#boardPlayer1");
            AnchorPane second = pane.lookup("#boardPlayer2");
            AnchorPane third = pane.lookup("#boardPlayer3");
            for(int i=1;i<10;i++) {
                first.lookup("#hall" + i).setFill("Color.TRANSPARENT");
                second.lookup("#hall" + i).setFill("Color.TRANSPARENT");
                third.lookup("#hall" + i).setFill("Color.TRANSPARENT");


            }

        }
*/
    }

}
