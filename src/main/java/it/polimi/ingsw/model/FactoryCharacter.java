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
        if(id==0){
            TokensCharacter c= new Character1(id);
            c.addStudents(drowStudents(4, bag));
            card=c;
        }
        if(id==1||id==3||id==5||id==7||id==8)
            card= new TurnEffectCharacter(id);
        if(id==2)
            card= new Character3(id);
        if(id==4)
            card=new Character5(id);
        if(id == 6){
            TokensCharacter c= new Character7(id);
            c.addStudents( drowStudents(6,bag));
            card=c;
        }
        if(id==9)
            card= new Character10(id);
        if(id == 10){
            TokensCharacter c= new Character11(id);
            c.addStudents(drowStudents(4,bag));
            card=c;
        }

        return card;
    }
}
