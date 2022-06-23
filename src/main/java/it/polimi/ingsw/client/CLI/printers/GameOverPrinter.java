package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.view.CentralView;

public class GameOverPrinter implements Printer{
    private static final String team1= """
            ████████╗███████╗ █████╗ ███╗   ███╗     ██╗    ██╗    ██╗ ██████╗ ███╗   ██╗
            ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║    ███║    ██║    ██║██╔═══██╗████╗  ██║
               ██║   █████╗  ███████║██╔████╔██║    ╚██║    ██║ █╗ ██║██║   ██║██╔██╗ ██║
               ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║     ██║    ██║███╗██║██║   ██║██║╚██╗██║
               ██║   ███████╗██║  ██║██║ ╚═╝ ██║     ██║    ╚███╔███╔╝╚██████╔╝██║ ╚████║
               ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝     ╚═╝     ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═══╝""";
    private static final String team2= """
            ████████╗███████╗ █████╗ ███╗   ███╗    ██████╗     ██╗    ██╗ ██████╗ ███╗   ██╗
            ╚══██╔══╝██╔════╝██╔══██╗████╗ ████║    ╚════██╗    ██║    ██║██╔═══██╗████╗  ██║
               ██║   █████╗  ███████║██╔████╔██║     █████╔╝    ██║ █╗ ██║██║   ██║██╔██╗ ██║
               ██║   ██╔══╝  ██╔══██║██║╚██╔╝██║    ██╔═══╝     ██║███╗██║██║   ██║██║╚██╗██║
               ██║   ███████╗██║  ██║██║ ╚═╝ ██║    ███████╗    ╚███╔███╔╝╚██████╔╝██║ ╚████║
               ╚═╝   ╚══════╝╚═╝  ╚═╝╚═╝     ╚═╝    ╚══════╝     ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═══╝""";
    private static final String youWon= """
            ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗ ██████╗ ███╗   ██╗
            ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██╔═══██╗████╗  ██║
             ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║   ██║██╔██╗ ██║
              ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║   ██║██║╚██╗██║
               ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝╚██████╔╝██║ ╚████║
               ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝  ╚═════╝ ╚═╝  ╚═══╝""";
    private static final String youLost= """
            ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗      ██████╗ ███████╗████████╗
            ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║     ██╔═══██╗██╔════╝╚══██╔══╝
             ╚████╔╝ ██║   ██║██║   ██║    ██║     ██║   ██║███████╗   ██║  \s
              ╚██╔╝  ██║   ██║██║   ██║    ██║     ██║   ██║╚════██║   ██║  \s
               ██║   ╚██████╔╝╚██████╔╝    ███████╗╚██████╔╝███████║   ██║  \s
               ╚═╝    ╚═════╝  ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝   ╚═╝  \s""";
    public static String print(CentralView view) {
        String out = PINK+"""
                 ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗\s
                ██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗
                ██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝
                ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗
                ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║
                 ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝
                 """;
        if(view.isTeamPlay()) {
            if (view.getWinner() == 1)
                out += team1;
            else out+= team2;
        }else {
            if(view.getWinner()==view.getPersonalPlayer().getId())
                out+=youWon;
            else out+=youLost;
        }
    return out;
    }
}
