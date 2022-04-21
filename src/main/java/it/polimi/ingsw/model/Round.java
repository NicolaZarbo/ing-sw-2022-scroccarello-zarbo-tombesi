package it.polimi.ingsw.model;
import it.polimi.ingsw.messages.server.CloudMessage;
import it.polimi.ingsw.messages.server.PlayedAssistentMessage;
import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

public class Round  extends Observable<ServerMessage> {
    Game game;
    public Round(Game game) {
        this.game=game;
    }

    public void SetCloud() {
        int cloudDim=game.getClouds()[0].getDimension();
        Student[] students = new Student[cloudDim];
        for (Cloud cloud:game.getClouds()) {
            for (int i = 0; i < cloudDim; i++) {
                students[i]=game.getBag().getToken();
            }
            cloud.setStud(students);
        }
        this.notify(new CloudMessage(game));


    }

    public void playCard(int playerId, int cardPlayed){
        Hand actualHand=game.getPlayer(playerId).getHand();
        AssistantCard played=actualHand.playAssistant(cardPlayed);
        game.addCardPlayedThisRound(playerId,played);
        this.notify(new PlayedAssistentMessage(game));
    }
// orders players TODO
    public static void roundOrder(Game game){
        ArrayList<Integer> order = new ArrayList<>();
       game.setPlayIngOrder(order);
    }
    public static List<Integer> getRoundOrder(Game game){
        return game.getPlayIngOrder();
    }

}
