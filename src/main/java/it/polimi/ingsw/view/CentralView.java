package it.polimi.ingsw.view;

import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.messages.server.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.model.token.Token;
import it.polimi.ingsw.model.token.TowerColor;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class  CentralView extends Observable<ClientMessage> implements Observer<ServerMessage> {
    private List<Island> islands;
    private List<Cloud> clouds;
    private List<Player> players; //va trasformato in simplified player come tutto il resto?
    private MotherNature mother;
    private String name;
    private Player personalPlayer;
    private GameState state;
    private int turnOf;
    private static int fakeId=99;
    private ArrayList<Integer> playedCardThisTurn;

    public CentralView(String name){
        this.name=name;
    }
    public void errorFromServer(ErrorMessageForClient message){}
    public void setView(WholeGameMessage message) {
        islands=message.getIslands();
        clouds= Arrays.stream(message.getClouds()).toList();
        players= Arrays.asList(message.getModelPlayers());
        mother = message.getMotherNature();
        playedCardThisTurn= new ArrayList<>(players.size());
        for (Player pl: players) {
            if(pl.getNickname().equals( name))
                personalPlayer=pl;
        }
    }
    public void cloudUpdate(CloudMessage message){
        this.clouds= Arrays.stream(message.getClouds()).toList();
    }
    public void motherPositionUpdate(MotherPositionMessage message){
        this.mother.changePosition(message.getMotherPosition());
    }
    public void islandsUpdate(IslandsMessage message){
        this.islands=message.getIslandList();
    }
    public void playedAssistentUpdate(PlayedAssistentMessage message){

    }
    public void singleBoardUpdate(SingleBoardMessage message){
        for (Player p:players) {
            if (p.getId() == message.getBoardPlayerId()) {
                p.getBoard().setDiningRoom(message.getBoard().getDiningRoom());
                p.getBoard().setEntrance(message.getBoard().getEntrance());
            }
        }
    }

    @Override
    public void update(ServerMessage message) {
        //command Pattern???
        //message.doAction(this);
    }

    public void useCard(int cardId){
        if(personalPlayer.getHand().getAssistant().stream().map(AssistantCard::getId).toList().contains(cardId))
            notify(new PlayAssistantMessage(personalPlayer.getId(),cardId));
        throw new CardNotFoundException();
    }
    public void moveMother(int steps){
        notify(new MoveMotherMessage(personalPlayer.getId(),steps));
    }
    public void moveStudentToHall(int studentId){
        if(personalPlayer.getBoard().getEntrance().stream().map(Token::getId).toList().contains(studentId))
            notify(new StudentToHallMessage(personalPlayer.getId(), studentId));
        else throw new NoTokenFoundException();
    }
    public void moveStudentToIsland(int studentId, int islandId){
        if(personalPlayer.getBoard().getEntrance().stream().map(Token::getId).toList().contains(studentId) && islands.stream().map(Island::getID).toList().contains(islandId))
            notify(new StudentToIslandMessage(personalPlayer.getId(), studentId, islandId));
        else throw new NoTokenFoundException();
    }
    public void chooseCloud(int cloudId){
        for (Cloud c: clouds) {
            if(c.getId()==cloudId && !c.isEmpty())
                notify(new ChooseCloudMessage(cloudId, personalPlayer.getId()));
        }
        throw new RuntimeException("invalid Cloud");
    }
    public void playCharacter(int cardId, ParameterObject parameters){
        notify(new CharacterCardMessage(personalPlayer.getId(), parameters,cardId));
    }
    public void choosePlayerCustom(TowerColor towerColor, Mage mage){
        notify(new PrePlayerMessage(fakeId,new LobbyPlayer(towerColor,mage,name)));
    }
}
