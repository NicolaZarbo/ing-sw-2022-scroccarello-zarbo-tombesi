package it.polimi.ingsw.model;

import java.util.List;

public class Game {

    private final boolean easy;
    private final int nPlayers;
    private List<Island> islands;
    private Player[] players;
    private Cloud[] clouds;
    private MotherNature motherNature;

    public Game(boolean easy, int nPlayer,int nIsole){
        this.easy=easy;
        this.nPlayers =nPlayer;
        this.players =Setup.createPlayer(easy, nPlayer);//genera giocatori con le lore rispettive board e mani
        this.clouds= Setup.createClouds(nPlayer);
        this.islands=Setup.createIslands(nIsole);
        this.motherNature=new MotherNature(islands.get(0).getID());

    }
    public List<Island> getIslands(){return islands;}

    public boolean isEasy() {
        return easy;
    }

    public int getNPlayers() {
        return nPlayers;
    }

    public MotherNature getMotherNature(){
        return this.motherNature;
    }
    public void setMotherNature(MotherNature m){
        this.motherNature=m;
    }

    public Cloud[] getClouds(){
        return this.clouds;
    }
    public void setClouds(Cloud[] cloud){
        this.clouds=cloud;
    }

    public Player[] getPlayers(){
        return this.players;
    }
}
