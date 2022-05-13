package it.polimi.ingsw.view.CLI.printers;

import it.polimi.ingsw.messages.server.PlayerSetUpMessage;

public class PersonalizationPrinter implements Printer{
    public static String printForColorsAndMages(PlayerSetUpMessage message){
        StringBuilder out = new StringBuilder();
        out.append("\nCHOOSE FROM AVAILABLE MAGES   and  TOWER COLORS \n( mage number followed by the color's first letter) \n");
        for (Integer mageInt: message.getAvailableMages()) {
            out.append(CYAN).append(translateMage(mageInt)).append(RST).append("\s \s");
        }
        out.append("\n");
        for (Integer colorInt: message.getAvailableColor()) {
            out.append(translateColor(colorInt)).append("\s \s");
        }
        return out.toString();
    }

    private static String translateColor(int i){
        return switch (i){
            default -> "error";
            case 0-> "BLACK";
            case 1-> WHITE_BKG+BLACK+"WHITE"+RST;
            case 2 ->"gray";
        };
    }
    private static String translateMage(int i){
        return switch (i){
            default -> "error";
            case 0-> "mage 1";
            case 1-> "mage 2";
            case 2->"mage 3";
            case 3->"mage 4";//TODO add some fun to these names
        };
    }
}
