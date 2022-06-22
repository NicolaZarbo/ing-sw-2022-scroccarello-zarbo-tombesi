package it.polimi.ingsw.enumerations;
/**The four magicians of the game. They are involved in player's customization since they  are associated to the assistant card.*/
public enum Mage {
    /**The wizard.*/
    mage1,
    /**The king.*/
    mage2,
    /**The witch.*/
    mage3,
    /**The sage.*/
    mage4;


    private static final Mage[] list = Mage.values();

    /**@param i index of the magician
     * @return the magician of the related index from the list*/
    public static Mage getMage(int i) {
        return list[i];
    }

    /**@return the last index of the list of magicians*/
    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
