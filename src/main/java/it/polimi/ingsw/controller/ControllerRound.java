package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Round;

public class ControllerRound {
    private int[] actualOrder;
    private int[] playerWantsToPlay; //all'i-esimo giocatore corrisponderà l'ID della carta che vuole giocare
    @SuppressWarnings("FieldMayBeFinal")
    private int[] motherMovements;
    private Game game;

    public ControllerRound(int[]cards, int numPlayers){
        this.playerWantsToPlay=cards;
        this.actualOrder=new int[numPlayers];
        this.motherMovements=new int[numPlayers];
    }

    public void createGame(boolean easy, int nPlayer, int nIsole){
        game=new Game(easy, nPlayer, nIsole);

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


    public int[] getActualOrder() {
        return actualOrder;
    }
}
