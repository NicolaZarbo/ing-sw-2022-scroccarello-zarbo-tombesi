package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.model.Game;
import junit.framework.TestCase;

import java.util.ArrayList;

/**It tests characters creations with the factory method.*/
public class FactoryCharacterTest extends TestCase {
    Game gameTest;
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
    }

    /**It tests character creations. It tests the creation of all the available characters*/
    public void testCreateCharacter() {
        ArrayList<CharacterCard> deck=new ArrayList<>();
        for(int i=1;i<12;i++){
            if(i!=3&&i!=4&&i!=5&&i!=12)
                try{
                    deck.add(FactoryCharacter.createCharacter(i,gameTest.getBag()));
                }
            catch(RuntimeException e){
                    e.printStackTrace();
            }
        }
        assertEquals(8,deck.size());
    }

    /**It tests error handling of character creations the case the card is not implemented (card number does not exist).
     */
    public void testNotDisponibleCharacter(){
        try{
            CharacterCard cardCrasher=FactoryCharacter.createCharacter(4,gameTest.getBag());
        }
        catch(RuntimeException e){
            assertNotNull(e);
        }
        try{
            CharacterCard cardCrasher=FactoryCharacter.createCharacter(13,gameTest.getBag());
        }
        catch(RuntimeException ex){
            assertNotNull(ex);
        }
    }
}