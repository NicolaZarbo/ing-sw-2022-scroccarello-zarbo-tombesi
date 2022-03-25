package it.polimi.ingsw.model;

public class Tower {
    TowerColor col;
    int id;

    public Tower(TowerColor color, int idTower){
        this.col=color;
        this.id= idTower ;
    }

    public int getId() {
        return id;
    }

    public TowerColor getCol() {
        return col;
    }
}
