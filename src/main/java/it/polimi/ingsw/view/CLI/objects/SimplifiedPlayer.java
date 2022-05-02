package it.polimi.ingsw.view.CLI.objects;


import java.util.Arrays;

public class SimplifiedPlayer {

    private String username;
    private int id;
    private SimplifiedBoard board;
    private int towerColor;//towercolor assigned to player 1-black,2-white,3-gray
    //player hand representation
    private int mage;
    private int coin;
    private boolean[] assistantCards; //card se true giocabile
    private boolean[] discardedCards; //card se true scartata



    public SimplifiedPlayer(String username,int id, int towerColor, int coin, SimplifiedBoard board,int mage) {
        this.username = username;
        this.id=id;
        this.towerColor = towerColor;
        this.coin = coin;
        this.assistantCards = new boolean[10];
        Arrays.fill(assistantCards, true);
        this.discardedCards = new boolean[10];
        Arrays.fill(discardedCards, false);
        this.board=board;
    }

    public SimplifiedBoard getBoard() {
        return board;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setBoard(SimplifiedBoard board) {
        this.board = board;
    }
}
