package model;

public enum TokenColor {
    red,yellow,green,blue,pink;

    private static TokenColor[] list = TokenColor.values();


    public static TokenColor getColor(int i) {
        return  list[i];
    }

    public static int listGetLastIndex() {
        return list.length - 1;
    }
}
