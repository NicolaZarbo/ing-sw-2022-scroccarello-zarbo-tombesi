package it.polimi.ingsw.client.CLI.printers;

public interface Printer {
    String RED="\u001B[31m";
    String BLUE="\u001B[34m";
    String YELLOW="\u001B[33m";
    String GREEN="\u001B[32m";
    String PINK="\u001B[35m";
    String RST="\u001B[0m";
    String CYAN="\u001B[36m";
    String WHITE_BKG="\u001B[47m";
    String YELLOW_BKG="\u001B[43m";
    String BLACK="\u001B[30m";
    String CYAN_BKG = "\u001B[46m";
    String BR_GREEN_BKG ="\u001B[102m";
    String DR_GRAY = "\u001B[90m";
    String DR_GRAY_BKG = "\u001B[100m";
    String BR_PINK="\u001B[95m";
    String BR_WHITE_BKG="\u001B[107m";
    String BR_WHITE="\u001B[97m";
    /** used to merge the rows that are dynamically built to show to the player some game info*/
    static String mergeRows(String[] rowsForOutput){
        StringBuilder out= new StringBuilder();
        for (String s:rowsForOutput) {
            out.append(s);
            out.append("\n");
        }
        return out.toString();
    }
    /** @return The color corresponding to an integer value
     *  */
    static String getCliColor(int i){
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
    /** Used to add some spacing to ensure correct visuals even with player dependent text*/
    static String padWithSpaces(String in, int dimensionNeeded){
        StringBuilder out = new StringBuilder(in);
        while(out.length()<dimensionNeeded)
            out.append("\s");
        return out.toString();
    }
}
