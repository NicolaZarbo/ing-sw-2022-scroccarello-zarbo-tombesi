package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bag {
    int left;
    private  List<Student> tokenLeft;

    public Bag(int pedForColor, int numColor){
        int size=pedForColor*numColor;
        left =size;
        Student[] temparr=new Student[size];
        int k=0;
        for(int i=0;i<numColor;i++)
            for(int j=0;j<pedForColor;j++){
                Student temp=new Student(k, TokenColor.getColor(i+3));
                temparr[k]=temp;
                k++;
            }
        tokenLeft =new LinkedList<>(Arrays.asList(temparr));
    }
    public boolean isToken(int i){
        boolean present=false;
            if(tokenLeft.get(i)!=null)
                present=true;

        return present;
    }

public Student getToken(){
    Random rand = new Random();
    int i=rand.nextInt(tokenLeft.size());
    Student temp= tokenLeft.get(i);
    tokenLeft.remove(i);
    return temp;
}
}
