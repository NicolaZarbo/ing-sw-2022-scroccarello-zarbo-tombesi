package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.view.CentralView;

import java.util.Arrays;

/**The class which prints the clouds of the game.*/
public class CloudPrinter implements Printer{
    private static final String xx= """
              ________\s
             (         )
            ( R   g     )
            ( y   b     )
             (   P     )
              (_______)""";

    /**It prints on the screen the clouds of the game with the number of tokens for color on them.
     * @param view the central view of the player*/
    public static String print(CentralView view){
        StringBuilder out = new StringBuilder();
        String[] rows= new String[7];
        Arrays.fill(rows,"");
        int id=0;
        for (Integer[] cloud :view.getClouds()) {
            id++;
            int [] colorStud = cloudStudentForColor(cloud);
            rows[0]+="  ________      \t";
            rows[1]+=" (         )    \t";
            rows[2]+="( "+RED+"R:"+ colorStud[0] +"  "+GREEN+"G:"+colorStud[2]+"  "+RST+")   \t";
            rows[3]+="( "+YELLOW+"Y:"+colorStud[1]+"  "+BLUE+"B:"+colorStud[3]+"  "+RST+")   \t";
            rows[4]+=" (   "+PINK+"P:"+colorStud[4]+"   "+RST+")    \t";
            rows[5]+="  (_______)     \t";
            rows[6]+="      "+Printer.padWithSpaces(String.valueOf(id),9)+"\t";
        }
        for (String row:rows) {
            out.append(row).append("\n");
        }
        return out.toString();
    }

    /**It extracts the number of students for color given the cloud.
     * @param cloud the list of student tokens' ids
     * @return the number of students for color on the cloud*/
    private static int[] cloudStudentForColor(Integer[] cloud){
        int[] studForColor= new int[5];
        Arrays.fill(studForColor,0);
        for (Integer studId: cloud ){
            if(studId!=-1)
                studForColor[studId/26] ++;
        }
        return studForColor;
    }
}
