package it.polimi.ingsw.view.simplifiedobjects;

import it.polimi.ingsw.exceptions.NoTokenFoundException;

import java.util.List;

public class SimplifiedBoard {
    private final Integer[][] diningRoom;
    private final Integer[] professorTable;
    private final List<Integer> entrance;
    private final int towersLeft;
    private final boolean[][] coinDN;


    public SimplifiedBoard(Integer[][] diningRoom, Integer[] professorTable, List<Integer> entrance, int towersLeft, boolean[][] coinDN ) {
        this.diningRoom = diningRoom;
        this.professorTable = professorTable;
        this.entrance = entrance;
        this.towersLeft = towersLeft;
        this.coinDN=coinDN;
    }
    public boolean hasStudentOfColorInEntrance(int tokenColor){
        for (Integer id:entrance) {
            if(id/26==tokenColor)
                return true;
        }
        return false;
    }
    public int getStudentFromColorInEntrance(int tokenColor){
        for (Integer id:entrance) {
            if(id/26==tokenColor)
                return id;
        }
        throw new NoTokenFoundException("no student of color: "+tokenColor+" in entrance");
    }
    public boolean hasStudentOfColorInDining(int tokenColor){
        for (Integer id:diningRoom[tokenColor]) {
            if(id/26==tokenColor)
                return true;
        }
        return false;
    }
    public int getStudentFromColorInDining(int tokenColor){
        for (Integer id:diningRoom[tokenColor]) {
            if(id/26==tokenColor)
                return id;
        }
        throw new NoTokenFoundException("no student of that color in dining room");
    }

    public boolean[][] getCoinDN() {
        return coinDN;
    }

    public Integer[][] getDiningRoom() {
        return diningRoom;
    }

    public Integer[] getProfessorTable() {
        return professorTable;
    }

    public List<Integer> getEntrance() {
        return entrance;
    }

    public int getTowersLeft() {
        return towersLeft;
    }
}
