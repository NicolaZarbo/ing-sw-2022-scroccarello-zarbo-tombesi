package it.polimi.ingsw.model;
import it.polimi.ingsw.model.token.Student;
import java.util.Observable;

public class Round   {

    public static int[] SetCloud(int cloudNum, Game game) {
        Student[] studList = new Student[3];
        int[] studID = new int[3];
        for (int i = 0; i < 3; i++) {
            studList[i] = game.getBag().getToken();
            studID[i] = studList[i].getId();
        }
        game.getClouds()[cloudNum].setStud(studList);
        updateClouds(game);
        return studID;
    }
    public static void updateClouds(Game game){

    }




    public static int CardPlayedValue(int numPlayer, int cardPlayed, Game game){
        Hand actualHand=game.getPlayer(numPlayer).getHand();
        AssistantCard played=actualHand.playAssistant(cardPlayed);
        return played.getValTurn();
    }
    public static int MotherMovement(int numPlayer, int cardPlayed,Game game){
        Hand actualHand=game.getPlayer(numPlayer).getHand();
        AssistantCard played=actualHand.playAssistant(cardPlayed);
        return played.getMoveMother();
    }

}
