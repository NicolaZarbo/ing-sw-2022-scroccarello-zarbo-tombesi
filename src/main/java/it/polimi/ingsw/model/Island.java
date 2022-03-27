package it.polimi.ingsw.model;
import java.util.ArrayList;
import java.util.List;

public class Island {
    private int ID;
    private ArrayList<Student> students;
    private ArrayList<Tower> towers;
    private int islandSize;

    public Island(int id){
        this.ID=id;
        this.students=new ArrayList<Student>();
        this.towers=new ArrayList<Tower>();
        this.islandSize=0;
    }
    public int getID() {return this.ID;}

    //ritorna tutte le torri sull'isola attenzione ai null!(ovvero se viene chiamato e l'isola non ha torri)
    public ArrayList<Tower> getTower(){
        return this.towers;
    }
    //metodo per aggiungere un singolo studente su un'isola (quando il giocatore di sua sponte lo aggiunge)
    public void addStudent(Student t){
        students.add(t);
    }

    public ArrayList<Student> getStudents(){
        return this.students;
    }

    //metodo per settare la torre su un'isola che non ce l'ha
    public void setTower(Tower t){
        this.towers.add(t);
    }

    private void mergeIslands(Island island, List<Island> isl) {
        this.islandSize++;
        for (int i=0;i<isl.size();) {
            if(isl.get(i).getID()==island.getID()) {
                isl.remove(i);
                break;
            }
        }
        for (int i=0;i<island.students.size();i++){
            this.students.add(island.students.get(i));
        }
        for (int i=0;i<island.towers.size();i++){
            this.towers.add(island.towers.get(i));
        }
    }


}