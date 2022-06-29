package it.polimi.ingsw.view.simplifiedobjects;

import java.util.List;

public class SimplifiedIsland {
    private List<Integer> students;
    private final int towers;
    private final int dimension;
    private final int islandId;
    private final int towerColor;
    private final List<SimplifiedIsland> subIslands;

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
