package it.polimi.ingsw.view.CLI.printers;

import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.CLI.objects.SimplifiedIsland;

import java.util.Arrays;

public class IslandsPrinter implements Printer{
String yo =" ________ \n" +
        "/        \\\n" +
        "|        |\n" +
        "|        |\n" +
        "|        |\n" +
        "\\________/";
    public static String print(CentralView view){
        StringBuilder out= new StringBuilder("\n");
        String[] rows= new String[7];
        Arrays.fill(rows,"");
        int escapeCounter=0;
        for (SimplifiedIsland island:view.getIslands()) {
            if(escapeCounter>=view.getIslands().size()/2){
                out.append(Printer.mergeRows(rows));
                Arrays.fill(rows,"");
                out.append("\n \n");
                escapeCounter=0;
            }
            escapeCounter++;

            int[] studentForColor= IslandsPrinter.islandColorStudents(island);
            rows[0]+="Island : "+island.getIslandId()+"  \t";
            rows[1]+=" _________    \t";
            rows[2]+="/         \\   \t";
            rows[3]+="|T°:"+island.getNumberOfTowers()+" "+PINK+"P:"+studentForColor[4]+""+RST+" |   \t";
            rows[4]+="| "+RED+"R:"+studentForColor[0]+" "+YELLOW+"Y:"+studentForColor[1]+""+RST+" |   \t";
            rows[5]+="| "+GREEN+"G:"+studentForColor[2]+" "+BLUE+"B:"+studentForColor[3]+""+RST+" |   \t";
            if(view.getIslands().get(view.getMother()).getIslandId()==island.getIslandId())
                rows[6]+="\\__Mother_/   \t";
            else
                rows[6]="\\_________/   \t";
        }
        out.append(Printer.mergeRows(rows));
        return out.toString();
    }
    private static int[] islandColorStudents(SimplifiedIsland island){
        int[] studForColor= new int[5];
        Arrays.fill(studForColor,0);
        for (Integer studId:island.getStudents()) {
            studForColor[studId/26] ++;
        }
        return studForColor;
    }
}
