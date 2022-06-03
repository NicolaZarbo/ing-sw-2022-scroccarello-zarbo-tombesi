package it.polimi.ingsw.view.simplifiedobjects;

import java.util.ArrayList;
import java.util.List;

public class SimplifiedIsland {
    private List<Integer> students;
    private int towers;
    private int dimension;
    private int islandId;
    private int towerColor;
    private List<SimplifiedIsland> subIslands;

    public SimplifiedIsland(List<Integer> studentsId,List<SimplifiedIsland> subIsland, int numberOfTowers, int dimension, int islandId,int towerColor) {
        this.students = studentsId;
        this.subIslands =subIsland;
        this.towers = numberOfTowers ;
        this.islandId = islandId;
        this.dimension=dimension;
        this.towerColor=towerColor;
    }

    public int getTowerColor() {
        return towerColor;
    }
    /*
    public ArrayList<SimplifiedIsland> getAllSubIsland(){
        ArrayList<SimplifiedIsland> everyIsland= new ArrayList<>();
        for (SimplifiedIsland subIsland: subIslands) {
            everyIsland.addAll(allGetter(subIsland));
        }
        return everyIsland;
    }
    private ArrayList<SimplifiedIsland> allGetter(SimplifiedIsland subIsland){
        ArrayList<SimplifiedIsland> recursiveCon= new ArrayList<>();
        for (SimplifiedIsland subSub:subIsland.subIslands) {

        }
    }

     */

    public List<SimplifiedIsland> getSubIslands() {
        return subIslands;
    }
    public int getEntireNumberOfTower(){
        int n=getNumberOfTowers();
        for (SimplifiedIsland subIsland: subIslands) {
            n+=subIsland.getEntireNumberOfTower();
        }
        return n;
    }
    public List<Integer> getStudents() {
        return students;
    }

    public void setStudents(List<Integer> students) {
        this.students = students;
    }

    public int getNumberOfTowers() {
        return towers;
    }

    public int getIslandId() {
        return islandId;
    }

    public int getDimension() {
        return dimension;
    }

    public int getTowers() {
        return towers;
    }
}
