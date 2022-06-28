package it.polimi.ingsw.model;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.enumerations.TowerColor;
import it.polimi.ingsw.messages.ModelToViewTranslate;
import it.polimi.ingsw.model.characters.Character1;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.tokens.Professor;
import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.server.Lobby;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**It tests the setup component of the game.
 * @see Setup*/
public class SetupTest extends TestCase {

    Bag bag;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        bag=new Bag(10,5);
    }

    /**It tests the creation of islands.*/
    public void testCreateIslands() {
        List<Island> islands = Setup.createIslands(12, bag);
        assertEquals(12,islands.size());
        for (Island island : islands) {
            assertEquals(1,island.getIslandSize());
            for (Student stud : island.getStudents()) {
                assertNotNull(stud);
            }
            assertEquals(0,island.getTowers().size());
        }
    }

    /**It tests the creation of professors.*/
    public void testCreateProfessor() {
        Professor[] prof=new GameStub(false,2,12).getSetupPhase().createProfessor(5);
        assertTrue(prof.length==5);
        for(int i=0;i<5;i++){
            assertEquals(TokenColor.getColor(i),prof[i].getColor());
        }
    }

    /**It tests the creation of the players.*/
    public void testCreatePlayer() {
        ArrayList<LobbyPlayer> preplayers=new ArrayList<>();
        for(int i=0;i<3;i++){
            preplayers.add(new LobbyPlayer(TowerColor.getColor(i), Mage.getMage(i),"pippo"+i));
        }
        Player[] players=Setup.createPlayer(false,preplayers,bag);
        for(Player p : players){
            assertNotNull(p.getBoard());
            assertNotNull(p.getHand());
        }
    }
    /*public void testIno(){
        GameStub test = new GameStub(false, 4, 12);
        for (Player pla: test.getPlayers()) {
            System.out.println(pla.getColorT());
        }
        List<SimplifiedPlayer> pp= ModelToViewTranslate.translatePlayer(test.getPlayers());
        for (SimplifiedPlayer p:pp) {
            System.out.println(p.getTowerColor());
        }

    }

     */

    /**It tests clouds creations according to the number of players.*/
    public void testCreateClouds() {
        for(int i=2;i<4;i++) {
            Cloud[] clouds = Setup.createClouds(i);
            for (Cloud c : clouds) {
                assertNotNull(c.getStud());
                assertEquals(i+1, c.getStud().length);
            }
        }
    }
    /**It tests character cards creation for expert move.*/
    public void testCharacterCards(){
        ArrayList<CharacterCard> characters=new GameStub(false,2,12).getSetupPhase().createCharacterCards(bag,12);
        for(CharacterCard c : characters){
            assertTrue(c.getId()!=3 && c.getId()!=4 && c.getId()!=5 && c.getId()!=12);
            assertTrue(c.getCost()>=1 && c.getCost()<=3);
        }
    }
}