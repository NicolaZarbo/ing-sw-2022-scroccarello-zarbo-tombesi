package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;

public class ControllerRound {
    private int[] actualOrder;
    private int[] playerWantsToPlay; //all'i-esimo giocatore corrisponderà l'ID della carta che vuole giocare
    private int[] motherMovements;
    static Game game;

    public ControllerRound(int[]cards){
        this.playerWantsToPlay=cards;
    }
    public void controlPianification(Round currRound){
        currRound.setClouds(game.getClouds(),game.getBag());
        for(int i=0;i<actualOrder.length-1;i++){
            currRound.pianification(i,playerWantsToPlay[i]);
        }
        motherMovements=currRound.getMotherMovements();
        int []tmp=currRound.getOrderForController();
        for(int i=0;i<tmp.length-1;i++){
            int minTemp=tmp[i];
            int k=0;
            for(int j=i;j<tmp.length-1;j++) {
                if(tmp[j]<minTemp){
                    minTemp=tmp[j];
                    k=j;
                }
            }
            actualOrder[i]=k;
        }
    }
}
