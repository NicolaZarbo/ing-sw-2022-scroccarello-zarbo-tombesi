package it.polimi.ingsw.model;

public class Player {
    private final TowerColor colorT;
    private final Mage mage;
    Hand hand;
    Board board;
    private final int id;

    public Player(int id, Mage mag, Hand man, Board board){
        this.id=id;
        colorT= TowerColor.getColor(id);
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
}
