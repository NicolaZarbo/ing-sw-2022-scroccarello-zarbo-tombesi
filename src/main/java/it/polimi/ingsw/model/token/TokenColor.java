package it.polimi.ingsw.model.token;

public enum TokenColor {
    red,yellow,green,blue,pink;

    private static TokenColor[] list = TokenColor.values();


    public static TokenColor getColor(int i) {
        return  list[i];
    }

    public static int getIndex(TokenColor i){
        int j=-1;
        for(int k=0;k<list.length;k++)
            if(TokenColor.getColor(k).equals(i))
                j=k;

            return j;
    }

    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
