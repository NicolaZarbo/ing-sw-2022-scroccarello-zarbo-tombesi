package it.polimi.ingsw.model;
import it.polimi.ingsw.model.tokens.Student;

/**The cloud tile in the center of the game table which contains three student tokens. It is accessible to all players at the end of their turn or when a related <b>character card</b> is activated (expert mode only).*/
public class Cloud {
    private final int id;
    private Student[] stud;
    private final int dimension;

    /**It builds the cloud by assigning it an id and setting the students
     * @param id id characterizing the cloud
     * @param students set of student tokens on the cloud*/
    public Cloud(Student[] students, int id){
        this.stud=new Student[students.length];
        this.dimension=students.length;
        System.arraycopy(students, 0, stud, 0, students.length);
        this.id=id;
    }

    /**@return set of student tokens on the cloud*/
    public Student[] getStud() {
        return stud;
    }

    /**It sets the student tokens on the cloud.
     * @param newStudents tokens to insert*/
    public void setStud(Student[] newStudents){
        stud=newStudents;
    }

    /**@return the id of the cloud*/
    public int getId() {return id;}

}
