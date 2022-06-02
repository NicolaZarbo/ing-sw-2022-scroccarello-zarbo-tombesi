package it.polimi.ingsw.client.GUI.Scenes;


import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

public class BoardScene3Controller extends BoardSceneController{

    public Pane yourentrance;
    public Pane yourhall;
    public Pane yourtable;
    public Pane yourtowers;
    public Pane other1entrance;
    public Pane other1hall;
    public Pane other1table;
    public Pane other1towers;
    public Pane other2entrance;
    public Pane other2hall;
    public Pane other2table;
    public Pane other2towers;

    private ArrayList<ImageView> towers1;
    private ArrayList<ImageView> towers2;
    private ArrayList<ImageView> towers0; //0 is the ID of "you"
    private ArrayList<Polygon> table1;
    private ArrayList<Polygon> table2;
    private ArrayList<Polygon> table0;
    private ArrayList<Circle> entrance1List;
    private ArrayList<Circle> entrance2List;
    private ArrayList<Circle> entrance0List;
    private ArrayList<ArrayList<Circle>> diningRoom1;
    private ArrayList<ArrayList<Circle>> diningRoom2;
    private ArrayList<ArrayList<Circle>> diningRoom0;

    public BoardScene3Controller(){
        super();
        this.diningRoom0=new ArrayList<>();
        this.diningRoom1=new ArrayList<>();
        this.diningRoom2=new ArrayList<>();
    }

    @Override
    public void initialize() {
        for(int i=0;i<3;i++){
            buildEntrance(i);
            buildHall(i);
            buildTable(i);
            buildTowers(i);
        }
        int other1=-1;
        int other2=-1;
        for(SimplifiedPlayer p : gui.getGame().getPlayers()){
            if(p!=gui.getGame().getPersonalPlayer()){
                if(other1==-1){
                    other1=p.getId();
                }
                else{
                    other2=p.getId();
                }
            }
        }
        setEntrance(entrance0List,gui.getGame().getPersonalPlayer().getId()+1);
        setEntrance(entrance1List,other1+1);
        setEntrance(entrance2List,other2+1);

        setHall(diningRoom0,gui.getGame().getPersonalPlayer().getId()+1);
        setHall(diningRoom1,other1+1);
        setHall(diningRoom2,other2+1);

        setProfessors(table0,gui.getGame().getPersonalPlayer().getId()+1);
        setProfessors(table1,other1+1);
        setProfessors(table2,other2+1);

        setTowers(towers0,gui.getGame().getPersonalPlayer().getId()+1);
        setTowers(towers1,other1+1);
        setTowers(towers2,other2+1);
    }

    @Override
    protected void showMoveButton() {

    }

    @Override
    protected void hideMoveButtons() {

    }

    private void buildEntrance(int player){
       switch(player){
           case 0->{
               entrance0List= new ArrayList<>();
               for (Node student : yourentrance.getChildren()) {
                   entrance0List.add((Circle)student);
               }
           }
           case 1->{
               entrance1List= new ArrayList<>();
               for (Node student : other1entrance.getChildren()) {
                   entrance1List.add((Circle)student);
               }
           }

            case 2->{
                entrance2List=new ArrayList<>();
                for (Node student : other2entrance.getChildren()) {
                    entrance2List.add((Circle)student);
            }
        }
       }
    }
    private void buildHall(int player){
        ArrayList<Circle> diningRow = new ArrayList<>();
        Pane diningReference=null;
        ArrayList<ArrayList<Circle>> diningContainer=new ArrayList<>();
        switch(player) {
            case 0-> {
                diningContainer=diningRoom0;
                diningReference=yourhall;
            }
            case 1->{
                diningContainer=diningRoom1;
                diningReference=other1hall;
            }
            case 2-> {
                diningContainer = diningRoom2;
                diningReference = other2hall;
            }
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
        switch(player) {
            case 0 -> {
                table0 = new ArrayList<>();
                for (Node teacher : yourtable.getChildren()) {
                    table0.add((Polygon) teacher);
                }
            }

            case 1->{
                    table1 = new ArrayList<>();
                    for (Node teacher : other1table.getChildren()) {
                        table1.add((Polygon) teacher);
                    }
            }
            case 2->{
                table2 = new ArrayList<>();
                for (Node teacher : other2table.getChildren()) {
                    table2.add((Polygon) teacher);
                }
            }
        }
    }

    private void buildTowers(int player){
        switch(player){
            case 0->{
            towers0=new ArrayList<>();
            for (Node tower:yourtowers.getChildren()) {
                towers0.add((ImageView) tower);
            }
        }
            case 1->{
                towers1=new ArrayList<>();
                for (Node tower:other1towers.getChildren()) {
                    towers1.add((ImageView) tower);
                }
        }
            case 2->{
                towers2=new ArrayList<>();
                for (Node tower:other2towers.getChildren()) {
                    towers2.add((ImageView) tower);
                }
            }
    }
    }

}
