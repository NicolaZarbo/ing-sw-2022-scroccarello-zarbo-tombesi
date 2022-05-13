package it.polimi.ingsw.view;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.messages.server.*;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.objects.SimplifiedIsland;
import it.polimi.ingsw.view.objects.SimplifiedPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//per le torri si può scegliere anche di non usare l'id ma basarsi sul mero colore
public class  CentralView extends Observable<ClientMessage> implements Observer<ServerMessage> {
    private List<SimplifiedIsland> islands;
    private List<Integer[]> clouds;
    private List<SimplifiedPlayer> players;
    private int mother;
    private List<Integer> characters;
    private Map<Integer,List<Integer>> studentsOnCard;
    private Map<Integer,Integer> costOfCard;
    private String name;
    private SimplifiedPlayer personalPlayer;
    private GameState state;
    private int turnOf;
    private int id;
    private ArrayList<Integer> playedCardThisTurn;
    private int cardYouPlayed;
    private final UserInterface clientScreen;

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
    }
    public void errorFromServer(ErrorMessageForClient message){
        if(message.getTargetPlayerId()==personalPlayer.getId()|| message.getTargetPlayerId()==-1)
            clientScreen.showError(message.getErrorInfo());
    }
    public void setView(WholeGameMessage message) {
        islands=message.getIslands();
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

        clientScreen.showView();
        if(isYourTurn())
            clientScreen.showHand();
    }
    public void setName(String name){
        this.name=name;
    }
    public void cloudUpdate(CloudMessage message){
        this.clouds= message.getClouds();
        clientScreen.showView();

    }
    public void motherPositionUpdate(MotherPositionMessage message){
        this.mother=message.getMotherPosition();
        clientScreen.showView();
        if(state==GameState.actionChooseCloud && isYourTurn()){
            clientScreen.showClouds();
        }
    }
    public void islandsUpdate(IslandsMessage message){
        this.islands=message.getIslandList();
    }
    /** adds to card to the list of played card in this turn, this way the players know which card he can or cannot use*/
    public void playedAssistantUpdate(PlayedAssistantMessage message){
        playedCardThisTurn.add(message.getPlayedCardId()%10);
        if(message.getPlayerId()==personalPlayer.getId())
            cardYouPlayed=message.getPlayedCardId()%10;
        if(isYourTurn() && state==GameState.planPlayCard)
            clientScreen.showHand();
        if(isYourTurn() && state==GameState.actionMoveStudent) {
            clientScreen.showView();
            clientScreen.askToMoveStudent();
        }
    }
    public void singleBoardUpdate(SingleBoardMessage message){
        for (SimplifiedPlayer p:players) {
            if (p.getId() == message.getBoardPlayerId()) {
               p.setBoard(message.getBoard());
            }
        }
        clientScreen.showView();
        if(isYourTurn() && state==GameState.actionMoveStudent) {
            clientScreen.askToMoveStudent();
        }
        if(isYourTurn() && GameState.actionMoveMother==state)
            clientScreen.askToMoveMother();
    }
    public void personalizePlayer(PlayerSetUpMessage message){
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
    }


    public void update(ServerMessage message) {
        this.state=message.getState();
        message.doAction(this);
    }

    public void useAssistantCard(int cardId){
        if(personalPlayer.getAssistantCards()[cardId-1])
            notify(new PlayAssistantMessage(personalPlayer.getId(),cardId));
        else
            throw new CardNotFoundException();
    }
    public void moveMother(int steps){
        notify(new MoveMotherMessage(personalPlayer.getId(),steps));
    }
    public void moveStudentToHall(int colorOfStudent) throws NoTokenFoundException{
            int studID=personalPlayer.getBoard().getStudentFromColor(colorOfStudent);
            notify(new StudentToHallMessage(personalPlayer.getId(), studID));
    }
    public void moveStudentToIsland(int colorOfStudent, int islandId) throws IllegalMoveException,NoTokenFoundException{
        int studID=personalPlayer.getBoard().getStudentFromColor(colorOfStudent);
        if( islands.stream().map(SimplifiedIsland::getIslandId).toList().contains(islandId))
            notify(new StudentToIslandMessage(personalPlayer.getId(), studID, islandId));
        else throw new IllegalMoveException("Island not available");
    }
    public void chooseCloud(int cloudId){
        if(cloudId<clouds.size() && clouds.get(cloudId).length==3)
            notify(new ChooseCloudMessage(cloudId, personalPlayer.getId()));
        throw new RuntimeException("invalid Cloud");
    }
    public void playCharacter(int cardId, ParameterObject parameters){
        notify(new CharacterCardMessage(personalPlayer.getId(), parameters,cardId));
    }
    public void choosePlayerCustom(int towerColor,int mage){
       notify(new PrePlayerMessage(id,towerColor,mage,name));
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
        else return true;//this check on setup is only server side
    }

    public int getCardYouPlayed() {
        return cardYouPlayed;
    }
}
