package it.polimi.ingsw.enumerations;

/**The possible colors of the tokens. They are related to students and professors.*/
public enum TokenColor {
    red,yellow,green,blue,pink;

    private static final TokenColor[] list = TokenColor.values();


    /**@param i index of the list
     * @return the relative color of the index*/
    public static TokenColor getColor(int i) {
        return  list[i];
    }

    /**@param i one of the colors
     * @return the relative index of the color in the list*/
    public static int getIndex(TokenColor i){
        int j=-1;
        for(int k=0;k<list.length;k++)
            if(TokenColor.getColor(k).equals(i))
                j=k;
        return j;
    }

    /**@return the last index of colors' list of the game*/
    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
