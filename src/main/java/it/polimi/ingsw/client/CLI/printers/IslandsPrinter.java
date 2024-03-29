package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**The class which prints the islands of the game.*/
public class IslandsPrinter implements Printer{
String yo = """
         ________\s
        /        \\
        |        |
        |        |
        |        |
        \\________/""";

/**It prints the islands of the game with the students for color and towers on them, and also their sub-islands.
 * @param view the player's central view*/
public static String print(CentralView view){
        StringBuilder out= new StringBuilder("\n");
        String[] rows= new String[9];
        String[] preRows= new String[7];
        Arrays.fill(rows,"");
        String tColor;
        int escapeCounter=0;
        int size=view.getIslands().size();
        for (SimplifiedIsland island:view.getIslands()) {
            if(escapeCounter==size/2){
                out.append(Printer.mergeRows(rows));
                Arrays.fill(rows,"");
                out.append("\n \n");
            }
            escapeCounter++;
            tColor=Printer.padWithSpaces(towerColor(island.getTowerColor()),6);
            int[] studentForColor= IslandsPrinter.islandColorStudents(island);
            int numberOfTower=island.getEntireNumberOfTower();
            if(island.getIslandId()>9)
                preRows[0]="Island - "+island.getIslandId()+"       ";
            else preRows[0]="Island - "+island.getIslandId()+"        ";
            preRows[1]=" _________        ";
            preRows[2]="/ "+tColor+" \\       ";
            preRows[3]="|T#:"+numberOfTower+" "+PINK+"P:"+studentForColor[4]+""+RST+" |       ";
            preRows[4]="| "+RED+"R:"+studentForColor[0]+" "+YELLOW+"Y:"+studentForColor[1]+""+RST+" |       ";
            preRows[5]="| "+GREEN+"G:"+studentForColor[2]+" "+BLUE+"B:"+studentForColor[3]+""+RST+" |       ";
            if(view.getIslands().get(view.getMother()).getIslandId()==island.getIslandId()){
                preRows[6]="\\__"+CYAN+"Mother"+RST+"_/       ";}
            else preRows[6]="\\_________/       ";
            if(escapeCounter>(size/2))
                for (int i = 0; i < 7; i++) {
                    preRows[i]=reverseString(preRows[i]);
                }

            if(escapeCounter<=(size/2) ) {
                if (escapeCounter == 1 || escapeCounter == (size / 2))
                    addLowerIsland(rows, preRows);
                else addHigherIsland(rows,preRows);
            }
            if(escapeCounter>(size/2) ){
                if(escapeCounter== size || escapeCounter==(size/2)+1)
                    addHigherIsland(rows, preRows);
                else addLowerIsland(rows,preRows);
                }
        }
        out.append(reversedMerge(rows));
        return out.toString();
    }

    /**It adds to the island to print its lower island.
     * @param rows the strings to print the island
     * @param preRows the string to print the lower island*/
    private static void addLowerIsland(String [] rows, String []preRows) {
        rows[0] += "                  ";
        rows[1] += "                  ";
        for (int i = 0; i < 7; i++) {
            rows[i + 2] += preRows[i];
        }
    }

    /**It adds to the island to print its higher island.
     * @param rows the strings to print the island
     * @param preRows the string to print the higher island*/
    private static void addHigherIsland(String [] rows, String []preRows){
        rows[8] += "                  ";
        rows[7] += "                  ";
        for (int i = 0; i < 7; i++) {
            rows[i] += preRows[i];
        }
    }

    /**It reverses the string to print row by row.
     * @param rowsForOutput rows to print out reverted*/
    private static String reversedMerge(String[] rowsForOutput){
        StringBuilder out= new StringBuilder();
        for (String s:rowsForOutput) {
            out.append(reverseString(s));
            out.append("\n");
        }
        return out.toString();
    }

    /**It reverses a certain string char by char.
     * @param str the string to revert*/
    private static String reverseString(String str){
        char[] ch =str.toCharArray();
        StringBuilder rev= new StringBuilder();
        for(int i=ch.length-1;i>=0;i--){
            rev.append(ch[i]);
        }
        return rev.toString();
    }

    /**It extracts the number of students for color given the island.
     * @param island the list of student tokens' ids
     * @return the number of students for color on the island*/
    private static int[] islandColorStudents(SimplifiedIsland island){
        int[] studForColor= new int[5];
        Arrays.fill(studForColor,0);
        List<Integer> students= new ArrayList<>(island.getStudents());
        for (SimplifiedIsland subIsland:island.getSubIslands()) {
            students.addAll(subIsland.getStudents());
        }
        for (Integer studId:students) {
            studForColor[studId/26] ++;
        }
        return studForColor;
    }


    /**@param tColor index of tower color
     * @return the string to print out related to the tower color*/
    private static String towerColor(int tColor){
        if(tColor==0)
            return BLACK+CYAN_BKG+" BLACK "+RST;
        if(tColor==1)
            return BLACK+CYAN_BKG+" WHITE "+RST;
        if(tColor==2)
            return BLACK+CYAN_BKG+" GRAY "+RST;
        return "       ";
    }
}
