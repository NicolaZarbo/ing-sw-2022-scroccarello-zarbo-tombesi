package it.polimi.ingsw.view.simplifiedobjects;

import it.polimi.ingsw.exceptions.NoTokenFoundException;

import java.util.List;

public class SimplifiedBoard {
    private Integer[][] diningRoom; //array in cui ci va l'id degli studenti presenti
    private Integer[] professorTable;//array in cui va l'id del professore se lo ha
    private List<Integer> entrance;
    private int towersLeft; //array che rappresenta le towers presenti
    private boolean[][] coinDN;//se true c'è ancora il coin da prendere


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
        throw new NoTokenFoundException("no student of that color in entrance");
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