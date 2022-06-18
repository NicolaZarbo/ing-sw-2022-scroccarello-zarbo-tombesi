package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.enumerations.TowerColor;

/**The player of the game. It is created during setup phase using the information coming from the lobby customization. The player is given, in addition to the custom information, a <b>hand</b>, a <b>board</b> and an <b>id</b> which are all of them unique for each player.*/
public class Player {
    private final TowerColor colorT;
    private final Mage mage;
    private Hand hand;
    private Board board;
    private final int id;
    private final String nickname;

    /**It creates the player of the game.
     * @param prePlayer information about player's customization
     * @param id id of the player
     * @param hnd the hand of the player
     * @param board the board of the player*/
    public Player(LobbyPlayer prePlayer, int id, Hand hnd, Board board){
        this.id=id;
        this.nickname=prePlayer.getNickname();
        colorT= prePlayer.getTowerColor();
        mage =prePlayer.getMage();
        hand =hnd;
        this.board = board;
    }

    /**@return the nickname of the player*/
    public String getNickname() {
        return nickname;
    }

    /**@return the id of the player*/
    public int getId() {
        return id;
    }

    /**@return the magician of the player*/
    public Mage getMage() {
        return mage;
    }

    /**@return the tower color of the player (team color)*/
    public TowerColor getColorT() {
        return colorT;
    }

    /**@return the board of the player*/
    public Board getBoard(){
        return this.board;
    }

    /**@return the hand of the player*/
    public Hand getHand() {
        return hand;
    }
}
