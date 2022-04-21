package model;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final boolean easy;
    private final int nPlayers;
    private List<Island> islands;
    private Player[] players;
    private Cloud[] clouds;
    private MotherNature motherNature;

    public Game(boolean easy, int nplayer){
        this.easy=easy;
        this.nPlayers =nplayer;
        this.players =Setup.createPlayer(easy, nplayer,0);//genera giocatori con le lore rispettive board e mani
        this.clouds= Setup.createClouds(nplayer,0);
        //createislands
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
}
