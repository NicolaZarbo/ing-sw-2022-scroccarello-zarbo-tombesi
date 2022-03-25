package it.polimi.ingsw.model;
import java.util.ArrayList;

public class Island {
    private int ID;
    private ArrayList<Student> student;
    private Tower tower;

    public Island(int id, TowerColor color, int idTorre){
        this.ID=id;
        this.student =new ArrayList<Student>();
        this.tower =new Tower(color, idTorre);
    }
    public int getID() {return this.ID;}

    public Tower getTower(){
        return this.tower;
    }
    public void setTower(Tower t){
        this.tower=t;
    }


}