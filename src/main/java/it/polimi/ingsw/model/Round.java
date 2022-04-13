package it.polimi.ingsw.model;
import it.polimi.ingsw.model.token.Student;

import java.util.*;

public class Round   {

    public static void SetCloud( Game game) {
        int cloudDim=game.getClouds()[0].getDimension();
        Student[] students = new Student[cloudDim];
        for (Cloud cloud:game.getClouds()) {
            for (int i = 0; i < cloudDim; i++) {
                students[i]=game.getBag().getToken();
            }
            cloud.setStud(students);
        }
        //serversendAll(new CloudMessage(game))

    }

    public static void playCard(int playerdId, int cardPlayed, Game game){
        Hand actualHand=game.getPlayer(playerdId).getHand();
        AssistantCard played=actualHand.playAssistant(cardPlayed);
        game.addCardPlayedThisRound(playerdId,played);
        //serversendAll(new CardPlayedMessage())
    }
// orders players TODO
    public static void roundOrder(Game game){
        ArrayList<Integer> order = new ArrayList<>();
       game.setPlayIngOrder(order);
    }

}
