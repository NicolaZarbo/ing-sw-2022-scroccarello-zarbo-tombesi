package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BoardScene2Controller extends BoardSceneController{
    @FXML
    private Pane yourentrance;
    @FXML
    private Pane yourhall;
    @FXML
    private Pane yourtable;
    @FXML
    private Pane yourtowers;

    ArrayList<Circle> entrance;
    ArrayList<ArrayList<Circle>> hall;
    ArrayList<Polygon> table;
    ArrayList<Rectangle>towers;

    public BoardScene2Controller(GUI g){
        super(g);
        this.entrance=new ArrayList<>();
        this.hall=new ArrayList<>();
        this.table=new ArrayList<>();
        this.towers=new ArrayList<>();
    }

    @Override
    public void initialize(){
        entrance=(ArrayList)yourentrance.getChildren();
        hall=(ArrayList)yourhall.getChildren();
        table=(ArrayList)yourtable.getChildren();
        towers=(ArrayList)yourtowers.getChildren();

        setEntrance(entrance);
        setHall(hall);
        setProfessors(table);
    }
}
