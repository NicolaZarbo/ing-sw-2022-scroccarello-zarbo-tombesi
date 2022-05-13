package it.polimi.ingsw.view.CLI.printers;

import it.polimi.ingsw.view.CentralView;

import java.util.Arrays;

public class CloudPrinter implements Printer{
    private static final String xx= """
              ________\s
             (         )
            ( R   g     )
            ( y   b     )
             (   P     )
              (_______)""";
    public static String print(CentralView view){
        StringBuilder out = new StringBuilder();
        String[] rows= new String[7];
        Arrays.fill(rows,"");
        int id=0;
        for (Integer[] cloud :view.getClouds()) {
            id++;
            rows[0]+="  ________      \t";
            rows[1]+=" (         )    \t";
            rows[2]+="( "+RED+"R:"+cloud[0]+"  "+GREEN+"G:"+cloud[2]+"    "+RST+")   \t";
            rows[3]+="( "+YELLOW+"Y:"+cloud[1]+"   "+BLUE+"B:"+cloud[4]+"   "+RST+" )   \t";
            rows[4]+=" (   "+PINK+"P:"+cloud[5]+"     "+RST+")    \t";
            rows[5]+="  (_______)     \t";
            rows[6]+="        "+id+"        \t";
        }
        for (String row:rows) {
            out.append(row).append("\n");
        }
        return out.toString();
    }
}
