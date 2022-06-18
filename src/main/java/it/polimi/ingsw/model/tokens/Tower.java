package it.polimi.ingsw.model.tokens;

import it.polimi.ingsw.enumerations.TowerColor;

/**The tower token of the game. It is related to the player's team color. It is used to conquer an island, otherwise it can be found on the board's <b>towers</b>> section. The game ends if the player has no towers left.
 * @see it.polimi.ingsw.model.Board
 * @see it.polimi.ingsw.model.Island*/
public class Tower {
    private final TowerColor color;
    private final int id;

    /**It builds the tower token.
     * @param color tower color, as well as player's team color
     * @param idTower id of the tower*/
    public Tower(TowerColor color, int idTower){
        this.color=color;
        this.id= idTower ;
    }

    /**@return the id of the tower*/
    public int getId() {
        return id;
    }

    /**@return the color of the tower*/
    public TowerColor getColor() {
        return color;
    }
}
