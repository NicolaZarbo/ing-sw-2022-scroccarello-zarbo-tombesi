package it.polimi.ingsw.model;
import java.util.ArrayList;
public class Island {
    private int ID;
    private ArrayList<Student> students;
    private ArrayList<Tower> tower;

    public Island(int id) {
        this.ID = id;
        this.students = new ArrayList<Student>();
        this.tower = new ArrayList<Tower>();
    }

    public int getID() {
        return this.ID;
    }

    //ritorna tutte le torri sull'isola attenzione ai null!(ovvero se viene chiamato e l'isola non ha torri)
    public ArrayList<Tower> getTower() {
        return this.tower;
    }

    //metodo per aggiungere un singolo studente su un'isola (quando il giocatore di sua sponte lo aggiunge)
    public void addStudent(Student t) {
        students.add(t);
    }

    //metodo per mergiare gli studenti di due isole
    public void addAllStudents(ArrayList<Student> s) {
        this.students.addAll(s);
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    //metodo per settare la torre su un'isola che non ce l'ha
    public void setTower(Tower t) {
        this.tower.add(t);
    }

    //metodo per mergiare le torri di due isole
    public void addAllTowers(ArrayList<Tower> t) {
        this.tower.addAll(t);
    }
}