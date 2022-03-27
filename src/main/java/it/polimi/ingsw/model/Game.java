package it.polimi.ingsw.model;

import java.util.List;

public class Game {

    private final boolean easy;
    private final int nPlayers;
    private List<Island> islands;
    private Player[] players;
    private Cloud[] clouds;
    private MotherNature motherNature;
    private Professor[] teachers;
    //manca array dei professori non ancora assegnati, che vanno inizializzati nel setup!

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

    public Player getPlayer(int playerId){
        Player temp=null;
        for(int j=0;j<this.players.length;j++)
            if(this.players[j].getId()==playerId)
                temp=this.players[j];
        return temp;
    }


    public Professor getFromGame(TokenColor color){
        Professor temp=null;
        if(isProfessorOnGame(color)){
            temp=teachers[color.ordinal()];
            teachers[color.ordinal()]=null;
        }
        return temp;
    }
    public boolean isProfessorOnGame(TokenColor color){
        boolean b=false;
        if(teachers[color.ordinal()]!=null)
            b=true;

        return b;
    }
}
