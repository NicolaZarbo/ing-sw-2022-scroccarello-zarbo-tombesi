package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.tokens.Token;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedBoard;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**The class used to translate model objects into simplified objects for the view.*/
public class ModelToViewTranslate {

    /**It creates a simplified representation of the islands for the view.
     * @param islands the model islands
     * @return the view islands*/
    public static List<SimplifiedIsland> translateIsland(List<Island> islands){
        ArrayList<SimplifiedIsland> simplifiedIslands =new ArrayList<>();
        for (Island island: islands) {
            List<Integer> studentsIds= island.getStudents().stream().map(Token::getId).toList();
            int nTowers=island.getTowers().size();
            int dimension = island.getIslandSize();
            int color=-1;
            if(nTowers>0)
                color=  island.getTowers().get(0).getColor().ordinal();
            simplifiedIslands.add(new SimplifiedIsland(studentsIds,translateIsland(island.getSubIslands()),nTowers,dimension,island.getID(),color));
        }
        return simplifiedIslands;
    }

    /**It creates a simplified representation of the players for the view.
     * @param modelPlayers the players in the model
     * @return the view players*/    public static List<SimplifiedPlayer> translatePlayer(Player[] modelPlayers){
        List<SimplifiedPlayer> viewPlayers=new ArrayList<>();
        for (Player modelPlayer :modelPlayers) {
            int coins = modelPlayer.getHand().getCoin();
            SimplifiedBoard board=ModelToViewTranslate.translateBoard(modelPlayer.getBoard());
            SimplifiedPlayer player =new SimplifiedPlayer(modelPlayer.getNickname(), modelPlayer.getId(), modelPlayer.getColorT().ordinal(),coins,board,modelPlayer.getMage().ordinal());
            player.setAssistantCards(getAvailableCard(modelPlayer.getHand()));
            viewPlayers.add(player);
        }
        return viewPlayers;
    }

    private static boolean[] getAvailableCard(Hand playerHand){
        boolean[] out = new boolean[10];
        Arrays.fill(out,false);
        for (AssistantCard card : playerHand.getAssistant()) {
            int x=card.getId()%10;
            out[x]=true;
        }
        return out;
    }

    /**It creates a simplified representation of the boards for the view.
     * @param modelBoard the model islands
     * @return the view boards*/
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

    /**It creates a simplified representation of the clouds for the view.
     * @param modelClouds the model islands
     * @return the view clouds*/    public static ArrayList<Integer[]> translateClouds(Cloud[] modelClouds){
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
