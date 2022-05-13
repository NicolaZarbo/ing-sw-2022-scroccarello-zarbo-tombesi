package it.polimi.ingsw.messages.server;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Island;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.token.Token;
import it.polimi.ingsw.view.objects.SimplifiedIsland;
import it.polimi.ingsw.view.objects.SimplifiedBoard;
import it.polimi.ingsw.view.objects.SimplifiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModelToViewTranslate {
    public static List<SimplifiedIsland> translateIsland(List<Island> islands){
        ArrayList<SimplifiedIsland> simplifiedIslands =new ArrayList<>();
        for (Island island: islands) {
            List<Integer> studentsIds= island.getStudents().stream().map(Token::getId).toList();
            int nTowers=island.getTowers().size();
            int dimension = island.getIslandSize();
            simplifiedIslands.add(new SimplifiedIsland(studentsIds,nTowers,dimension,island.getID()));
        }
        return simplifiedIslands;
    }


    public static List<SimplifiedPlayer> translatePlayer(Player[] modelPlayers){
        List<SimplifiedPlayer> viewPlayers=new ArrayList<>();
        for (Player modelPlayer :modelPlayers) {
            int coins = modelPlayer.getHand().getCoin();
            SimplifiedBoard board=ModelToViewTranslate.translateBoard(modelPlayer.getBoard());
            SimplifiedPlayer player =new SimplifiedPlayer(modelPlayer.getNickname(), modelPlayer.getId(), modelPlayer.getColorT().ordinal(),coins,board,modelPlayer.getMage().ordinal());
            viewPlayers.add(player);
        }
        return viewPlayers;
    }

    public static SimplifiedBoard translateBoard(Board modelBoard){
        Integer[][] diningRoom= new Integer[modelBoard.getDiningRoom().length][modelBoard.getDiningRoom()[0].length];
        for (int i = 0; i < modelBoard.getDiningRoom().length; i++) {
            for (int j = 0; j < modelBoard.getDiningRoom()[i].length; j++) {
                if(modelBoard.getDiningRoom()[i][j]!=null)
                    diningRoom[i][j]=modelBoard.getDiningRoom()[i][j].getId();
                else diningRoom[i][j]=-1;
            }
        }
        Integer[] profBoard= new Integer[modelBoard.getTable().length];
        for (int i = 0; i < modelBoard.getTable().length; i++) {
            if(modelBoard.getTable()[i]!=null)
                profBoard[i]=modelBoard.getTable()[i].getId();
            else profBoard[i]=-1;
        }
        List<Integer> entrance= modelBoard.getEntrance().stream().map(Token::getId).toList();
        int towerLeft=modelBoard.towersLeft();
        return  new SimplifiedBoard(diningRoom,profBoard,entrance,towerLeft, modelBoard.getCoinDN());
    }

    public static ArrayList<Integer[]> translateClouds(Cloud[] modelClouds){
        ArrayList<Integer[]> viewClouds=new ArrayList<>();

        for (Cloud cloud:modelClouds) {
            Integer[] singleCloud =new Integer[cloud.getStud().length];
            if(cloud.getStud()[0]!=null)
                for (int i = 0; i < cloud.getStud().length; i++) {
                    singleCloud[i]=cloud.getStud()[i].getId();
                }
            else Arrays.fill(singleCloud,-1);
            viewClouds.add(singleCloud);
        }
        return viewClouds;
    }
}
