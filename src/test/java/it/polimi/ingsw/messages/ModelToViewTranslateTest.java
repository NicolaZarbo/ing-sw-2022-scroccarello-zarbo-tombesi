package it.polimi.ingsw.messages;

import it.polimi.ingsw.messages.server.ModelToViewTranslate;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.view.objects.SimplifiedBoard;
import it.polimi.ingsw.view.objects.SimplifiedIsland;
import it.polimi.ingsw.view.objects.SimplifiedPlayer;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModelToViewTranslateTest extends TestCase {
    Game gameTest;
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new Game(false,4,12);
    }

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

    public void testTranslatePlayer() {
        List<SimplifiedPlayer> listTest= ModelToViewTranslate.translatePlayer(gameTest.getPlayers());
        for(int i=0;i<listTest.size();i++){
            assertEquals(gameTest.getPlayer(i).getNickname(),listTest.get(i).getUsername());
            assertEquals(gameTest.getPlayer(i).getId(),listTest.get(i).getId());
            assertEquals(gameTest.getPlayer(i).getColorT().ordinal(),listTest.get(i).getTowerColor());
            assertEquals(gameTest.getPlayer(i).getHand().getCoin(),listTest.get(i).getCoin());
            int finalI = i;
            if(gameTest.getPlayer(i).getHand().getAssistant().size()>0){
                assertTrue(gameTest.getPlayer(i).getHand().getAssistant().stream().anyMatch(x->listTest.get(finalI).getAssistantCards()[x.getId()]));
            }
            else
                assertFalse(gameTest.getPlayer(i).getHand().getAssistant().stream().anyMatch(x->listTest.get(finalI).getAssistantCards()[x.getId()]));
            if(gameTest.getPlayer(i).getHand().getDiscarded().size()>0){
                assertTrue(gameTest.getPlayer(i).getHand().getDiscarded().stream().anyMatch(x->listTest.get(finalI).getDiscardedCards()[x.getId()]));
            }
            else
                assertFalse(gameTest.getPlayer(i).getHand().getDiscarded().stream().anyMatch(x->listTest.get(finalI).getDiscardedCards()[x.getId()]));

        }
    }

    public void testTranslateBoard() {
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

    public void testTranslateClouds() {
    }
}