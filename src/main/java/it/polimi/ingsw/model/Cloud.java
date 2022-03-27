package it.polimi.ingsw.model;
import java.util.LinkedList;
public class Cloud {
    private final int id;//per identificare la nuvola
    private Student[] stud;

    public Cloud(Student[] arr, int id){
        this.stud=new Student[arr.length];
        for(int i=0;i<arr.length;i++)
            stud[i]=arr[i];
        // System.arraycopy(arr, 0, stud, 0, arr.length); metodo più compatto e alternativo
        this.id=id;

    }

    public Student[] getStud() {
        return stud;
    }

    public void setStud(Student[] temps){
        stud=temps;
    }

    public void putStudent(Student stunew){
        if(stud[stud.length-1]==null){
            boolean put=false;
            for(int i=0;i<stud.length;i++)
                if(!put && stud[i]==null) {
                    stud[i] = stunew;
                    put=true;
                }

        }
    }
    public boolean isStud(){
        boolean b=false;
        if(stud[0]!=null)
            b=true;
        return b;

    }

    public int getId() {
        return id;
    }
}
