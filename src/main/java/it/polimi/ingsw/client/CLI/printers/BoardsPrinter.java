package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedBoard;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;

import java.util.Arrays;
import java.util.List;

public class BoardsPrinter implements Printer{
    String yo = """
              _Entr___DN___Prof___T°_\s
             |      |     |     |    |
            R|      |     |     |    |
            Y|      |     |     |____|
            G|      |     |     |
            B|      |     |     |
            P|      |     |     |
             |______|_____|_____|\s""".indent(1);
    public static String printPersonal(CentralView view){
        StringBuilder out = new StringBuilder();
        SimplifiedBoard board=view.getPersonalPlayer().getBoard();
        String tColor= Printer.padWithSpaces(towerColor(view.getPersonalPlayer().getTowerColor()),5);
        int[] entranceColors=BoardsPrinter.entranceForColor(board);
        String[] diningColor =BoardsPrinter.diningRoomColor(board);
        String[] professors= BoardsPrinter.getProfs(board);

        out.append("  _Entr_____________DN_____________Prof___T°_ \n");
        out.append(" |      |                        |     |    |\n") ;
        out.append("R|  "+RED+entranceColors[0]+RST+"   |  "+RED+diningColor[0]+RST+"  |  "+professors[0]+" |  "+board.getTowersLeft()+" |\n");
        out.append("Y|  "+YELLOW+entranceColors[1]+RST+"   |  "+YELLOW+diningColor[1]+RST+"  |  "+professors[1]+" |____|\n");
        out.append("G|  "+GREEN+entranceColors[2]+RST+"   |  "+GREEN+diningColor[2]+RST+"  |  "+professors[2]+" | "+tColor+"\n");
        out.append("B|  "+BLUE+entranceColors[3]+RST+"   |  "+BLUE+diningColor[3]+RST+"  |  "+professors[3]+" |     \n");
        out.append("P|  "+PINK+entranceColors[4]+RST+"   |  "+PINK+diningColor[4]+RST+"  |  "+professors[4]+" |     \n");
        out.append(" |______|________________________|_____|     \n");
        return out.toString();
    }
    public static String print(CentralView view){
        StringBuilder out= new StringBuilder();
        List<SimplifiedPlayer> players = view.getPlayers();
        String[] rows = new String[9];
        Arrays.fill(rows,"");

        for (SimplifiedPlayer pl: players) {
            SimplifiedBoard board=pl.getBoard();
            String tColor= Printer.padWithSpaces(towerColor(pl.getTowerColor()),5);
            int[] entranceColors=BoardsPrinter.entranceForColor(board);
            String[] diningColor =BoardsPrinter.diningRoomColor(board);
            String[] professors= BoardsPrinter.getProfs(board);
            String nameForBoard;
            if(pl.getUsername().equals(view.getName()))
                nameForBoard=(RED+">>>"+WHITE_BKG+BLACK+"Your Board"+RST+RED+"<<<"+RST);
            else nameForBoard=(CYAN_BKG+BLACK+"Player "+pl.getUsername()+RST);
            nameForBoard= Printer.padWithSpaces(nameForBoard,44);
            rows[0]+=nameForBoard+"                  ";
            rows[1]+="  _Entr_____________DN_____________Prof___T°_ \t";
            rows[2]+=" |      |                        |     |    |\t" ;
            rows[3]+="R|  "+RED+entranceColors[0]+RST+"   |  "+RED+diningColor[0]+RST+"  |  "+professors[0]+" |  "+board.getTowersLeft()+" |\t";
            rows[4]+="Y|  "+YELLOW+entranceColors[1]+RST+"   |  "+YELLOW+diningColor[1]+RST+"  |  "+professors[1]+" |____|\t";
            rows[5]+="G|  "+GREEN+entranceColors[2]+RST+"   |  "+GREEN+diningColor[2]+RST+"  |  "+professors[2]+" | "+tColor+"\t";
            rows[6]+="B|  "+BLUE+entranceColors[3]+RST+"   |  "+BLUE+diningColor[3]+RST+"  |  "+professors[3]+" |     \t";
            rows[7]+="P|  "+PINK+entranceColors[4]+RST+"   |  "+PINK+diningColor[4]+RST+"  |  "+professors[4]+" |     \t";
            rows[8]+=" |______|________________________|_____|     \t";
        }
        for (String s:rows) {
            out.append("\n");
            out.append(s);
        }
        return out.toString();
    }
    private static String[] getProfs(SimplifiedBoard board){
        String[] profs= new String[5];
        for (int i = 0; i < 5; i++) {
            profs[i]= Printer.getCliColor(i);
            if (board.getProfessorTable()[i] == -1)
                profs[i] += "X ";
            else profs[i]+=":)";
            profs[i]+= RST;
        }
        return profs;
    }
    private static String towerColor(int tcolor){
        return switch (tcolor){
            default -> "nt";
            case 1-> "B";
            case 2-> "W";
            case 3-> "G";
        };
    }
    private static String[] diningRoomColor(SimplifiedBoard board){
        int[] colorQuantity= new int[5];
        String[] coloredStudents= new String[5];
        Arrays.fill(coloredStudents,"");
        Arrays.fill(colorQuantity,0);
        for (int i = 0; i < 5; i++) {
            for (Integer studId: board.getDiningRoom()[i]) {
                if(studId!=-1)
                    colorQuantity[getColor(studId,26)]++;
            }
            coloredStudents[i]+= Printer.getCliColor(i);
            for (int j = 0; j < 10; j++) {
                if(j<colorQuantity[i])
                    coloredStudents[i]+=" █";
                else
                    if((j+1)%3==0 && j<9) {
                        if (board.getCoinDN()[i][(j) / 3])
                            coloredStudents[i] += " C";
                    }
                    else
                        coloredStudents[i]+="  ";
            }
        }
        return coloredStudents;
    }

    private static int[] entranceForColor(SimplifiedBoard board ){
        int[] colorQuantity= new int[5];
        Arrays.fill(colorQuantity,0);
        for (Integer studId: board.getEntrance()) {
            colorQuantity[getColor(studId,26)]++;
        }
        return colorQuantity;
    }
    private static int getColor(int studId,int pedForColor){
      return studId/pedForColor;
    }
}
