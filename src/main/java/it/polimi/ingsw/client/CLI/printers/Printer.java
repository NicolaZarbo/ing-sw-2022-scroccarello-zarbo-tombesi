package it.polimi.ingsw.client.CLI.printers;

/**The generic printer interface implemented by every class involved in printing Command Line Interface of the player.
 * It contains all the necessary colors and the methods to ensure correct visualization of information on the screen. Notice that every managed information is in string format.*/
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

    /**It is used to merge the rows that are dynamically built to show to the player some game info.
     * @param rowsForOutput the rows to merge together*/
    static String mergeRows(String[] rowsForOutput){
        StringBuilder out= new StringBuilder();
        for (String s:rowsForOutput) {
            out.append(s);
            out.append("\n");
        }
        return out.toString();
    }
    /** @return The color corresponding to an integer value
     * @param i the color index*/
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

    /** It is used to add some space at the end of a certain string to ensure correct visualization.
     * @param in the string to adjust
     * @param dimensionNeeded the dimension to which extend the string*/
    static String padWithSpaces(String in, int dimensionNeeded){
        StringBuilder out = new StringBuilder(in);
        while(out.length()<dimensionNeeded)
            out.append("\s");
        return out.toString();
    }
}
