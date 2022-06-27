package it.polimi.ingsw.model;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.model.characters.CharacterCard;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**It tests the game and its methods. It is concerned to test the creation of game and all the main methods to make advancements in the game.
 * @see Game*/
public class GameTest extends TestCase {

    Game game ;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        game = new GameStub(false, 4, 12);
    }

    /**It tests getter of islands. It is used to test the right creation of every island.*/
    public void testGetIslands() {
       assertNotNull(game.getIslands());
        for (Island island: game.getIslands()) {
            assertNotNull(island);
        }
    }

    /**It tests get difficulty mode.*/
    public void testIsEasy() {
        assertFalse(game.isEasy());
        game=new GameStub(true,4,12);
        assertTrue(game.isEasy());
    }

    /**It tests getter of number of players.*/
    public void testGetNPlayers() {
        assertEquals(4, game.getNPlayers());
    }

    /**It tests the getter of mother nature token.*/
    public void testGetMotherNature() {
        for(int i=1;i<12;i++){
            game.getActionPhase().moveMotherNature(1);
            assertEquals(i,game.getMotherNature().getPosition());
        }
    }

    /**It tests the change of phase.*/
    public void testMoveToNextPhase() {
        GameState gs=game.getActualState();
        game.moveToNextPhase();
        assertEquals(GameState.values()[gs.ordinal()+1],game.getActualState());
    }

    /**It tests getter of clouds. It is used to test the right creation of every cloud.*/
    public void testGetClouds() {
        assertNotNull(game.getClouds());
        for (Cloud cloud : game.getClouds()) {
            assertNotNull(cloud);
        }

    }

    /**It tests the getter of players. It is used to test the right creation of every player and of every element he has to own.*/
    public void testGetPlayers(){
        assertNotNull(game.getPlayers());
        for (Player player: game.getPlayers()) {
            assertNotNull(player);
            Board board=player.getBoard();
            Hand hand = player.getHand();
            assertNotNull(board);
            assertNotNull(hand);
            assertNotNull(hand.getCoin());

            for (AssistantCard ass: hand.getAssistant()) {
                assertNotNull(ass);
            }

        }
    }

    /**It tests the change of turn.*/
    public void testChangePlayerTurn(){
        List<Integer> currentorder = game.getPlayIngOrder();
        int currentplayer=game.getCurrentPlayerId();
        int currentindex=-1;
        for(int i=0;i<currentorder.size();i++){
            if(currentplayer==currentorder.get(i)) {
                currentindex = i;
                break;
            }
        }
        try{
            game.changePlayerTurn();
        }
        catch(NullPointerException e){
            assertTrue(currentindex!=-1);
        }
    }

    /**It tests character bonus activation.*/
    public void testBonusActive(){
        for(CharacterCard c : game.getCharacters()){
            assertFalse(game.isBonusActive(c.getId()));
            game.setCardBonusActive(c.getId());
            assertTrue(game.isBonusActive(c.getId()));
            if(c.getId()==9){
                game.setTargetColor(TokenColor.pink);
                assertEquals(TokenColor.pink,game.getTargetColor());
            }
            game.resetBonus();
            assertTrue(game.isBonusActive(0));
        }
    }


}