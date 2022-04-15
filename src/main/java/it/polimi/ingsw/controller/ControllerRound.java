package it.polimi.ingsw.controller;
import it.polimi.ingsw.messages.client.PlayAssistantMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;

import java.util.List;

public class ControllerRound {

    private Game game;
    private final Round modelRound;

    public ControllerRound(Game game){
        this.modelRound= new Round(game);
    }

    public void playAssistantCard(PlayAssistantMessage message){
        modelRound.playCard(message.getPlayerId(),message.getPlayedCard());
    }
    private List<Integer> getActualOrder() {
        return Round.getRoundOrder(game);
    }

/*
    public void controlPianification(){
        for(int i=0;i<Main.clouds.size();i++){
           // Main.clouds.set(i,Round.SetCloud(i,game));
            Main.bag-=3;
        }
        int []tmp=new int[actualOrder.length];
        for(int i=0;i<actualOrder.length;i++){
            motherMovements[i]=Round.MotherMovement(i,playerWantsToPlay[i],game);
            tmp[i]= Round.CardPlayedValue(i, playerWantsToPlay[i],game);
        }

        for(int i=0;i<tmp.length;i++){
            int minTemp=tmp[i];
            int k=i;
            for (int j = 0; j < tmp.length; j++) {
                if ((tmp[j] < minTemp && tmp[j]!=0)||(minTemp==0&&tmp[j]!=0)) {
                    minTemp = tmp[j];
                    k = j;
                }
            }
            actualOrder[i] = k;
            tmp[k]=0;
        }
    }

 */



}
