package it.polimi.ingsw.model;

public class Player {
    private final TowerColor colorT;
    private final Mage mage;
    private Hand hand;
    private Board board;
    private final int id;

    public Player(int id, Mage mag, Hand man, Board board,TowerColor color){
        this.id=id;
        colorT= color;
        mage =mag;
        hand =man;
        this.board = board;
        
    }



    public int getId() {
        return id;
    }

    public Mage getMage() {
        return mage;
    }

    public TowerColor getColorT() {
        return colorT;
    }

    public Board getBoard(){
        return this.board;
    }

    public Hand getHand() {
        return hand;
    }
}
