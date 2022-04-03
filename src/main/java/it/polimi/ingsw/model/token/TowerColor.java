package it.polimi.ingsw.model.token;

public enum TowerColor {
    black,white,grey;


    private static TowerColor[] list = TowerColor.values();


    public static TowerColor getColor(int i) {
        return list[i];
    }

    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
