package it.polimi.ingsw.view;

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
