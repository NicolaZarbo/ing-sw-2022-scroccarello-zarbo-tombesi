package it.polimi.ingsw.model.character;

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
        if(id==1){
            TokensCharacter c= new Character1(id);
            c.addStudents(drawStudents(4, bag));
            card=c;
        }
        if(id==2||id==6||id==8)
            card= new TurnEffectCharacter(id);

        if(id == 7){
            TokensCharacter c= new Character7(id);
            c.addStudents( drawStudents(6,bag));
            card=c;
        }
        if(id==9)
            card=new Character9(id);
        if(id==10)
            card= new Character10(id);
        if(id == 11){
            TokensCharacter c= new Character11(id);
            c.addStudents(drawStudents(4,bag));
            card=c;
        }

        return card;
        }
    }

