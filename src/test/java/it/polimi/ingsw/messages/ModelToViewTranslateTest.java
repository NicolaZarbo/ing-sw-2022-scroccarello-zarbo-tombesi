package it.polimi.ingsw.messages;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedBoard;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**It tests the translation of model objects into simplified objects for the client view.*/
public class ModelToViewTranslateTest extends TestCase {
    Game gameTest;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
    }

    /**It tests the translation of a model island into a simplified island.*/
    public void testTranslateIsland() {
        List<SimplifiedIsland> listTest= ModelToViewTranslate.translateIsland(gameTest.getIslands());
        for(int i=0;i<listTest.size();i++){
            assertEquals(gameTest.getIslands().get(i).getIslandSize(),listTest.get(i).getDimension());
            assertEquals(gameTest.getIslands().get(i).getStudents().stream().map(x->x.getId()).toList(),listTest.get(i).getStudents());
            assertEquals(gameTest.getIslands().get(i).getTowers().size(),listTest.get(i).getNumberOfTowers());
            assertEquals(gameTest.getIslands().get(i).getID(),listTest.get(i).getIslandId());
            if(listTest.get(i).getNumberOfTowers()>0){
                assertEquals(gameTest.getIslands().get(i).getTowers().get(0).getColor().ordinal(),listTest.get(i).getTowerColor());
            }

        }
    }

    /**It tests the translation of a model player into a simplified player.*/
    public void testTranslatePlayer() {
        List<SimplifiedPlayer> listTest= ModelToViewTranslate.translatePlayer(gameTest.getPlayers());
        ((GameStub)gameTest).setManuallyGamePhase(GameState.planPlayCard);
        for(int i=0;i<listTest.size();i++){
            assertEquals(gameTest.getPlayer(i).getNickname(),listTest.get(i).getUsername());
            assertEquals(gameTest.getPlayer(i).getId(),listTest.get(i).getId());
            assertEquals(gameTest.getPlayer(i).getColorT().ordinal(),listTest.get(i).getTowerColor());
            assertEquals(gameTest.getPlayer(i).getHand().getCoin(),listTest.get(i).getCoin());
            int finalI = i;
           if(gameTest.getPlayer(i).getHand().getAssistant().size()>0){
                assertTrue(gameTest.getPlayer(i).getHand().getAssistant().stream().anyMatch(x->listTest.get(finalI).getAssistantCards()[x.getId()%10]));
            }
            else
                assertFalse(gameTest.getPlayer(i).getHand().getAssistant().stream().anyMatch(x->listTest.get(finalI).getAssistantCards()[x.getId()%10]));
            if(gameTest.getPlayer(i).getHand().getDiscarded().size()>0){
                assertTrue(gameTest.getPlayer(i).getHand().getDiscarded().stream().anyMatch(x->listTest.get(finalI).getDiscardedCards()[x.getId()%10]));
            }
            else
                assertFalse(gameTest.getPlayer(i).getHand().getDiscarded().stream().anyMatch(x->listTest.get(finalI).getDiscardedCards()[x.getId()%10]));

        }
    }

    /**It tests the translation of a model board into a simplified board.*/
    public void testTranslateBoard() {
        ((GameStub)gameTest).setManuallyGamePhase(GameState.planPlayCard);
        SimplifiedBoard bTest=ModelToViewTranslate.translateBoard(gameTest.getPlayer(gameTest.getCurrentPlayerId()).getBoard());
        for(int i=0;i<bTest.getDiningRoom().length;i++){
            for(int j=0;j<bTest.getDiningRoom()[i].length;j++){
                if(gameTest.getPlayer(gameTest.getCurrentPlayerId()).getBoard().getDiningRoom()[i][j]!=null)
                    assertTrue(gameTest.getPlayer(gameTest.getCurrentPlayerId()).getBoard().getDiningRoom()[i][j].getId() == bTest.getDiningRoom()[i][j]);

            }
        }
        for(int i=0;i<bTest.getEntrance().size();i++){
            assertTrue(gameTest.getPlayer(gameTest.getCurrentPlayerId()).getBoard().getEntrance().get(i).getId() == bTest.getEntrance().get(i));
        }

    }

    /**It tests the translation of the model clouds into simplified clouds.*/
    public void testTranslateClouds() {
        ArrayList<Integer[]> list=ModelToViewTranslate.translateClouds(gameTest.getClouds());
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.get(i).length;j++){
                if(list.get(i)[j]!=-1){
                    assertTrue(gameTest.getClouds()[i].getStud()[j].getId()==list.get(i)[j]);
                }
                else{
                    assertNull(gameTest.getClouds()[i]);
                }
            }
        }
    }
}