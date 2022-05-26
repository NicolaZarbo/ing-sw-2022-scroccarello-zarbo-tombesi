package it.polimi.ingsw.view;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.messages.servermessages.*;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class  CentralView extends Observable<ClientMessage> implements Observer<ServerMessage> {
    private List<SimplifiedIsland> islands;
    private List<Integer[]> clouds;
    private List<SimplifiedPlayer> players;
    private int mother;
    private List<Integer> characters;
    private Map<Integer,List<Integer>> studentsOnCard;
    private Map<Integer,Integer> costOfCard;
    private String name;
    private boolean easy;
    private SimplifiedPlayer personalPlayer;
    private int activeCharacter;
    private GameState state;
    private int turnOf;
    private int id;
    private ArrayList<Integer> playedCardThisTurn;
    private int cardYouPlayed;
    private final UserInterface clientScreen;
    private int studentMoved;

    public List<SimplifiedIsland> getIslands() {
        return islands;
    }

    public List<SimplifiedPlayer> getPlayers() {
        return players;
    }

    public List<Integer[]> getClouds() {
        return clouds;
    }

    public int getMother() {
        return mother;
    }

    public String getName() {
        return name;
    }

    public boolean isEasy() {
        return easy;
    }

    public SimplifiedPlayer getPersonalPlayer() {
        return personalPlayer;
    }

    public int getTurnOf() {
        return turnOf;
    }

    public ArrayList<Integer> getPlayedCardThisTurn() {
        return playedCardThisTurn;
    }

    public CentralView(UserInterface userInterface){
        this.clientScreen=userInterface;
        this.state=GameState.setupPlayers;
        this.id=-1;
    }
    public void errorFromServer(ErrorMessageForClient message){
        if(message.getTargetPlayerId()==id|| message.getTargetPlayerId()==-1) {
            clientScreen.showError(message.getErrorInfo());
            seeAction();
        }
    }
    public void setView(WholeGameMessage message) {
        islands=message.getIslands();
        easy= message.isEasy();
        clouds=message.getClouds();
        players=message.getModelPlayers();
        players=message.getModelPlayers();
        mother = message.getMotherNature();
        playedCardThisTurn= new ArrayList<>(players.size());
        for (SimplifiedPlayer pl: players) {
            if(pl.getUsername().equals( name))
                personalPlayer=pl;
        }
        characters=message.getCharacters();
        studentsOnCard=message.getCardStudents();
        costOfCard=message.getCardCosts();
        activeCharacter= message.getActiveCharacter();
        clientScreen.showView();
        if(state==GameState.actionMoveStudent||state==GameState.actionMoveMother)
            seeAction();
    }
    /** used to return to the right input point*/
    public void seeAction() {
        if(!isYourTurn())
            return;
        switch (state) {
            case planPlayCard -> clientScreen.showHand();
            case actionMoveMother -> clientScreen.askToMoveMother();
            case actionMoveStudent -> clientScreen.askToMoveStudent();
            case actionChooseCloud -> clientScreen.showClouds();
        }
    }
    public void setName(String name){
        this.name=name;
    }
    public void cloudUpdate(CloudMessage message){
        this.clouds= message.getClouds();
    }
    public void motherPositionUpdate(MotherPositionMessage message){
        this.mother=message.getMotherPosition();
    }
    public void islandsUpdate(IslandsMessage message){

    this.islands=message.getIslandList();
    }
    /** adds to card to the list of played card in this turn, this way the players know which card he can or cannot use*/
    public void playedAssistantUpdate(PlayedAssistantMessage message){
        playedCardThisTurn.add(message.getPlayedCardId()%10);
        if(message.getPlayerId()==personalPlayer.getId())
            cardYouPlayed=message.getPlayedCardId()%10;

    }
    public void singleBoardUpdate(SingleBoardMessage message){
        for (SimplifiedPlayer p:players){
            if (p.getId() == message.getBoardPlayerId()) {
               p.setBoard(message.getBoard());
               p.setCoin(message.getCoin());
            }
        }
       //
        if(isYourTurn() && state==GameState.actionMoveStudent) {
            //clientScreen.showView();
            studentMoved++;
            if(studentMoved<3)
                clientScreen.askToMoveStudent();
        }

    }
    public void personalizePlayer(PlayerSetUpMessage message){
        turnOf=message.getNewId();
        if(message.getTurnOf().equals(this.name)) {
            this.id=message.getNewId();
            clientScreen.showOptionsForPersonalization(message);
        }
    }
    public void changeTurn(ChangeTurnMessage message){
        this.turnOf=message.getPlayer();
        if(isYourTurn() && state==GameState.planPlayCard){
            clientScreen.showHand();
        }
        if(isYourTurn() && state== GameState.actionMoveStudent) {
            studentMoved=0;
            clientScreen.askToMoveStudent();
        }
    }
    public void changePhase(ChangePhaseMessage message){
        this.state=message.getState();
        switch (state){
            case setupPlayers -> {throw new RuntimeException("something went really wrong");}
            case planPlayCard -> {
                if(isYourTurn())
                    clientScreen.showHand();
            }
            case actionMoveStudent -> {
                activeCharacter=0;
                playedCardThisTurn = new ArrayList<>();
                //clientScreen.showView();
            }
            case actionMoveMother -> {
                //clientScreen.showView();
                if(isYourTurn())
                    clientScreen.askToMoveMother();
            }
            case actionChooseCloud ->{
                //clientScreen.showView();
                if(isYourTurn())
                    clientScreen.showClouds();
            }
        }
    }


    public void update(ServerMessage message) {
        message.doAction(this);
    }

    public void useAssistantCard(int cardId){
        if(personalPlayer.getAssistantCards()[cardId]) {
            notify(new PlayAssistantMessage(id, cardId+10*id));
            personalPlayer.removeCard(cardId);
        }
        else
            throw new CardNotFoundException("card :"+(cardId+1)+" not available");
    }
    public void moveMother(int steps){
        notify(new MoveMotherMessage(steps,personalPlayer.getId()));
    }
    public void moveStudentToHall(int colorOfStudent) throws NoTokenFoundException{
            int studID=personalPlayer.getBoard().getStudentFromColorInEntrance(colorOfStudent);
            notify(new StudentToHallMessage(personalPlayer.getId(), studID));
    }
    public void moveStudentToIsland(int colorOfStudent, int islandId) throws IllegalMoveException,NoTokenFoundException{
        int studID=personalPlayer.getBoard().getStudentFromColorInEntrance(colorOfStudent);
        if( islands.stream().map(SimplifiedIsland::getIslandId).toList().contains(islandId))
            notify(new StudentToIslandMessage(personalPlayer.getId(), studID, islandId));
        else throw new IllegalMoveException("Island not available");
    }
    public void chooseCloud(int cloudId){
        if(cloudId<clouds.size() && clouds.get(cloudId).length==3)
            notify(new ChooseCloudMessage(cloudId, personalPlayer.getId()));
        else
            throw new RuntimeException("invalid Cloud");
    }
    public void playCharacter(int cardId, ParameterObject parameters){
        notify(new CharacterCardMessage(this.id, parameters,cardId));
    }
    public void choosePlayerCustom(int towerColor,int mage){
       notify(new PrePlayerMessage(0,towerColor,mage,name));
    }

    public List<Integer> getCharacters() {
        return characters;
    }

    public Map<Integer, List<Integer>> getStudentsOnCard() {
        return studentsOnCard;
    }

    public Map<Integer, Integer> getCostOfCard() {
        return costOfCard;
    }

    public GameState getState() {
        return state;
    }
    public boolean isYourTurn(){
        if(state!=GameState.setupPlayers)
            return this.turnOf==personalPlayer.getId();
        else return this.turnOf==this.id;//this check on setup is only server side
    }

    public int getCardYouPlayed() {
        return cardYouPlayed;
    }

    public int getActiveCharacter() {
        return activeCharacter;
    }

    public int getStudentMoved() {
        return studentMoved;
    }

    public UserInterface getClientScreen() {
        return clientScreen;
    }
}
