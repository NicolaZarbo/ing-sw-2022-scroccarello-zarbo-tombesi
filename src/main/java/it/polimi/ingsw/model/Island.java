package it.polimi.ingsw.model;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.model.token.Tower;

import java.util.ArrayList;

public class Island {
    private final int ID;
    private final ArrayList<Student> students;
    private final ArrayList<Tower> tower;//todo this should be a single tower, fix also all methods that access
    private final ArrayList<Island> subIslands;
    private int islandSize;

    public Island(int id) {
        this.ID = id;
        this.subIslands= new ArrayList<>();
        this.students = new ArrayList<Student>();
        this.tower = new ArrayList<Tower>();
        this.islandSize=1;
    }

    public int getID() {
        return this.ID;
    }

    public ArrayList<Island> getSubIslands() {
        return subIslands;
    }
    public void addSubIsland(Island added){
        this.subIslands.add(added);
    }

    public void incrementIslandSize() {
        this.islandSize ++;
    }
    /** returns the number of islands grouped together*/
    public int getIslandSize() {
        return islandSize;
    }

    //ritorna tutte le torri sull'isola attenzione ai null!(ovvero se viene chiamato e l'isola non ha torri)
    public ArrayList<Tower> getTowers() {
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