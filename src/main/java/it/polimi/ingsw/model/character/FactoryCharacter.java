package it.polimi.ingsw.model.character;

import it.polimi.ingsw.exceptions.CharacterNotFoundException;
import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.token.Student;

import java.util.ArrayList;

public class FactoryCharacter {
    /**Draws student from the bag when setting up characters */
    private static ArrayList<Student> drawStudents(int x, Bag bag){
        ArrayList<Student> drawn  = new ArrayList<>();
        for(int i =0;i<x; i++)
            drawn.add(bag.getToken()) ;
        return drawn;
    }
    /** Factory method used to create the right instance of character based on id in the parameters*/
    public static CharacterCard createCharacter(int id, Bag bag)
    {
        CharacterCard card;
        TokensCharacter c;
        switch (id) {
            case 1 -> {
                c = new Character1(id);
                c.addStudents(drawStudents(4, bag));
                card = c;
            }
            case 2, 6, 8 -> card = new TurnEffectCharacter(id);

            case 7 -> {
                c = new Character7(id);
                c.addStudents(drawStudents(6, bag));
                card = c;
            }
            case 9 -> card = new Character9(id);
            case 10 -> card = new Character10(id);
            case 11 -> {
                c = new Character11(id);
                c.addStudents(drawStudents(4, bag));
                card = c;
            }
            default -> card = null;
        }

        return card;
        }
    }

