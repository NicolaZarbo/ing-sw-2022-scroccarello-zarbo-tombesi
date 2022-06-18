package it.polimi.ingsw.model.characters;
import it.polimi.ingsw.model.tokens.Student;

import java.util.ArrayList;
public abstract class TokensCharacter extends CharacterCard{

    private final ArrayList<Student> students;
    public TokensCharacter (int id){
        super(id);
        this.students= new ArrayList<>();
    }
    /**Adds a list of students to the card  */
    public void addStudents(ArrayList<Student> studs){
        this.students.addAll(studs);
    }
    /**@param stud the student you need to put on the card */
    public void addStudents(Student stud){
        this.students.add(stud);
    }

    /** Returns every student on the card*/
    public ArrayList<Student> getStudents(){
        return this.students;
    }
    /** Returns a particular student based on an id in the parameter*/
    protected Student getStudent(int id)throws NullPointerException{
        Student stud;
        for (Student student: students) {
            if (student.getId()==id) {
                stud = student;
                this.students.remove(stud);
                return stud;
            }
        }
        throw new NullPointerException("no student with id: "+id+" present on card "+getId());
    }
}