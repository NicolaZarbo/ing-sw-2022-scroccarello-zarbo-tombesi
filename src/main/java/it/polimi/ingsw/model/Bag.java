package it.polimi.ingsw.model;

import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.model.token.TokenColor;

import java.util.*;
import it.polimi.ingsw.exceptions.*;
public class Bag {
    private int left;
    private final List<Student> tokenLeft;

    public Bag(int pedForColor, int numColor){
        int size=pedForColor*numColor;
        left =size;
        Student[] temparr=new Student[size];
        int k=0;
        for(int i=0;i<numColor;i++)
            for(int j=0;j<pedForColor;j++){
                Student temp=new Student(k, TokenColor.getColor(i));
                temparr[k]=temp;
                k++;
            }
        tokenLeft =new LinkedList<>(Arrays.asList(temparr));
    }
    public boolean isToken(int i){
        boolean present= tokenLeft.get(i) != null;
        return present;
    }

    public Student getToken(){
         if(left>0){
             Random rand = new Random();
             int i=rand.nextInt(left);
             Student temp= tokenLeft.get(i);
             tokenLeft.remove(i);
             left--;
             return temp;
         }
        else throw new EmptyBagException("no tokens left");
    }

    public ArrayList<Student> setupStudents(int nIslands){
        ArrayList<Student> students = new ArrayList<>();
        int molt=left/(nIslands-2);
        for (int i = 0; i < nIslands-2; i++) {
            students.add(this.tokenLeft.get(i*molt));
        }
        Collections.shuffle(students);
        return students;
    }
}
