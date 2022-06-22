package it.polimi.ingsw.enumerations;

/**The possible colors of the towers. They are related to the players' team. Only in 3 players game (1vs1vs1) the colors are 3, in the other formats the available colors are 2.*/
public enum TowerColor {
    black,white,grey;


    private static final TowerColor[] list = TowerColor.values();

    /**@param i the index of the list of colors
     * @return the color of the index*/
    public static TowerColor getColor(int i) {
        return list[i];
    }

    /**@return the last index of the list of colors*/
    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
