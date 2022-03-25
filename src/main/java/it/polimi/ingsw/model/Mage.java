package it.polimi.ingsw.model;
public enum Mage {
    mage1,mage2,mage3,mage4;

    private static Mage[] list = Mage.values();


    public static Mage getMage(int i) {
        return list[i];
    }

    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
