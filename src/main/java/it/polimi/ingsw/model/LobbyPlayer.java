package it.polimi.ingsw.model;


import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.enumerations.TowerColor;

public class LobbyPlayer {
    private TowerColor towerColor;
    private Mage mage;
    private String nickname;

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
