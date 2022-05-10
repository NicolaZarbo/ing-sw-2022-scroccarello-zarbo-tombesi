package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.CharacterNotFoundException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.token.Student;

import java.util.ArrayList;

public class FactoryCharacter {
    private static ArrayList<Student> drawStudents(int x, Bag bag){
        ArrayList<Student> drawn  = new ArrayList<>();
        for(int i =0;i<x; i++)
            drawn.add(bag.getToken()) ;
        return drawn;
    }
    public static CharacterCard createCharacter(int id, Bag bag)
    {
        CharacterCard card=null;
        TokensCharacter c;
        switch(id){

            case 1:
                c = new Character1(id);
                c.addStudents(drawStudents(4, bag));
                card=c;
                break;

            case 2:

            case 6:

            case 8:
                card = new TurnEffectCharacter(id);
                break;
            //case 2,6 and 8 have been merged from the IDE

            case 7:
                c = new Character7(id);
                c.addStudents( drawStudents(6,bag));
                card=c;
                break;

            case 9:
                card=new Character9(id);
                break;

            case 10:
                card= new Character10(id);
                break;

            case 11:
                c = new Character11(id);
                c.addStudents(drawStudents(4,bag));
                card=c;
                break;

            default: throw new CharacterNotFoundException("No results for character "+id);
        }

        return card;
        }
    }

