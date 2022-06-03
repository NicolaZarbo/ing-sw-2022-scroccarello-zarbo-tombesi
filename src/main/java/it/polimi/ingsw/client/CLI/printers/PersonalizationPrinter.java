package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;

public class PersonalizationPrinter implements Printer{
    public static String printForColorsAndMages(PlayerSetUpMessage message){
        StringBuilder out = new StringBuilder();
        out.append(CYAN+"\nCHOOSE FROM AVAILABLE MAGES   and  TOWER COLORS"+Printer.RED+" \n( mage number followed by the color's first letter) \n[mage_Number Color_Initial] \n"+Printer.RST);
        for (Integer mageInt: message.getAvailableMages()) {
            out.append(DR_GRAY_BKG+BR_PINK).append(" "+translateMage(mageInt)+" ").append(RST).append("\s \s");
        }
        out.append("\n\n");
        if( message.isTeamPlay())
            out.append(welcomeTeamPrint(message.getAvailableMages().size()));
        for (Integer colorInt: message.getAvailableColor()) {
            out.append(translateColor(colorInt)).append("\s \s");
        }
        return out.toString();
    }
    private static String welcomeTeamPrint(int magesLeft){
        String out="";
        int team=(3-magesLeft);
        if(magesLeft>2) {
            out += CYAN + "YOU ARE THE TEAM LEADER " + RST;
            team=5-magesLeft;
        }
        out+= BR_WHITE_BKG+BLACK+"WELCOME TO TEAM "+team+RST+"\n";

        return out;
    }

    private static String translateColor(int i){
        return switch (i){
            default -> "error";
            case 0-> "BLACK";
            case 1-> BR_WHITE_BKG+BLACK+" WHITE "+RST;
            case 2 -> DR_GRAY_BKG+BR_WHITE+" GRAY "+RST;
        };
    }
    private static String translateMage(int i){
        return switch (i){
            default -> "error";
            case 0-> "MAGE 1";
            case 1-> "MAGE 2";
            case 2->"MAGE 3";
            case 3->"MAGE 4";//TODO add some fun to these names
        };
    }
}
