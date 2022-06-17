package it.polimi.ingsw.model;

import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.enumerations.TokenColor;

import java.util.*;
import it.polimi.ingsw.exceptions.*;
/**<p>It represents the bag of the game which contains all the student tokens of the game.</p>
 */
public class Bag {
    private int left;
    private final List<Student> tokenLeft;

    /**It builds a bag with the required number of tokens.
     * @param numColor number of colors
     * @param pedForColor number of tokens for each color*/
    public Bag(int pedForColor, int numColor){
        int size=pedForColor*numColor;
        this.left =size;
        tokenLeft=new ArrayList<>(size);
        int id=0;
        for(int i=0;i<numColor;i++)
            for(int j=0;j<pedForColor;j++){
                tokenLeft.add(new Student(id, TokenColor.getColor(i)));
                id++;
            }
    }

    /**It randomly extracts one token from the bag.
     * @return student token
     * @exception EmptyBagException if there are no tokens left*/
    public Student getToken(){
         if(left>0){
             Random rand = new Random();
             int i=rand.nextInt(0,left);
             Student temp= tokenLeft.get(i);
             tokenLeft.remove(i);
             left--;
             return temp;
         }
        else throw new EmptyBagException("no tokens left");
    }

    /**
     * @param nIslands number of islands on game
     * @return shuffled list of students*/
    public ArrayList<Student> setupStudents(int nIslands){
        ArrayList<Student> students = new ArrayList<>();
        int molt=left/(nIslands-2);
        for (int i = 0; i < nIslands-2; i++) {
            students.add(this.tokenLeft.get(i*molt));
        }
        tokenLeft.removeAll(students);
        left-=(nIslands-2);
        Collections.shuffle(students);
        return students;
    }

    /**It states if there are still tokens in the bag or not.
     * @return: •true: there are no tokens left
     * <p>•false: there is at least one token left</p>*/
    public boolean isEmpty() {
        return left<=0;
    }
}
