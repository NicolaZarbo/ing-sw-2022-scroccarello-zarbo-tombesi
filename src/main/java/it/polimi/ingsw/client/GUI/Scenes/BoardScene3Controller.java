package it.polimi.ingsw.client.GUI.Scenes;


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
    }

    @Override
    public void initialize() {
        for(int i=0;i<3;i++){
            buildEntrance(i);
            setEntrance(entrance0List,i-1);
        }


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
}
