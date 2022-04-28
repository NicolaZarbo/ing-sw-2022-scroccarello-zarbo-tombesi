package it.polimi.ingsw.view;

import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.client.*;
import it.polimi.ingsw.messages.server.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.model.token.Token;
import it.polimi.ingsw.model.token.TokenColor;
import it.polimi.ingsw.model.token.TowerColor;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//per le torri si può scegliere anche di non usare l'id ma basarsi sul mero colore
public class  CentralView extends Observable<ClientMessage> implements Observer<ServerMessage> {
    private List<int[]> islandsStudent;
    private List<int[]> islandsTowers;
    private List<Integer> numberofIslands; //indica per ogni isola da quante isole è composta
    private List<Cloud> clouds;
    private List<SimplifiedPlayer> players; //va trasformato in simplified player come tutto il resto?
    private MotherNature mother;
    private String name;
    private SimplifiedPlayer personalPlayer;
    private GameState state;
    private int turnOf;
    private static int fakeId=99;
    private ArrayList<Integer> playedCardThisTurn;

    public CentralView(String name){
        this.name=name;
    }
    public void errorFromServer(ErrorMessageForClient message){}
    public void setView(WholeGameMessage message) {
        List<Island> islands= message.getIslands();
        for(int i=0;i<islands.size();i++){
            islandsStudent.add(translateIslandStudents(islands.get(i)));
            islandsTowers.add(translateIslandTower(islands.get(i)));
            numberofIslands.add(translateIslandNumbers(islands.get(i)));
        }

        clouds= Arrays.stream(message.getClouds()).toList();
        List<Player> tempPlayers= Arrays.asList(message.getModelPlayers());
        for(int i=0;i<tempPlayers.size();i++)
            players.add(translate(tempPlayers.get(i)));
        mother = message.getMotherNature();
        playedCardThisTurn= new ArrayList<>(players.size());
        for (SimplifiedPlayer pl: players) {
            if(pl.getUsername().equals( name))
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

    public SimplifiedPlayer translatePlayer(Player player){
        int tC=player.getColorT().ordinal();
        int m=player.getMage().ordinal();
        int c=player.getHand().getCoin();
        boolean[] ac=new boolean[player.getHand().getAssistant().size()];
        boolean[] dc=new boolean[player.getHand().getAssistant().size()];
        for(int i=0;i<player.getHand().getAssistant().size();i++)
            ac[player.getHand().getAssistant().get(i).getId()]=true;

        for(int i=0;i<ac.length;i++)
            dc[i]= !ac[i];
        int[][] dr=new int[player.getBoard().getDiningRoom().length][player.getBoard().getDiningRoom()[0].length];
        for(int i=0;i<dr.length;i++)
            for(int j=0;j<dr[i].length;j++)
                dr[i][j]=player.getBoard().getDiningRoom()[i][j].getId();

        int[] pt=new int[player.getBoard().getTable().length];
        for(int i=0;i<pt.length;i++) {//se non c'è professore di un colore quella entry avrà come valore -1
            if (player.getBoard().hasProfessor(TokenColor.getColor(i)))
                pt[i] = player.getBoard().getProfessor(TokenColor.getColor(i)).getId();
            else
                pt[i] = -1;
        }
        int tl=player.getBoard().towersLeft();
        boolean[][] cDN=new boolean[player.getBoard().getCoinDN().length][player.getBoard().getCoinDN()[0].length];

        for(int i=0;i<cDN.length;i++)
            for(int j=0;j<cDN[i].length;j++)
                cDN[i][j]=player.getBoard().getCoinDN()[i][j];


        int es=player.getBoard().getEntranceSize();

        String nick=player.getNickname();

        SimplifiedPlayer temp= new SimplifiedPlayer(nick,tC,c,tl,es);
        temp.setMage(m);
        temp.setAssistantCards(ac);
        temp.setDiscardedCards(dc);
        temp.setDiningRoom(dr);
        temp.setProfessorTable(pt);
        temp.setCoinDN(cDN);

        return temp;


    }
    public int[] translateIslandStudents(Island isl){
        int[] temp=new int[isl.getStudents().size()];
        for(int i=0;i<temp.length;i++)
            temp[i]=isl.getStudents().get(i).getId();

        return temp;
    }
    public int[] translateIslandTower(Island isl){
        int[] temp=new int[isl.getTowers().size()];
        for(int i=0;i<temp.length;i++)
            temp[i]=isl.getTowers().get(i).getColor().ordinal();

        return temp;
    }
    public int translateIslandNumbers(Island isl){
       return isl.getIslandSize();
    }
}
