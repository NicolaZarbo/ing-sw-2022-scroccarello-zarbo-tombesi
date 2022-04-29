package it.polimi.ingsw.client.printers;

import java.util.ArrayList;
import java.util.Arrays;

public class CardPrinter implements Printer{
    public static String print(ArrayList<Boolean> cards){
        StringBuilder out= new StringBuilder(PINK+"""

                ░█▀▀░█░█░█▀█░█▀▀░█▀▀░░░█▀█░░░█▀▀░█▀█░█▀▄░█▀▄
                ░█░░░█▀█░█░█░▀▀█░█▀▀░░░█▀█░░░█░░░█▀█░█▀▄░█░█
                ░▀▀▀░▀░▀░▀▀▀░▀▀▀░▀▀▀░░░▀░▀░░░▀▀▀░▀░▀░▀░▀░▀▀░"""+RST);
        int id=0;
        String[] rows = new String[9];
        Arrays.fill(rows,"");
        rows[0]="\n";
        for (Boolean cc:cards) {
            id++;
            rows[1]+=" _______    ";
            rows[2]+="|       |   ";
            rows[3]+="| M "+CYAN+(id+1)/2+RST+"   |   ";
            rows[4]+="|       |   ";
            if(id<10)
                rows[5]+="| T "+CYAN+id+RST+"   |   ";
            else
                rows[5]+="| T "+CYAN+id+RST+"  |   ";
            rows[6]+="|       |   ";
            rows[7]+="|_______|   ";
            rows[8]+="    "+id+"       ";

        }
        for (String s:rows) {
            out.append(s).append("\n");
        }



        return out.toString();
    }
}
