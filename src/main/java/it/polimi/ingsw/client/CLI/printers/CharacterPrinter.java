package it.polimi.ingsw.client.CLI.printers;

import it.polimi.ingsw.view.CentralView;

import java.util.Arrays;
import java.util.List;

/**The class which prints the characters of the game (expert mode only).*/
public class CharacterPrinter implements Printer{

    /**It prints the character's description and its cost on the screen. It also specifies how much money the player owns.*/
    public static String print(CentralView view){
        StringBuilder out = new StringBuilder();
        for (Integer character:view.getCharacters()) {
            out.append(getCost(character,view));
            out.append(getDescription(character,view));
            out.append("\n ");
            out.append("---------------------------------------------------------------------------------------------------\n");
        }
        out.append(CYAN+"        YOU HAVE  : "+view.getPersonalPlayer().getCoin()+" COINS"+RST);
        return out.toString();
    }

    /**It prints on screen the current character's effect active (if active).
     * @param view the central view of the player*/
    public static String printUsedCharacterEffect(CentralView view){
        int active=view.getActiveCharacter();
        return CYAN+"Active effect - CARD# "+active+RST+" \n" + getDescription(active,view);
    }

    /**@param i the index of the card
     * @param view the central view of the player
     * @return the cost of the character in a string format*/
    private static String getCost(int i, CentralView view){
        return CYAN+"COST : "+view.getCostOfCard().get(i)+RST+"\n";
    }

    /**It extracts the description of the specified character card.
     * @param i the index of the character card
     * @param view the central view of the player
     * @return the description of the card effect*/
    private static String getDescription(int i,CentralView view){
        return switch(i){
            default -> "no description";
            case 1 ->CYAN+"1"+RST+" Take 1 student from this card and place it on an island of your choice.\n"+getStudentsOnCard(i,view);
            case 2 ->CYAN+"2"+RST+" For this turn you take control of any number of professor, even if you have the same number of students ";
            case 3, 4, 5, 12 ->"SHOULD NOT BE PRESENT \n";
            case 6 ->CYAN+"6 "+RST+"For this turn towers don't count towards influence";
            case 7 ->CYAN+"7 "+RST+"you may take up to 3 students from this card and replace them with the same number of students from your entrance \n"+getStudentsOnCard(i,view);
            case 8 ->CYAN+"8 "+RST+"For this turn you count as having 2 more influence";
            case 9 ->CYAN+"9 "+RST+"Choose a color of Student: for this turn students of that color don't count towards influence";
            case 10 ->CYAN+"10"+RST+ "you may exchange up to 2 students between your entrance and dining room";
            case 11 ->CYAN+"11 "+RST+"Take 1 student from this card and place it in your dining room\n"+getStudentsOnCard(i,view);
        };
    }

    /**It extracts the list of students on token cards.
     * @param i the id of the card
     * @param view the view of the player
     * @return the list of student's ids and colors*/
    private static String getStudentsOnCard(int i, CentralView view){
        StringBuilder out = new StringBuilder();
        out.append("Students on top :");
        if(i==1 || i==7 || i==11)
            for (Integer student:view.getStudentsOnCard().get(i)) {
                out.append(Printer.getCliColor(student / 26)).append("█").append(RST);
                out.append("  ");
            }
        else
            out.append("no students on this card!");

        return out.toString();
    }

    /**It prints on screen the token card with the number of tokens for color on it.
     * @param cardId the id of the token card
     * @param studentOnTop the list of student ids on the card*/
    public static String printStudentOnTop(int cardId, List<Integer> studentOnTop){
        StringBuilder out = new StringBuilder();
            String [] studC = studentOfColorCard(studentOnTop);
            out.append(" __________ ").append("\n");
            out.append("| card n°"+CYAN+(cardId)+RST+" |").append("\n");
            out.append("| R"+studC[0]+"  G"+studC[2]+"  |").append("\n");
            out.append("| Y"+studC[1]+"  B"+studC[4]+"  |").append("\n");
            out.append("|   P"+studC[3]+"     |").append("\n");
            out.append("|__________|").append("\n");

        return out.toString();
    }

    /**It extracts the number of students for color given a student list.
     * @param students the list of student tokens' ids
     * @return the number of students for color in the list*/
    private static String[] studentOfColorCard(List<Integer> students){
        int[] studForColor= new int[5];
        Arrays.fill(studForColor,0);
        for (Integer studId:students) {
            studForColor[studId/26] ++;
        }
        String [] out = new String[5];
        for (int i = 0; i < 5; i++) {
            out[i]=Printer.getCliColor(i)+""+studForColor[i]+Printer.RST;
        }
        return out;
    }
}