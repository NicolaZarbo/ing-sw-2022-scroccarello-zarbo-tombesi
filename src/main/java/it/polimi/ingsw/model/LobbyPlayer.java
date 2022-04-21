package it.polimi.ingsw.model;


import it.polimi.ingsw.model.token.TowerColor;

public class LobbyPlayer {
    private TowerColor towerColor;
    private Mage mage;
    private String nickname; //should we check the string input ? -serverside

    public LobbyPlayer(TowerColor towerColor, Mage mage, String nickname) {
        this.towerColor = towerColor;
        this.mage = mage;
        this.nickname = nickname;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public Mage getMage() {
        return mage;
    }

    public String getNickname() {
        return nickname;
    }
}
