package it.polimi.ingsw.model.token;

import it.polimi.ingsw.enumerations.TowerColor;

public class Tower {
    private final TowerColor color;
    private final int id;

    public Tower(TowerColor color, int idTower){
        this.color=color;
        this.id= idTower ;
    }

    public int getId() {
        return id;
    }

    public TowerColor getColor() {
        return color;
    }
}
