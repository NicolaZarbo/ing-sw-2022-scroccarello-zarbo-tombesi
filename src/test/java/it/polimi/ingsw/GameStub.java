package it.polimi.ingsw;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.enumerations.TowerColor;
import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Arrays;

/**The stub class intended to create the game without player's customization. It contains a construtor to support testing and other methods intended for the same task.
 * @see Game*/
public class GameStub extends Game {

    /**It creates a testing game
     * @param easy difficulty mode to test
     * @param nIsole number of islands to test
     * @param nPlayers number of players to test
     * */
    public GameStub(boolean easy, int nPlayers, int nIsole){
        super(easy,nPlayers);
        ArrayList<LobbyPlayer> lobbyPlayers= new ArrayList<>();
        for (int i = 0; i < nPlayers; i++) {
            if(nPlayers==4 && (i==2 || i==3))
                lobbyPlayers.add(new LobbyPlayer(TowerColor.getColor(i-2), Mage.getMage(i),"nick: "+i));
            else
                lobbyPlayers.add(new LobbyPlayer(TowerColor.getColor(i), Mage.getMage(i),"nick: "+i));
        }
        this.setPlayers(Setup.createPlayer(easy,lobbyPlayers,this.getBag()));
        this.setPlayIngOrder(Arrays.stream(this.getPlayers()).map(Player::getId).toList());

        for(int i=0;i<nPlayers;i++){
            this.getAllCardPlayedThisRound().put(i,new AssistantCard(i*11,i*11,i*11,Mage.getMage(i)));
        }
    }
    /**It swaps the state of the game manually.
     * @param state the state to set*/
    public void setManuallyGamePhase(GameState state){
        while(this.getActualState()!=state)
            this.moveToNextPhase();
    }
}
