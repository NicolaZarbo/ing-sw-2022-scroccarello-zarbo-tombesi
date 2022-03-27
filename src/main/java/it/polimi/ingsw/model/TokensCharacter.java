package it.polimi.ingsw.model;
import java.util.ArrayList;
public abstract class TokensCharacter extends CharacterCard{
    private int id;
    private int cost;
    private ArrayList<Student> students;

    public TokensCharacter (int id,ArrayList<Student> studs){
        this.id=id;
        this.students= studs;
    }
    public void addStudents(ArrayList<Student> studs){
        this.students.addAll(studs);
    }
    public Student getStudents(int studId){
        for (Student stud: this.students) {
            if(stud.getId()==studId)
                return stud;
        }
        return null;
    }

}
