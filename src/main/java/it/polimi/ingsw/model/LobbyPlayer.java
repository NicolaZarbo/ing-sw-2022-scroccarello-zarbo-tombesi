package it.polimi.ingsw.model;


import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.enumerations.TowerColor;

/**The customized player from the lobby. It is created after player chooses his custom parameters such as his team color (<b>tower color</b>), his <b>magician</b>, his <b>nickname</b>. */
public class LobbyPlayer {
    private TowerColor towerColor;
    private Mage mage;
    private String nickname;

    /**It creates the custom player.
     * @param mage the selected mage
     * @param nickname the selected nickame
     * @param towerColor the selected team color*/
    public LobbyPlayer(TowerColor towerColor, Mage mage, String nickname) {
        this.towerColor = towerColor;
        this.mage = mage;
        this.nickname = nickname;
    }

    /**@return the player's team color*/
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**@return the player's magician*/
    public Mage getMage() {
        return mage;
    }

    /**@return the player's nickname*/
    public String getNickname() {
        return nickname;
    }
}
