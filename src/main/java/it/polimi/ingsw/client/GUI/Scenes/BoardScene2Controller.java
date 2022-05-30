package it.polimi.ingsw.client.GUI.Scenes;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import java.util.ArrayList;


public class BoardScene2Controller extends BoardSceneController{
    public Pane entrance_1;
    public Pane dining_1;
    public Pane yourhall;   //you can omit the fxml annotation if the field is public
    public Pane yourentrance;
    public Pane yourtable;
    public Pane teachers_1;
    public Pane towers_1;
    public Pane yourtowers;

    ArrayList<ImageView> towers1;
    ArrayList<ImageView> towers2;
    ArrayList<Polygon> table1;
    ArrayList<Polygon> table2;
    ArrayList<Circle> entrance1List;
    ArrayList<ArrayList<Circle>> diningRoom1;
    ArrayList<Circle> entrance2List;
    ArrayList<ArrayList<Circle>> diningRoom2;

    @FXML
    private Text player1name;
    @FXML
    private Text player2name;

    public BoardScene2Controller(){
        super();
        diningRoom1=new ArrayList<>();
        diningRoom2=new ArrayList<>();
    }

    @Override
    public void initialize(){

        for(int i=1;i<3;i++){
            buildEntrance(i);
            buildHall(i);
            buildTable(i);
            buildTowers(i);
        }
        setEntrance(entrance1List,1);
        setEntrance(entrance2List,2);
        setHall(diningRoom1,1);
        setHall(diningRoom2,2);
        setProfessors(table1,1);
        setProfessors(table2,2);
        setTowers(towers1,1);
        setTowers(towers2,2);
        setPlayerName(player1name,1);
        setPlayerName(player2name,2);
    }

    private void buildEntrance(int player){
        if(player==1){
            entrance1List= new ArrayList<>();
            for (Node student:entrance_1.getChildren()) {
                entrance1List.add((Circle)student);
            }
        }
        else{
            entrance2List=new ArrayList<>();
            for (Node student:yourentrance.getChildren()) {
                entrance2List.add((Circle)student);
            }
        }

    }
    private void buildHall(int player){
        //ArrayList<Circle> row;
        ArrayList<Circle> diningRow = new ArrayList<>();
        Pane diningReference;
        ArrayList<ArrayList<Circle>> diningContainer;
        if(player==1) {
            diningContainer=diningRoom1;
            diningReference=dining_1;
        }
        else {
            diningContainer=diningRoom2;
            diningReference=yourhall;
        }

        for (Node row: diningReference.getChildren()) {
            for (Node student:((Pane) row).getChildren()) {
                diningRow.add((Circle)student);
            }
            diningContainer.add(diningRow);
            diningRow=new ArrayList<>();
        }
    }
    private void buildTable(int player){

        if(player==1){
            table1=new ArrayList<>();
            for (Node teacher :teachers_1.getChildren()) {
                table1.add((Polygon) teacher);
            }

        }
        else{
            table2=new ArrayList<>();
            for (Node teacher :yourtable.getChildren()) {
                table2.add((Polygon) teacher);
            }
        }
    }
    private void buildTowers(int player){
        if(player==1){
            towers1=new ArrayList<>();
            for (Node tower:towers_1.getChildren()) {
                towers1.add((ImageView) tower);
            }

        }
        else{
            towers2=new ArrayList<>();
            for (Node tower:yourtowers.getChildren()) {
                towers2.add((ImageView) tower);
            }
        }
    }

    public void sendToHand(){
        gui.showHand();
    }
}
