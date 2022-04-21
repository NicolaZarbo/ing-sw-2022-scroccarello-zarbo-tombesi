package it.polimi.ingsw.model;

import it.polimi.ingsw.model.token.TowerColor;

public class Player {
    private final TowerColor colorT;
    private final Mage mage;
    private Hand hand;
    private Board board;
    private final int id;
    private final String nickname;

    public Player(LobbyPlayer prePlayer, int id, Hand man, Board board){
        this.id=id;
        this.nickname=prePlayer.getNickname();
        colorT= prePlayer.getTowerColor();
        mage =prePlayer.getMage();
        hand =man;
        this.board = board;
        
    }


    public String getNickname() {
        return nickname;
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
