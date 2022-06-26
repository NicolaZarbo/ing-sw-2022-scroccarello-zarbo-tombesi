package it.polimi.ingsw.client.CLI.printers;

import java.util.Arrays;

/**The class which prints the cards in the hand of the player.*/
public class CardPrinter implements Printer{
    private final String chooseACard=PINK+"""

                ░█▀▀░█░█░█▀█░█▀▀░█▀▀░░░█▀█░░░█▀▀░█▀█░█▀▄░█▀▄
                ░█░░░█▀█░█░█░▀▀█░█▀▀░░░█▀█░░░█░░░█▀█░█▀▄░█░█
                ░▀▀▀░▀░▀░▀▀▀░▀▀▀░▀▀▀░░░▀░▀░░░▀▀▀░▀░▀░▀░▀░▀▀░"""+RST;

    /**It prints the cards in the hand of the player.
     * @param cards the list of available and unavailable cards to print in the right place on the screen*/
    public static String print(boolean[] cards){
        StringBuilder out= new StringBuilder();
        int id=0;
        String[] rows = new String[9];
        Arrays.fill(rows,"");
        rows[0]="\n";
        for (Boolean cc:cards) {
            id++;
            if(cc) {
                rows[1] += " _______    ";
                rows[2] += "|       |   ";
                rows[3] += "| M " + CYAN + (id + 1) / 2 + RST + "   |   ";
                rows[4] += "|       |   ";
                if (id < 10)
                    rows[5] += "| T " + CYAN + id + RST + "   |   ";
                else
                    rows[5] += "| T " + CYAN + id + RST + "  |   ";
                rows[6] += "|       |   ";
                rows[7] += "|_______|   ";
                rows[8] += "    " + id + "       ";
            }
        }
        for (String s:rows) {
            out.append(s).append("\n");
        }
        return out.toString();
    }
}
