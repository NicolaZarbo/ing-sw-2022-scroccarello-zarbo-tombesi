package it.polimi.ingsw.model;
import it.polimi.ingsw.messages.server.CloudMessage;
import it.polimi.ingsw.messages.server.PlayedAssistantMessage;
import it.polimi.ingsw.model.token.Student;

import java.util.*;

public class Round {
    Game game;
    public Round(Game game) {
        this.game=game;
    }

    public void setCloud() {
        for(int i=0;i<game.getClouds().length;i++){
            int dim=game.getClouds()[i].getStud().length;
            Student[] students=new Student[dim];
            for(int j=0;j<dim;j++){
                students[j]=game.getBag().getToken();
            }
           game.getClouds()[i].setStud(students);
        }
        game.groupMultiMessage(new CloudMessage(game));
    }

    public void playCard(int playerId, int cardPlayed){
        Hand actualHand=game.getPlayer(playerId).getHand();
        AssistantCard played=actualHand.playAssistant(cardPlayed);
        game.addCardPlayedThisRound(playerId,played);
        game.groupMultiMessage(new PlayedAssistantMessage(game));
    }

    public void roundOrder(){
       ArrayList<Integer> order = new ArrayList<>();
       ArrayList<Integer> values=new ArrayList<>();
       AssistantCard cardPlayed;
       for(int i=1;i<=game.getNPlayers();i++){
           cardPlayed=game.getPlayedCard(i-1);
           values.add((i-1),cardPlayed.getValTurn());
       }
        for(int i=0;i<values.size();i++){
            int minTemp=values.get(i);
            int k=i;
            for (int j = 0; j < values.size(); j++) {
                if ((values.get(j) < minTemp && values.get(j)!=0)||(minTemp==0&&values.get(j)!=0)) {
                    minTemp = values.get(j);
                    k = j;
                }
            }
            order.add(i,k);
            values.set(k,0);
        }
        game.setPlayIngOrder(order);
    }

    public List<Integer> getRoundOrder(Game game){
        return game.getPlayIngOrder();
    }

}
