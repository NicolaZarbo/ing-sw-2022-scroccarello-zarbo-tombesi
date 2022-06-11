package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;

import java.util.Arrays;

public class UsedCardsPrinter implements Printer{


    public static String printUsedAssistant(CentralView view){
        String[] rows= new String[8];
        Arrays.fill(rows,"");
        for (SimplifiedPlayer player:view.getPlayers()) {
            if(view.getPlayedCardThisTurnByPlayerId(player.getId())!=null) {
                int playedCard = view.getPlayedCardThisTurnByPlayerId(player.getId());
                int M = (playedCard + 2) / 2;
                String T = playedCard + 1 + "";
                if (playedCard < 9)
                    T += " ";
                rows[0] = "  ____________    ";
                rows[1] = "  |          |    ";
                rows[2] = "  |  M: " + CYAN + M + RST + "    |    ";
                rows[3] = "  |          |    ";
                rows[4] = "  |  T: " + CYAN + T + RST + "   |    ";
                rows[5] = "  |__________|    ";
                rows[6] = "" + Printer.padWithSpaces(player.getUsername(), 10);
            }
        }
        return Printer.mergeRows(rows);
    }

}
