package it.polimi.ingsw.model;

import java.util.ArrayList;

public class FactoryCharacter {
    private static ArrayList<Student> drowStudents(int x, Bag bag){
        ArrayList<Student> drawn  = new ArrayList<>();
        for(int i =0;i<x; i++)
            drawn.add(bag.getToken()) ;
        return drawn;
    }
    //I still need to implement most of it, ecxept cards 1,7,11 and 5, the implementetion of the others will be strightfoward TODO
    public static CharacterCard createCharacter(int id, Bag bag)
    {
        CharacterCard card=null;
        if(id==1){
            TokensCharacter c= new Character1(id);
            c.addStudents(drowStudents(4, bag));
            card=c;
        }
        if(id==2||id==4||id==6||id==8)
            card= new TurnEffectCharacter(id);
        if(id==3)
            card= new Character3(id);
        if(id==5)
            card=new Character5(id);
        if(id == 7){
            TokensCharacter c= new Character7(id);
            c.addStudents( drowStudents(6,bag));
            card=c;
        }
        if(id==9)
            card=new Character9(id);
        if(id==10)
            card= new Character10(id);
        if(id == 11){
            TokensCharacter c= new Character11(id);
            c.addStudents(drowStudents(4,bag));
            card=c;
        }

        return card;
    }
}
