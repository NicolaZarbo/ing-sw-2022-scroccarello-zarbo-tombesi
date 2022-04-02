package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;

import java.util.ArrayList;

public class ControllerRound {
    private int[] actualOrder;
    private int[] playerWantsToPlay; //all'i-esimo giocatore corrisponderà l'ID della carta che vuole giocare
    private int[] motherMovements;
    Game game;

    public ControllerRound(int[]cards, int numPlayers){
        this.playerWantsToPlay=cards;
        this.actualOrder=new int[numPlayers];
        this.motherMovements=new int[numPlayers];
    }

    public void controlPianification(Round currRound){
        currRound.setClouds(game.getClouds(),game.getBag());
        for(int i=0;i<actualOrder.length;i++){
            currRound.pianification(i,playerWantsToPlay[i]);
        }
        motherMovements=currRound.getMotherMovements();
        int []tmp=currRound.getOrderForController();
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


    public int[] getActualOrder() {
        return actualOrder;
    }
}
