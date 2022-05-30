package it.polimi.ingsw.client.GUI.Scenes;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;


import java.util.ArrayList;

public class BoardScene2Controller extends BoardSceneController{
    @FXML
    private Circle player1hall1;
    @FXML
    private Circle player1hall2;
    @FXML
    private Circle player1hall3;
    @FXML
    private Circle player1hall4;
    @FXML
    private Circle player1hall5;
    @FXML
    private Circle player1hall6;
    @FXML
    private Circle player1hall7;
    @FXML
    private Circle player1hall8;
    @FXML
    private Circle player1hall9;
    @FXML
    private Circle player1board1c1r;
    @FXML
    private Circle player1board2c1r;
    @FXML
    private Circle player1board3c1r;
    @FXML
    private Circle player1board4c1r;
    @FXML
    private Circle player1board5c1r;
    @FXML
    private Circle player1board6c1r;
    @FXML
    private Circle player1board7c1r;
    @FXML
    private Circle player1board8c1r;
    @FXML
    private Circle player1board9c1r;
    @FXML
    private Circle player1board10c1r;
    @FXML
    private Circle player1board1c2r;
    @FXML
    private Circle player1board2c2r;
    @FXML
    private Circle player1board3c2r;
    @FXML
    private Circle player1board4c2r;
    @FXML
    private Circle player1board5c2r;
    @FXML
    private Circle player1board6c2r;
    @FXML
    private Circle player1board7c2r;
    @FXML
    private Circle player1board8c2r;
    @FXML
    private Circle player1board9c2r;
    @FXML
    private Circle player1board10c2r;
    @FXML
    private Circle player1board1c3r;
    @FXML
    private Circle player1board2c3r;
    @FXML
    private Circle player1board3c3r;
    @FXML
    private Circle player1board4c3r;
    @FXML
    private Circle player1board5c3r;
    @FXML
    private Circle player1board6c3r;
    @FXML
    private Circle player1board7c3r;
    @FXML
    private Circle player1board8c3r;
    @FXML
    private Circle player1board9c3r;
    @FXML
    private Circle player1board10c3r;
    @FXML
    private Circle player1board1c4r;
    @FXML
    private Circle player1board2c4r;
    @FXML
    private Circle player1board3c4r;
    @FXML
    private Circle player1board4c4r;
    @FXML
    private Circle player1board5c4r;
    @FXML
    private Circle player1board6c4r;
    @FXML
    private Circle player1board7c4r;
    @FXML
    private Circle player1board8c4r;
    @FXML
    private Circle player1board9c4r;
    @FXML
    private Circle player1board10c4r;
    @FXML
    private Circle player1board1c5r;
    @FXML
    private Circle player1board2c5r;
    @FXML
    private Circle player1board3c5r;
    @FXML
    private Circle player1board4c5r;
    @FXML
    private Circle player1board5c5r;
    @FXML
    private Circle player1board6c5r;
    @FXML
    private Circle player1board7c5r;
    @FXML
    private Circle player1board8c5r;
    @FXML
    private Circle player1board9c5r;
    @FXML
    private Circle player1board10c5r;
    @FXML
    private Polygon player1teacher1;
    @FXML
    private Polygon player1teacher2;
    @FXML
    private Polygon player1teacher3;
    @FXML
    private Polygon player1teacher4;
    @FXML
    private Polygon player1teacher5;
   @FXML
    private ImageView player1tower1;
    @FXML
    private ImageView player1tower2;
    @FXML
    private ImageView player1tower3;
    @FXML
    private ImageView player1tower4;
    @FXML
    private ImageView player1tower5;
    @FXML
    private ImageView player1tower6;
    @FXML
    private ImageView player1tower7;
    @FXML
    private ImageView player1tower8;

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
    @FXML
    private Text player1name;
    @FXML
    private Text player2name;

    private ArrayList<Circle> entrance1;
    private ArrayList<Circle> entrance2;
    private ArrayList<ArrayList<Circle>> hall1;
    private ArrayList<ArrayList<Circle>> hall2;
    private ArrayList<Polygon> table1;
    private ArrayList<Polygon> table2;
    private ArrayList<ImageView> towers1;
    private ArrayList<ImageView> towers2;

    public BoardScene2Controller(){
        super();
        this.entrance1 =new ArrayList<>();
        this.entrance2 =new ArrayList<>();
        this.hall1 =new ArrayList<>();
        this.hall2 =new ArrayList<>();
        this.table1 =new ArrayList<>();
        this.table2 =new ArrayList<>();
        this.towers1 =new ArrayList<>();
        this.towers2 =new ArrayList<>();

    }

