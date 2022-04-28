package it.polimi.ingsw.view;

import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.messages.server.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.model.token.TowerColor;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.List;

//per le torri si può scegliere anche di non usare l'id ma basarsi sul mero colore
public class  CentralView extends Observable<ClientMessage> implements Observer<ServerMessage> {
    private List<SimplifiedIsland> islands;
    private List<Integer[]> clouds;
    private List<SimplifiedPlayer> players; //va trasformato in simplified player come tutto il resto?
    private int mother;
    private String name;
    private SimplifiedPlayer personalPlayer;
    private GameState state;
    private int turnOf;
    private static int fakeId=99;
    private ArrayList<Integer> playedCardThisTurn;

    public List<SimplifiedIsland> getIslands() {
        return islands;
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

    public CentralView(String name){
        this.name=name;
    }
    public void errorFromServer(ErrorMessageForClient message){}
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
    public void playedAssistentUpdate(PlayedAssistentMessage message){

    }
    public void singleBoardUpdate(SingleBoardMessage message){
        for (SimplifiedPlayer p:players) {
            if (p.getId() == message.getBoardPlayerId()) {
               p.setBoard(message.getBoard());
            }
        }
    }

    @Override
    public void update(ServerMessage message) {
        message.doAction(this);
    }

    public void useCard(int cardId){
        if(personalPlayer.getDiscardedCards()[cardId-(10*personalPlayer.getId())])
            notify(new PlayAssistantMessage(personalPlayer.getId(),cardId));
        throw new CardNotFoundException();
    }
    public void moveMother(int steps){
        notify(new MoveMotherMessage(personalPlayer.getId(),steps));
    }
    public void moveStudentToHall(int studentId){
        if(personalPlayer.getBoard().getEntrance().contains(studentId))
            notify(new StudentToHallMessage(personalPlayer.getId(), studentId));
        else throw new NoTokenFoundException();
    }
    public void moveStudentToIsland(int studentId, int islandId){
        if(personalPlayer.getBoard().getEntrance().contains(studentId) && islands.stream().map(SimplifiedIsland::getIslandId).toList().contains(islandId))
            notify(new StudentToIslandMessage(personalPlayer.getId(), studentId, islandId));
        else throw new NoTokenFoundException();
    }
    public void chooseCloud(int cloudId){
        if(cloudId<clouds.size() && clouds.get(cloudId).length==3)
            notify(new ChooseCloudMessage(cloudId, personalPlayer.getId()));
        throw new RuntimeException("invalid Cloud");
    }
    public void playCharacter(int cardId, ParameterObject parameters){
        notify(new CharacterCardMessage(personalPlayer.getId(), parameters,cardId));
    }
    public void choosePlayerCustom(TowerColor towerColor, Mage mage){
        notify(new PrePlayerMessage(fakeId,new LobbyPlayer(towerColor,mage,name)));
    }

}
