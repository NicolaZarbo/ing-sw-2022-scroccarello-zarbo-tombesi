package it.polimi.ingsw.view.CLI.printers;

public interface Printer {
    static String RED="\u001B[31m";
    static String BLUE="\u001B[34m";
    static String YELLOW="\u001B[33m";
    static String GREEN="\u001B[32m";
    static String PINK="\u001B[35m";
    static String RST="\u001B[0m";
    static String CYAN="\u001B[36m";
    static String WHITE_BKG="\u001B[47m";
    static String BLACK="\u001B[30m";

    public static String mergeRows(String[] rowsForOutput){
        StringBuilder out= new StringBuilder();
        for (String s:rowsForOutput) {
            out.append(s);
            out.append("\n");
        }
        return out.toString();
    }
    public static String getCliColor(int i){
        return switch (i){
            default -> RST;
            case 0 -> RED;
            case 1-> YELLOW;
            case 2 -> GREEN;
            case 3 -> BLUE;
            case 4-> PINK;
            case -1 -> CYAN;
        };
    }
    public static String padWithSpaces(String in, int dimensionNeeded){
        StringBuilder out = new StringBuilder(in);
        while(out.length()<dimensionNeeded)
            out.append("\s");
        return out.toString();
    }
}
