package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.model.character.*;
import it.polimi.ingsw.model.token.Professor;
import it.polimi.ingsw.model.token.TokenColor;
import it.polimi.ingsw.observer.Observable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Game extends Observable<ServerMessage> {

    private final boolean easy;
    private final int nPlayers;
    private final List<Island> islands;
    private final Player[] players;
    private Cloud[] clouds;
    private MotherNature motherNature;
    private int currentPlayerId;
    private final HashMap<Integer, AssistantCard> cardPlayedThisRound;
    private List<Integer> playIngOrder;
    private final Bag bag;
    private final Professor[] teachers;
    private final List<CharacterCard> characters;
    private int cardBonusActive;
    private TokenColor targetColor;



    public List<Integer> getPlayIngOrder() {
        return playIngOrder;
    }
    public void setPlayIngOrder(List<Integer> playIngOrder) {
        this.playIngOrder = playIngOrder;
    }
    public AssistantCard getPlayedCard(int playerId){
        return cardPlayedThisRound.get(playerId);
    }

    public HashMap<Integer, AssistantCard> getAllCardPlayedThisRound() {
        return cardPlayedThisRound;
    }

    public void addCardPlayedThisRound(int playerId, AssistantCard playedCard) {
        this.cardPlayedThisRound.put(playerId,playedCard);
    }

    public Professor[] getTeachers() {
        return teachers;
    }

    public CharacterCard getCharacter(int id) {
        for (CharacterCard card: this.characters) {
            if(card.getId()==id)
                return card;
        }
        return null; //input Exception(?)
    }

    public Game(boolean easy, int nPlayer, int nIsole){
        this.easy=easy;
        this.nPlayers =nPlayer;
        this.bag=new Bag(10,5);
        this.islands=Setup.createIslands(nIsole,bag);
        this.clouds= Setup.createClouds(nPlayer);
        this.players =Setup.createPlayer(easy, nPlayer, bag);//genera giocatori con le lore rispettive board e mani
        this.teachers= Setup.createProfessor(5);
        this.motherNature=new MotherNature(islands.get(0).getID());
        if(!easy){
        this.characters= Setup.createCharacterCards(bag,9);
        }
        else{characters=null;}
        this.cardBonusActive=0;
        this.cardPlayedThisRound=new HashMap<>();
        this.playIngOrder= Arrays.stream(players).map(Player::getId).toList();
    }

    public boolean isBonusActive(int bonus) {
        return cardBonusActive==bonus;
    }
    public TokenColor getTargetColor() {
        return targetColor;
    }

    public void setTargetColor(TokenColor targetColor) {
        this.targetColor = targetColor;
    }
    public void setCardBonusActive(Integer cardBonus) {
        this.cardBonusActive=(cardBonus) ;
    }
    public void resetBonus(){
        this.cardBonusActive=0;
        this.targetColor=null;
    }

    //finds island based on id
    public Island getIsland(int id) throws NullPointerException{
        for (Island island: this.islands) {
            if(island.getID()==id)
                return island;
        }
        //throw new NullPointerException("no island with such id");
        return  null;
    }
    public Bag getBag(){
        return bag;
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
        boolean b= teachers[color.ordinal()] != null;

        return b;
    }
    public List<Island> getIslandList() {return this.islands;}

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
}