    @Override
    public void initialize(){
        for(int i=1;i<3;i++){
            buildEntrance(i);
            buildHall(i);
            buildTable(i);
            buildTowers(i);
        }
        setEntrance(entrance1,1);
        setEntrance(entrance2,2);
        setHall(hall1,1);
        setHall(hall2,2);
        setProfessors(table1,1);
        setProfessors(table2,2);
        setTowers(towers1,1);
        setTowers(towers2,2);
        setPlayerName(player1name,1);
        setPlayerName(player2name,2);
    }

    private void buildEntrance(int player){
        if(player==1){
            entrance1.add(player1hall1);
            entrance1.add(player1hall2);
            entrance1.add(player1hall3);
            entrance1.add(player1hall4);
            entrance1.add(player1hall5);
            entrance1.add(player1hall6);
            entrance1.add(player1hall7);
            entrance1.add(player1hall8);
            entrance1.add(player1hall9);
        }
        else{
            entrance2.add(player2hall1);
            entrance2.add(player2hall2);
            entrance2.add(player2hall3);
            entrance2.add(player2hall4);
            entrance2.add(player2hall5);
            entrance2.add(player2hall6);
            entrance2.add(player2hall7);
            entrance2.add(player2hall8);
            entrance2.add(player2hall9);
        }

    }
    private void buildHall(int player){
        ArrayList<Circle> row;
        if(player==1){
            row=new ArrayList<>(); //row 1
            row.add(player1board1c1r);
            row.add(player1board2c1r);
            row.add(player1board3c1r);
            row.add(player1board4c1r);
            row.add(player1board5c1r);
            row.add(player1board6c1r);
            row.add(player1board7c1r);
            row.add(player1board8c1r);
            row.add(player1board9c1r);
            row.add(player1board10c1r);
            hall1.add(row);

            row=new ArrayList<>(); //row 2
            row.add(player1board1c2r);
            row.add(player1board2c2r);
            row.add(player1board3c2r);
            row.add(player1board4c2r);
            row.add(player1board5c2r);
            row.add(player1board6c2r);
            row.add(player1board7c2r);
            row.add(player1board8c2r);
            row.add(player1board9c2r);
            row.add(player1board10c2r);
            hall1.add(row);

            row=new ArrayList<>(); //row 3
            row.add(player1board1c3r);
            row.add(player1board2c3r);
            row.add(player1board3c3r);
            row.add(player1board4c3r);
            row.add(player1board5c3r);
            row.add(player1board6c3r);
            row.add(player1board7c3r);
            row.add(player1board8c3r);
            row.add(player1board9c3r);
            row.add(player1board10c3r);
            hall1.add(row);

            row=new ArrayList<>(); //row 4
            row.add(player1board1c4r);
            row.add(player1board2c4r);
            row.add(player1board3c4r);
            row.add(player1board4c4r);
            row.add(player1board5c4r);
            row.add(player1board6c4r);
            row.add(player1board7c4r);
            row.add(player1board8c4r);
            row.add(player1board9c4r);
            row.add(player1board10c4r);
            hall1.add(row);

            row=new ArrayList<>(); //row 5
            row.add(player1board1c5r);
            row.add(player1board2c5r);
            row.add(player1board3c5r);
            row.add(player1board4c5r);
            row.add(player1board5c5r);
            row.add(player1board6c5r);
            row.add(player1board7c5r);
            row.add(player1board8c5r);
            row.add(player1board9c5r);
            row.add(player1board10c5r);
            hall1.add(row);
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
            hall2.add(row);

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
            hall2.add(row);

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
            hall2.add(row);

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
            hall2.add(row);

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
            hall2.add(row);
        }
    }
    private void buildTable(int player){
        if(player==1){
            table1.add(player1teacher1);
            table1.add(player1teacher2);
            table1.add(player1teacher3);
            table1.add(player1teacher4);
            table1.add(player1teacher5);
        }
        else{
            table2.add(player2teacher1);
            table2.add(player2teacher2);
            table2.add(player2teacher3);
            table2.add(player2teacher4);
            table2.add(player2teacher5);
        }
    }
    private void buildTowers(int player){
        if(player==1){
            towers1.add(player1tower1);
            towers1.add(player1tower2);
            towers1.add(player1tower3);
            towers1.add(player1tower4);
            towers1.add(player1tower5);
            towers1.add(player1tower6);
            towers1.add(player1tower7);
            towers1.add(player1tower8);
        }
        else{
            towers2.add(player2tower1);
            towers2.add(player2tower2);
            towers2.add(player2tower3);
            towers2.add(player2tower4);
            towers2.add(player2tower5);
            towers2.add(player2tower6);
            towers2.add(player2tower7);
            towers2.add(player2tower8);
        }
    }

    public void sendToHand(){
        gui.showHand();
    }
}
