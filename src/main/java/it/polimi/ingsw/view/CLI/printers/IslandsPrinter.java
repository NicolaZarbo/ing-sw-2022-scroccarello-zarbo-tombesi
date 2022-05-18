package it.polimi.ingsw.view.CLI.printers;

import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.objects.SimplifiedIsland;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IslandsPrinter implements Printer{
String yo = """
         ________\s
        /        \\
        |        |
        |        |
        |        |
        \\________/""";
    public static String print(CentralView view){
        StringBuilder out= new StringBuilder("\n");
        String[] rows= new String[7];
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
            int numberOfTower=island.getNumberOfTowers();
            for (SimplifiedIsland subIsland: island.getSubIsland()) {
                numberOfTower+=subIsland.getNumberOfTowers();
            }
            preRows[0]="Island : "+island.getIslandId()+"  \t";
            preRows[1]=" _________    \t";
            preRows[2]="/ "+tColor+"  \\   \t";
            preRows[3]="|T°:"+island.getNumberOfTowers()+" "+PINK+"P:"+studentForColor[4]+""+RST+" |   \t";
            preRows[4]="| "+RED+"R:"+studentForColor[0]+" "+YELLOW+"Y:"+studentForColor[1]+""+RST+" |   \t";
            preRows[5]="| "+GREEN+"G:"+studentForColor[2]+" "+BLUE+"B:"+studentForColor[3]+""+RST+" |   \t";
            if(view.getIslands().get(view.getMother()).getIslandId()==island.getIslandId()){
                preRows[6]="\\__"+CYAN+"Mother"+RST+"_/   \t";}
            else preRows[6]="\\_________/   \t";
            if(escapeCounter>(size/2))
                for (int i = 0; i < 7; i++) {
                    preRows[i]=reverseString(preRows[i]);
                }
            for (int i = 0; i < 7; i++) {
                rows[i]+=preRows[i];
            }
        }
        out.append(reversedMerge(rows));
        return out.toString();
    }
    private static String reversedMerge(String[] rowsForOutput){
        StringBuilder out= new StringBuilder();
        for (String s:rowsForOutput) {
            out.append(reverseString(s));
            out.append("\n");
        }
        return out.toString();
    }
    private static String reverseString(String str){
        char ch[]=str.toCharArray();
        StringBuilder rev= new StringBuilder();
        for(int i=ch.length-1;i>=0;i--){
            rev.append(ch[i]);
        }
        return rev.toString();
    }

    private static int[] islandColorStudents(SimplifiedIsland island){
        int[] studForColor= new int[5];
        Arrays.fill(studForColor,0);
        List<Integer> students= new ArrayList<>(island.getStudents());
        for (SimplifiedIsland subIsland:island.getSubIsland()) {
            students.addAll(subIsland.getStudents());
        }
        for (Integer studId:students) {
            studForColor[studId/26] ++;
        }
        return studForColor;
    }
    private static String towerColor(int tColor){
        if(tColor==0)
            return BLACK+CYAN_BKG+"BLACK"+RST;
        if(tColor==1)
            return BLACK+CYAN_BKG+"WHITE"+RST;
        if(tColor==2)
            return BLACK+CYAN_BKG+"GRAY"+RST;
        return "";
    }
}
