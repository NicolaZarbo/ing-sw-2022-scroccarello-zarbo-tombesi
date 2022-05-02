package it.polimi.ingsw.view.CLI.printers;

import it.polimi.ingsw.view.CentralView;

public class CharacterPrinter implements Printer{
    public static String print(CentralView view){
        StringBuilder out = new StringBuilder();
        for (Integer character:view.getCharacters()) {
            out.append(getCost(character,view));
            out.append(getDescription(character,view));
            out.append("\n ");
            out.append("---------------------------------------------------------------------------------------------------\n");
        }

        return out.toString();
    }
    private static String getCost(int i, CentralView view){
        return CYAN+"COST : "+view.getCostOfCard().get(i)+RST+"\n";
    }
    private static String getDescription(int i,CentralView view){
        return switch(i){
            default -> "no description";
            case 1 ->CYAN+"1"+RST+" Take 1 student from this card and place it on an island of your choice.\n"+getStudentsOnCard(i,view);
            case 2 ->CYAN+"2"+RST+" For this turn you take control of any number of professor, even if you have the same number of students ";
            case 3 ->"SHOULD NOT BE PRESENT ";
            case 4 ->"SHOULD NOT BE PRESENT \n 4 You may move mother nature up to 2 additional islands";
            case 5 ->"SHOULD NOT BE PRESENT \n";
            case 6 ->CYAN+"6 "+RST+"For this turn towers don't count towards influence";
            case 7 ->CYAN+"7 "+RST+"you may take up to 3 students from this card and replace them with the same number of students from your entrance \n"+getStudentsOnCard(i,view);
            case 8 ->CYAN+"8 "+RST+"For this turn you count as having 2 more influence";
            case 9 ->CYAN+"9 "+RST+"Choose a color of Student: for this turn students of that color don't count towards influence";
            case 10 ->CYAN+"10"+RST+ "you may exchange up to 2 students between your entrance and dining room";
            case 11 ->CYAN+"11 "+RST+"Take 1 student from this card and place it in your dining room\n"+getStudentsOnCard(i,view);
            case 12 ->" SHOULD NOT BE PRESENT \n";
        };
    }
    private static String getStudentsOnCard(int i, CentralView view){
        StringBuilder out = new StringBuilder();
        out.append("Students on top :");
        if(i==1 || i==7 || i==11)
            for (Integer student:view.getStudentsOnCard().get(i)) {
                out.append(Printer.getCliColor(student / 26)).append("█").append(RST);
                out.append("  ");
            }
        return out.toString();
    }
}