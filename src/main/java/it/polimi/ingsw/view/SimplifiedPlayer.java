package it.polimi.ingsw.view;


import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.token.TokenColor;

import java.util.ArrayList;
import java.util.Arrays;

public class SimplifiedPlayer {

    private String username;
    private int towerColor;//towercolor assigned to player 1-black,2-white,3-gray
    //player hand rapresentation
    private int mage;
    private int coin;
    private boolean[] assistantCards; //card se true giocabile
    private boolean[] discardedCards; //card se true scartata
    //player board rapresentation
    private int[][] diningRoom; //array in cui ci va l'id degli studenti presenti
    private int[] professorTable;//array in cui va l'id del professore se lo ha
    private int towersLeft; //array che rappresenta le towers presenti
    private boolean[][] coinDN;//se true c'è ancora il coin da prendere
    private int entranceSize;

    public SimplifiedPlayer(String username, int towerColor, int coin,int towerNumber, int entranceSize) {
        this.username = username;
        this.towerColor = towerColor;
        this.coin = coin;
        this.assistantCards = new boolean[10];
        Arrays.fill(assistantCards, true);
        this.discardedCards = new boolean[10];
        Arrays.fill(discardedCards, false);
        this.diningRoom = new int[5][10];
        this.professorTable = new int[5];
        this.towersLeft=towerNumber;
        this.coinDN = new boolean[5][3];
        for (boolean[] booleans : coinDN) Arrays.fill(booleans, true);
        this.entranceSize = entranceSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTowerColor() {
        return towerColor;
    }

    public void setTowerColor(int towerColor) {
        this.towerColor = towerColor;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public boolean[] getAssistantCards() {
        return assistantCards;
    }

    public void setAssistantCards(boolean[] assistantCards) {
        this.assistantCards = assistantCards;
    }
    public int getMage() {
        return mage;
    }

    public void setMage(int mage) {
        this.mage = mage;
    }
    public boolean[] getDiscardedCards() {
        return discardedCards;
    }

    public void setDiscardedCards(boolean[] discardedCards) {
        this.discardedCards = discardedCards;
    }

    public int[][] getDiningRoom() {
        return diningRoom;
    }

    public void setDiningRoom(int[][] diningRoom) {
        this.diningRoom = diningRoom;
    }

    public int[] getProfessorTable() {
        return professorTable;
    }

    public void setProfessorTable(int[] professorTable) {
        this.professorTable = professorTable;
    }

    public int getTowersLeft() {
        return towersLeft;
    }

    public void setTowersLeft(int towersLeft) {
        this.towersLeft = towersLeft;
    }

    public boolean[][] getCoinDN() {
        return coinDN;
    }

    public void setCoinDN(boolean[][] coinDN) {
        this.coinDN = coinDN;
    }

    public int getEntranceSize() {
        return entranceSize;
    }

    public void setEntranceSize(int entranceSize) {
        this.entranceSize = entranceSize;
    }


}
