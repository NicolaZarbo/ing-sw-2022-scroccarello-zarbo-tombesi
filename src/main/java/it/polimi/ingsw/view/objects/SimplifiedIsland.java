package it.polimi.ingsw.view.objects;

import java.util.List;

public class SimplifiedIsland {
    private List<Integer> students;
    private int towers;
    private int dimension;
    private int islandId;
    private int towerColor;

    public SimplifiedIsland(List<Integer> studentsId, int numberOfTowers, int dimension, int islandId,int towerColor) {
        this.students = studentsId;
        this.towers = numberOfTowers ;
        this.islandId = islandId;
        this.dimension=dimension;
        this.towerColor=towerColor;
    }

    public int getTowerColor() {
        return towerColor;
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
