package it.polimi.ingsw.model;
import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.model.tokens.Tower;

import java.util.ArrayList;

/**The island tile of the game. It contains student tokens, tower tokens and it's characterized by a unique id. They can be conquered by a player if he's got the higher influence on it when mother nature visits the tile. Islands near conquered by the same player can be merged together.*/
public class Island {
    private final int ID;
    private final ArrayList<Student> students;
    private final ArrayList<Tower> tower;//todo this should be a single tower, fix also all methods that access
    private final ArrayList<Island> subIslands;
    private int islandSize;

    /**It builds the single and empty island tile.
     * @param id the id of the island*/
    public Island(int id) {
        this.ID = id;
        this.subIslands= new ArrayList<>();
        this.students = new ArrayList<>();
        this.tower = new ArrayList<>();
        this.islandSize=1;
    }

    /**@return the id of the island*/
    public int getID() {
        return this.ID;
    }

    /**@return list of all the students on the island and in all of its subIslands*/
    public ArrayList<Student> getEveryStudents(){
        ArrayList<Student> stud = new ArrayList<>(students);
        for (Island subIsland : subIslands) {
            stud.addAll(subIsland.getEveryStudents());
        }
        return stud;
    }

    /**@return list of all the towers on the island and in all of its subIslands*/
    public ArrayList<Tower> getEveryTower(){
        ArrayList<Tower> towers = new ArrayList<>(getTowers());
        for (Island subIsland : subIslands) {
            towers.addAll(subIsland.getEveryTower());
        }
        return towers;
    }

    /**It removes every tower from the island and from all of its subIslands.*/
    public void removeEveryTower(){
        tower.remove(0);
        for (Island subIsland : subIslands) {
            subIsland.removeEveryTower();
        }
    }

    /**@return all subIslands of the island*/
    public ArrayList<Island> getSubIslands() {
        return subIslands;
    }

    /**It adds the target island to the list of the subIslands of the island which the method is invoked on.
     * @param added the target island to insert on*/
    public void addSubIsland(Island added){
        this.subIslands.add(added);
    }

    /**It increments the size of the island when a merge is occurred.*/
    public void incrementIslandSize() {
        this.islandSize ++;
    }

    /**@return number of islands merged together within the island*/
    public int getIslandSize() {
        return islandSize;
    }

    /**@return list of towers on the island*/
    public ArrayList<Tower> getTowers() {
        return this.tower;
    }

    /**It adds a student token on the island.
     * @param s student token to insert*/
    public void addStudent(Student s) {
        students.add(s);
    }

    /**It adds to the list of students the set of students from the merged island.
     * @param s list of students to insert*/
    public void addAllStudents(ArrayList<Student> s) {
        this.students.addAll(s);
    }

    /**@return the list of all the students on the island*/
    public ArrayList<Student> getStudents() {
        return this.students;
    }

    /**It sets the tower on the island.
     * @param t the tower to insert*/
    public void setTower(Tower t) {
        this.tower.add(t);
    }

    /**It adds to the list of towers the set of towers from the merged island.
     * @param t list of towers to insert*/
    public void addAllTowers(ArrayList<Tower> t) {
        this.tower.addAll(t);
    }


}