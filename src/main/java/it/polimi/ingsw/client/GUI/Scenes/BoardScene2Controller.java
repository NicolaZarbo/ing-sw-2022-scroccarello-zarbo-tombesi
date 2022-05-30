package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
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
    @FXML
    private Polygon player2teacher1;
    @FXML
    private Polygon player2teacher2;
    @FXML
    private Polygon player2teacher3;
    @FXML
    private Polygon player2teacher4;
    @FXML
    private Polygon player2teacher5;
    @FXML
    private ImageView player2tower1;
    @FXML
    private ImageView player2tower2;
    @FXML
    private ImageView player2tower3;
    @FXML
    private ImageView player2tower4;
    @FXML
    private ImageView player2tower5;
    @FXML
    private ImageView player2tower6;
    @FXML
    private ImageView player2tower7;
    @FXML
    private ImageView player2tower8;




    private ArrayList<Circle> entrance;
    private ArrayList<ArrayList<Circle>> hall;
    private ArrayList<Polygon> table;
    private ArrayList<ImageView>towers;

    public BoardScene2Controller(){
        super();
        this.entrance=new ArrayList<>();
        this.hall=new ArrayList<>();
        this.table=new ArrayList<>();
        this.towers=new ArrayList<>();

    }

    @Override
    public void initialize(){
        buildEntrance(2);
        buildHall(2);
        buildTable(2);
        buildTowers(2);

        setEntrance(entrance);
        setHall(hall);
        setProfessors(table);
        setTowers(towers);
    }

    private void buildEntrance(int player){
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
    private void buildHall(int player){
        ArrayList<Circle> row;
        if(player==1){

        }
        else{
            row=new ArrayList<>(); //row 1
            row.add(player2board1c1r);
            row.add(player2board2c1r);
            row.add(player2board3c1r);
            row.add(player2board4c1r);
            row.add(player2board5c1r);
            row.add(player2board6c1r);
            row.add(player2board7c1r);
            row.add(player2board8c1r);
            row.add(player2board9c1r);
            row.add(player2board10c1r);
            hall.add(row);

            row=new ArrayList<>(); //row 2
            row.add(player2board1c2r);
            row.add(player2board2c2r);
            row.add(player2board3c2r);
            row.add(player2board4c2r);
            row.add(player2board5c2r);
            row.add(player2board6c2r);
            row.add(player2board7c2r);
            row.add(player2board8c2r);
            row.add(player2board9c2r);
            row.add(player2board10c2r);
            hall.add(row);

            row=new ArrayList<>(); //row 3
            row.add(player2board1c3r);
            row.add(player2board2c3r);
            row.add(player2board3c3r);
            row.add(player2board4c3r);
            row.add(player2board5c3r);
            row.add(player2board6c3r);
            row.add(player2board7c3r);
            row.add(player2board8c3r);
            row.add(player2board9c3r);
            row.add(player2board10c3r);
            hall.add(row);

            row=new ArrayList<>(); //row 4
            row.add(player2board1c4r);
            row.add(player2board2c4r);
            row.add(player2board3c4r);
            row.add(player2board4c4r);
            row.add(player2board5c4r);
            row.add(player2board6c4r);
            row.add(player2board7c4r);
            row.add(player2board8c4r);
            row.add(player2board9c4r);
            row.add(player2board10c4r);
            hall.add(row);

            row=new ArrayList<>(); //row 5
            row.add(player2board1c5r);
            row.add(player2board2c5r);
            row.add(player2board3c5r);
            row.add(player2board4c5r);
            row.add(player2board5c5r);
            row.add(player2board6c5r);
            row.add(player2board7c5r);
            row.add(player2board8c5r);
            row.add(player2board9c5r);
            row.add(player2board10c5r);
            hall.add(row);


        }
    }
    private void buildTable(int player){
        if(player==1){

        }
        else{
            table.add(player2teacher1);
            table.add(player2teacher2);
            table.add(player2teacher3);
            table.add(player2teacher4);
            table.add(player2teacher5);
        }
    }
    private void buildTowers(int player){
        if(player==1){

        }
        else{
            towers.add(player2tower1);
            towers.add(player2tower2);
            towers.add(player2tower3);
            towers.add(player2tower4);
            towers.add(player2tower5);
            towers.add(player2tower6);
            towers.add(player2tower7);
            towers.add(player2tower8);
        }
    }
}
