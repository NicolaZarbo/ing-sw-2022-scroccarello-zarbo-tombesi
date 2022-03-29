package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class FactoryCharacter {
    private static ArrayList<Student> drawX(int x, Bag bag){
        ArrayList<Student> drawn  = new ArrayList<>();
        for(int i =0;i<x; i++)
            drawn.add(bag.getToken()) ;
        return drawn;
    }
    //I still need to implement most of it, ecxept cards 1,7,11 and 5, the implementetion of the others will be strightfoward TODO
    public static CharacterCard createCharacter(int id, Bag bag)
    {
        if(id==0){
            return new CharacterOne(id, drawX(4, bag));
        }

        if(id == 6){
            return new Character7(id, drawX(6,bag) );
        }
        if(id==9)
            return new Character10();
        if(id == 10){
            return new Character11(id, drawX(4,bag) );
        }
        return null;
    }
}
