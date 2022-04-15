package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.NoPlaceAvailableException;
import it.polimi.ingsw.model.token.Student;

public class Cloud {
    private final int id;
    private Student[] stud;
    private final int dimension;

    public Cloud(Student[] students, int id){
        this.stud=new Student[students.length];
        this.dimension=students.length;
        System.arraycopy(students, 0, stud, 0, students.length);
        this.id=id;
    }

    public int getDimension() {
        return dimension;
    }

    public Student[] getStud() {
        return stud;
    }

    public void setStud(Student[] newStudents){
        stud=newStudents;
    }

    public void putStudent(Student stunew)throws NoPlaceAvailableException,NullPointerException{
        if(stunew==null) throw new NullPointerException();

        else{
            for(int i=0;i<stud.length;i++)
                if(stud[i]==null) {
                    stud[i] = stunew;
                    return;
                }
            throw new NoPlaceAvailableException();
        }
    }

    public boolean isEmpty(){
        return stud[0] == null;
    }

    public int getId() {return id;}

}
