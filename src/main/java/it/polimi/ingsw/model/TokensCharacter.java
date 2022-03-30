package it.polimi.ingsw.model;
import java.util.ArrayList;
public abstract class TokensCharacter extends CharacterCard{

    private ArrayList<Student> students;
    public TokensCharacter (int id){
        super(id);
        this.students= new ArrayList<>();
    }
    public void addStudents(ArrayList<Student> studs){
        this.students.addAll(studs);
    }
    public void addStudents(Student stud){
        this.students.add(stud);
    }

    public ArrayList<Student> getStudents(){
        return this.students;
    }
    protected Student getStudent(int id){
        Student stud;
        for (Student student: students) {
            if (student.getId()==id){
                stud =student;
                this.students.remove(stud);
                return stud;
            }
        }
        return null;
    }
}
