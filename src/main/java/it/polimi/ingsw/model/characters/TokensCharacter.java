package it.polimi.ingsw.model.characters;
import it.polimi.ingsw.model.tokens.Student;

import java.util.ArrayList;
/**The class of characters with student tokens on them. Characters: 1, 7, 11.*/
public abstract class TokensCharacter extends CharacterCard{

    private final ArrayList<Student> students;

    /**It builds the card by initializing the list of students and the id of the card.
     * @param id the id of the card*/
    public TokensCharacter (int id){
        super(id);
        this.students= new ArrayList<>();
    }

    /**It adds a list of students to the card.
     * @param studs list of students to set*/
    public void addStudents(ArrayList<Student> studs){
        this.students.addAll(studs);
    }

    /**It adds one student token to the card.
     * @param stud the student you need to put on the card */
    public void addStudents(Student stud){
        this.students.add(stud);
    }

    /**@return every student on the card*/
    public ArrayList<Student> getStudents(){
        return this.students;
    }

    /**@param id id of the student
     * @return  a particular student based on the id
     * @exception NullPointerException if there isn't the token with the id on the card*/
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
