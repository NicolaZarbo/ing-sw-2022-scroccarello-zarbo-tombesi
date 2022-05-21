package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.view.CentralView;

public class GamePrinter implements Printer{
    public static String printGameTable(CentralView view){
        StringBuilder out= new StringBuilder();
        out.append(IslandsPrinter.print(view));
        out.append(BoardsPrinter.print(view));
        return out.toString();
    }
}
