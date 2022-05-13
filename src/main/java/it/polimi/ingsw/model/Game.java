package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.messages.server.ChangePhaseMessage;
import it.polimi.ingsw.messages.server.ChangeTurnMessage;
import it.polimi.ingsw.messages.server.MultipleServerMessage;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.model.token.Professor;
import it.polimi.ingsw.model.token.TokenColor;
import it.polimi.ingsw.model.token.TowerColor;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

public class Game extends Observable<ServerMessage> {

    private final boolean easy;
    private final int nPlayers;
    private final List<Island> islands;
    private  Player[] players;
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
    private Turn actionPhase;
    private Setup setupPhase;
    private Round planningPhase;
    private GameState actualState;
    private MultipleServerMessage multiMessage;





    /** creates a game without players, which are then created in a setup phase through players inputs*/
    public Game(boolean easy, int numberOfPlayer){
        actionPhase= new Turn(this);
        planningPhase= new Round(this);
        setupPhase = new Setup(this);
        this.easy=easy;
        this.nPlayers =numberOfPlayer;
        this.bag=new Bag(26,5);
        this.islands=Setup.createIslands(12,bag);
        this.clouds= Setup.createClouds(nPlayers);
        this.players =new Player[numberOfPlayer];
        this.teachers= Setup.createProfessor(5);
        this.motherNature=new MotherNature(islands.get(0).getID());
        if(!easy){
            this.characters= Setup.createCharacterCards(bag,9);
        }
        else{characters=null;}
        this.cardBonusActive=0;
        this.cardPlayedThisRound=new HashMap<>();
        planningPhase.setCloud();
        actualState=GameState.setupPlayers;
        multiMessage=null;
        //this.playIngOrder= Arrays.stream(this.players).map(Player::getId).toList();TODO
    }
    /** legacy constructor, still here for tests without players customization*/
    public Game(boolean easy, int nPlayers, int nIsole){
        this.easy=easy;
        this.nPlayers =nPlayers;
        ArrayList<LobbyPlayer>  lobbyPlayers= new ArrayList<>();
        for (int i = 0; i < nPlayers; i++) {
            if(nPlayers==4 && (i==2 || i==3))
                lobbyPlayers.add(new LobbyPlayer(TowerColor.getColor(i-2), Mage.getMage(i),"nick: "+i));
           else
               lobbyPlayers.add(new LobbyPlayer(TowerColor.getColor(i), Mage.getMage(i),"nick: "+i));
        }
        this.bag=new Bag(26,5);
        this.islands=Setup.createIslands(nIsole,bag);
        this.clouds= Setup.createClouds(nPlayers);
        this.players =Setup.createPlayer(easy, lobbyPlayers, bag);
        this.teachers= Setup.createProfessor(5);
        this.motherNature=new MotherNature(islands.get(0).getID());
        if(!easy){
            this.characters= Setup.createCharacterCards(bag,9);
        }
        else{characters=null;}
        this.cardBonusActive=0;
        this.cardPlayedThisRound=new HashMap<>();
        this.playIngOrder= Arrays.stream(this.players).map(Player::getId).toList();
        this.setupPhase=new Setup(this);
        this.actionPhase=new Turn(this);
        this.planningPhase=new Round(this);
        planningPhase.setCloud();
        for(int i=0;i<nPlayers;i++){
            this.cardPlayedThisRound.put(i,new AssistantCard(i*11,i*11,i*11,Mage.getMage(i)));
        }


    }

    @Override
    protected void notify(ServerMessage message) {
        super.notify(message);
    }
    public void groupMultiMessage(ServerMessage message){
        if (multiMessage!= null)
            multiMessage.addMessage(message);
        else multiMessage= new MultipleServerMessage(message);
    }
    public void sendMultiMessage(){
        multiMessage.serialize();
        this.notify(multiMessage);
        multiMessage=null;
    }

    public void moveToNextPhase(){
        int before = actualState.ordinal();
        if(before==4) {
            actualState = GameState.planPlayCard;
            currentPlayerId=playIngOrder.get(0);
        }
        else
            actualState= GameState.values()[before+1];
        groupMultiMessage(new ChangePhaseMessage(this));
        sendMultiMessage();
    }
    /**method only to make testing faster*/
    public void setManuallyGamePhase(GameState state){
        this.actualState=state;
    }

    public GameState getActualState() {
        return actualState;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
        this.playIngOrder= Arrays.stream(players).map(Player::getId).toList();
    }
    public boolean isLastPlayerTurn(){
        return this.playIngOrder.indexOf(this.currentPlayerId)==this.nPlayers-1;
    }

    public Round getPlanningPhase() {
        return planningPhase;
    }

    public Turn getActionPhase() {
        return actionPhase;
    }

    public Setup getSetupPhase() {
        return setupPhase;
    }

    public void changePlayerTurn(){
        int actualIndex=playIngOrder.indexOf(currentPlayerId);
        actualIndex+=1;
        if(actualIndex<nPlayers){
            currentPlayerId=playIngOrder.get(Math.floorMod(nPlayers,actualIndex));
            groupMultiMessage(new ChangeTurnMessage(this));
            sendMultiMessage();
        }
        else throw new NullPointerException("the phase should have moved on by now");
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
    public List<CharacterCard> getCharacters(){return characters;}
    public CharacterCard getCharacter(int id) {
        for (CharacterCard card: this.characters) {
            if(card.getId()==id)
                return card;
        }
        throw new CardNotFoundException();
    }
    public List<Integer> getPlayIngOrder() {
        return playIngOrder;
    }
    public void setPlayIngOrder(List<Integer> playIngOrder) {
        this.playIngOrder = playIngOrder;
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
        throw new NullPointerException("island not available");
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
    public Cloud[] getClouds(){
        return this.clouds;
    }
    public Player[] getPlayers(){
        return this.players;
    }

    public Player getPlayer(int playerId){
        for (Player player : this.players)
            if (player.getId() == playerId)
                return player;
        throw new NullPointerException("not existing player ");
    }


    public Professor getFromGame(TokenColor color){
        Professor temp=null;
        if(isProfessorOnGame(color)){
            temp=teachers[color.ordinal()];
            teachers[color.ordinal()]=null;
            return temp;
        }else throw new NullPointerException();

    }
    public boolean isProfessorOnGame(TokenColor color){
        return teachers[color.ordinal()] != null;
    }
    public List<Island> getIslandList() {return this.islands;}

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }
}
