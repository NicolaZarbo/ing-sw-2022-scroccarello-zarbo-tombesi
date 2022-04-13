package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.NoPlaceAvailableException;
import it.polimi.ingsw.model.token.Student;

public class Cloud {
    private final int id;
    private Student[] stud;
    private int dimension;

    public Cloud(Student[] students, int id){
        this.stud=new Student[students.length];
        this.dimension=students.length;
        for(int i=0;i<students.length;i++)
            stud[i]=students[i];
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
        if(stud[0]==null) return true;
        else return false;
    }

    public int getId() {return id;}

}
