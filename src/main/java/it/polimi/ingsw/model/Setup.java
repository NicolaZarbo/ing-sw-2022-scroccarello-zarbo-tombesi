package it.polimi.ingsw.model;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.FactoryCharacter;
import it.polimi.ingsw.model.token.Professor;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.model.token.TokenColor;
import it.polimi.ingsw.model.token.TowerColor;
import it.polimi.ingsw.messages.server.PlayerSetUpMessage;
import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.messages.server.WholeGameMessage;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class Setup {
    private Game game;
    private ArrayList<TowerColor> availableColor;
    private ArrayList<Mage> availableMages ;
    private ArrayList<LobbyPlayer> prePlayers;
    private ArrayList<String> preGameOrder;
    private String preGameTurnOf;


    public Setup(Game game){
        this.game=game;
        availableColor= new ArrayList<>(List.of(TowerColor.values()));
        availableMages = new ArrayList<>(List.of(Mage.values()));
        if (game.getNPlayers()!=3)
            availableColor.remove(TowerColor.grey);
        prePlayers= new ArrayList<>(game.getNPlayers());
    }
    /** set the initial order for choosing pregame options*/
    public void setPreOrder(List<String> names){
        this.preGameOrder= new ArrayList<>(names);
        this.preGameTurnOf=preGameOrder.get(0);
    }
    public void startPersonalisation(){
        game.notify(new PlayerSetUpMessage(game));
    }
    public boolean isPreOrderTurnOf(String nickname){
        return nickname.equals(preGameTurnOf);
    }
    /** get prePlayer from client, if it is the last one then creates the final players in game*/
    public void addPrePlayer(LobbyPlayer prePlayer){
        if(prePlayers.size()< game.getNPlayers()) {
            preGameOrder.remove(prePlayer.getNickname());
            prePlayers.add(prePlayer);
            availableMages.remove(prePlayer.getMage());
            availableColor.remove(prePlayer.getTowerColor());
            preGameTurnOf=preGameOrder.get(0);
            game.notify(new PlayerSetUpMessage(game));
        }
        else throw new RuntimeException("no space for more players");
        if(prePlayers.size()== game.getNPlayers()){
            game.setPlayers(Setup.createPlayer(game.isEasy(), prePlayers,game.getBag()));
            game.notify(new WholeGameMessage(game));
        }
    }

    public ArrayList<TowerColor> getAvailableColor() {
        return availableColor;
    }

    public ArrayList<Mage> getAvailableMages() {
        return availableMages;
    }
    /** creates a set of n Islands with 2 students on top
     * (except for the island with MN and the opposite one)*/
    public static List<Island> createIslands(int nIsole, Bag bag){
        List<Island> islands = new ArrayList<>();
        Island isl;
        ArrayList<Student> students = bag.setupStudents(nIsole);
        int k=0;
        for(int i=0;i<nIsole;i++){
            isl =  new Island(i);
            if(i!=0 && i!=(nIsole)/2){
                isl.addStudent(students.get(k));
                k++;
            }
            islands.add(isl);
        }
        return  islands;
    }
    /** creates the list of Character Cards using a constructor*/
    public static ArrayList<CharacterCard> createCharacterCards(Bag bag, int cardNumber){
            ArrayList<CharacterCard> cards = new ArrayList<>(cardNumber);
            CharacterCard cardChar;
            for (int i=1; i<=12; i++){
                    cardChar= FactoryCharacter.createCharacter(i, bag);
                if(cardChar!=null)
                    cards.add( cardChar ) ;
            }
            return cards;
    }
    /** creates the professor in game with no board assigned*/
    public static Professor[] createProfessor(int nColors){
        Professor[] profs = new Professor[nColors];
        for (int i=0; i<nColors;i++){
            profs[i]= new Professor(i, TokenColor.getColor(i));
        }
        return profs;
    }
    /** creates an array of player from a list of PrePlayer(a bean that
     * contains nickname, mage and tower-color)*/
    public static Player[] createPlayer(boolean easy , ArrayList<LobbyPlayer> prePlayers, Bag bag){
        int nPlayer=prePlayers.size();
        Player[] players =new Player[nPlayer];

        for (int id=0; id<nPlayer;id++){
            Hand man= Setup.createHand(id, easy, 10);
            TowerColor towerColor = Setup.playerColor(id, nPlayer);
            Board plan = Setup.createBoard(id, nPlayer, towerColor, bag);
            players[id]= new Player(prePlayers.get(id),id, man, plan);
        }
        return players;
    }
    /** creates an array of clouds without player on them*/
    public static Cloud[] createClouds(int nPlayer){
        Cloud[] clouds = new Cloud[nPlayer];
        Student[] studs;
        int dimIsl;
        if (nPlayer==3) {
            dimIsl = 4;
        }else {
            dimIsl=3;
        }
        studs = new Student[dimIsl];
        for(int k=0; k<dimIsl;k++){
            studs[k]=null;
        }
        for(int id = 0; id<nPlayer; id++){

            clouds[id]= new Cloud(studs,id);
        }
        return  clouds;
    }
    private static TowerColor playerColor(int playerId, int nPlayer){
        TowerColor color;
        if (nPlayer== 4 && playerId>1){
         color = TowerColor.getColor(playerId-2);
        }
        else
            color= TowerColor.getColor(playerId);
        return color;
    }
    private static Hand createHand(int playerId, boolean easy, int nCards){
        ArrayList<AssistantCard> ass ;
        int stIdCards= playerId * nCards  ;
        ass = new ArrayList<AssistantCard>();
        for(int j=0;j<nCards; j++){
            ass.add(new AssistantCard(stIdCards+j,j+1,(j/2)+1, Mage.getMage(playerId)));
        }
        Hand hand =new Hand(ass);
        if (!easy) {
            hand.addCoin();
        }
         return hand;
    }
    private static Board createBoard(int playerId, int nPlayer, TowerColor towerColor, Bag bag){
        Board pla ;
        int nTower, dimEntry;
        if (nPlayer == 3){
            nTower=6;
            dimEntry = 9;
        }
        else{
            nTower =8;
            dimEntry = 7;
        }
        if (nPlayer== 4 && playerId>1){
            nTower=0;
        }
        pla=new Board(nTower,dimEntry,towerColor,playerId);
        for (int i = 0; i < 7; i++) {
            pla.putStudentOnEntrance(bag.getToken());
        }
        return pla;
    }


}
