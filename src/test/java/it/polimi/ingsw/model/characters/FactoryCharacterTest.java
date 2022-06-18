package it.polimi.ingsw.model.characters;

import it.polimi.ingsw.GameStub;
import it.polimi.ingsw.model.Game;
import junit.framework.TestCase;

import java.util.ArrayList;

public class FactoryCharacterTest extends TestCase {
    Game gameTest;
    public void setUp() throws Exception {
        super.setUp();
        this.gameTest=new GameStub(false,4,12);
    }

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
    public void testNotDisponibleCharacter(){
        try{
            CharacterCard cardCrasher=FactoryCharacter.createCharacter(4,gameTest.getBag());
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
        try{
            CharacterCard cardCrasher=FactoryCharacter.createCharacter(13,gameTest.getBag());
        }
        catch(RuntimeException e){
            System.out.println(e.getMessage());
        }
    }
}