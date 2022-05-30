package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class BoardScene2Controller extends BoardSceneController{

    @FXML
    private Circle player2hall1;
    @FXML
    private Circle player2hall2;
    @FXML
    private Circle player2hall3;
    @FXML
    private Circle player2hall4;
    @FXML
    private Circle player2hall5;
    @FXML
    private Circle player2hall6;
    @FXML
    private Circle player2hall7;
    @FXML
    private Circle player2hall8;
    @FXML
    private Circle player2hall9;
    @FXML
    private Circle player2board1c1r;
    @FXML
    private Circle player2board2c1r;
    @FXML
    private Circle player2board3c1r;
    @FXML
    private Circle player2board4c1r;
    @FXML
    private Circle player2board5c1r;
    @FXML
    private Circle player2board6c1r;
    @FXML
    private Circle player2board7c1r;
    @FXML
    private Circle player2board8c1r;
    @FXML
    private Circle player2board9c1r;
    @FXML
    private Circle player2board10c1r;
    @FXML
    private Circle player2board1c2r;
    @FXML
    private Circle player2board2c2r;
    @FXML
    private Circle player2board3c2r;
    @FXML
    private Circle player2board4c2r;
    @FXML
    private Circle player2board5c2r;
    @FXML
    private Circle player2board6c2r;
    @FXML
    private Circle player2board7c2r;
    @FXML
    private Circle player2board8c2r;
    @FXML
    private Circle player2board9c2r;
    @FXML
    private Circle player2board10c2r;
    @FXML
    private Circle player2board1c3r;
    @FXML
    private Circle player2board2c3r;
    @FXML
    private Circle player2board3c3r;
    @FXML
    private Circle player2board4c3r;
    @FXML
    private Circle player2board5c3r;
    @FXML
    private Circle player2board6c3r;
    @FXML
    private Circle player2board7c3r;
    @FXML
    private Circle player2board8c3r;
    @FXML
    private Circle player2board9c3r;
    @FXML
    private Circle player2board10c3r;
    @FXML
    private Circle player2board1c4r;
    @FXML
    private Circle player2board2c4r;
    @FXML
    private Circle player2board3c4r;
    @FXML
    private Circle player2board4c4r;
    @FXML
    private Circle player2board5c4r;
    @FXML
    private Circle player2board6c4r;
    @FXML
    private Circle player2board7c4r;
    @FXML
    private Circle player2board8c4r;
    @FXML
    private Circle player2board9c4r;
    @FXML
    private Circle player2board10c4r;
    @FXML
    private Circle player2board1c5r;
    @FXML
    private Circle player2board2c5r;
    @FXML
    private Circle player2board3c5r;
    @FXML
    private Circle player2board4c5r;
    @FXML
    private Circle player2board5c5r;
    @FXML
    private Circle player2board6c5r;
    @FXML
    private Circle player2board7c5r;
    @FXML
    private Circle player2board8c5r;
    @FXML
    private Circle player2board9c5r;
    @FXML
    private Circle player2board10c5r;

    private ArrayList<Circle> entrance;
    private ArrayList<ArrayList<Circle>> hall;
    private ArrayList<Polygon> table;
    private ArrayList<Rectangle>towers;

    public BoardScene2Controller(){
        super();
        this.entrance=new ArrayList<>();
        this.hall=new ArrayList<>();
        this.table=new ArrayList<>();
        this.towers=new ArrayList<>();

        buildEntrance(entrance,2);
        buildHall(hall,2);

    }

    @Override
    public void initialize(){
        setEntrance(entrance);

    }

    private void buildEntrance(ArrayList<Circle>entrance,int player){
        if(player==1){

        }
        else{
            entrance.add(player2hall1);
            entrance.add(player2hall2);
            entrance.add(player2hall3);
            entrance.add(player2hall4);
            entrance.add(player2hall5);
            entrance.add(player2hall6);
            entrance.add(player2hall7);
            entrance.add(player2hall8);
            entrance.add(player2hall9);
        }

    }
    private void buildHall(ArrayList<ArrayList<Circle>>hall,int player){
        if(player==1){

        }
        else{

        }
    }
}
