package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.view.CentralView;

/**The class which prints the game table.*/
public class GamePrinter implements Printer{

    /**It prints the game table with all the islands and the players' boards*/
    public static String printGameTable(CentralView view){
        StringBuilder out= new StringBuilder();
        out.append(IslandsPrinter.print(view));
        out.append(BoardsPrinter.print(view));
        return out.toString();
    }
}
