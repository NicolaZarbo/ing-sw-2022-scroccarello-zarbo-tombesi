package it.polimi.ingsw.model;
import it.polimi.ingsw.enumerations.Mage;
import it.polimi.ingsw.messages.servermessages.*;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.FactoryCharacter;
import it.polimi.ingsw.model.tokens.Professor;
import it.polimi.ingsw.model.tokens.Student;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.enumerations.TowerColor;

import java.util.*;

/**The setup phase of the game. It is responsible for initializing all the components of the game.*/
public class Setup {
    private final Game game;
    private final ArrayList<TowerColor> availableColor;
    private final ArrayList<Mage> availableMages ;
    private final ArrayList<LobbyPlayer> prePlayers;
    private List<String> preGameOrder;
    private String preGameTurnOf;
    private int idCreator;

    /**It builds the setup phase of the game.
     * @param game the game*/
    public Setup(Game game){
        this.game=game;
        availableColor= new ArrayList<>(List.of(TowerColor.values()));
        availableMages = new ArrayList<>(List.of(Mage.values()));
        if (game.getNPlayers()!=3)
            availableColor.remove(TowerColor.grey);
        prePlayers= new ArrayList<>(game.getNPlayers());
        idCreator=0;
    }

    /** It sets the initial order for selecting pregame options
     * @param names list of player's nicknames*/
    public void setPreOrder(List<String> names){
        this.preGameOrder= names;
        this.preGameTurnOf=preGameOrder.get(0);
    }

    /**@return the nickname of the player currently choosing options.*/
    public String getPreGameTurnOf() {
        return preGameTurnOf;
    }

    /**It starts the personalization phase for the players in the lobby.*/
    public void startPersonalisation(){
        game.notify(new PlayerSetUpMessage(game,idCreator));
        idCreator++;
    }

    /**It states if the player has access to customization or not.
     * @param nickname the nickname of the player to check
     * @return •true: is the playe's turn to customize
     * <p>•false: it's not the player's turn to customize</p>*/
    public boolean isPreOrderTurnOf(String nickname){
        return nickname.equalsIgnoreCase(preGameTurnOf);
    }

    /** It adds a lobby player to the list of players joining the game.
     * <p>Notice that if the size of <b>prePlayers</b> list has reached its maximum, the game is started.</p>
     * @param prePlayer the customized player joining the game*/
    public void addPrePlayer(LobbyPlayer prePlayer){
        prePlayers.add(prePlayer);
        availableMages.remove(prePlayer.getMage());
        availableColor.remove(prePlayer.getTowerColor());
        preGameOrder.remove(prePlayer.getNickname());
        if(prePlayers.size() < game.getNPlayers()) {
            preGameTurnOf = preGameOrder.remove(0);
            game.notify(new PlayerSetUpMessage(game, idCreator));
            idCreator++;
        }
        if(prePlayers.size()== game.getNPlayers()){
            game.setPlayers(Setup.createPlayer(game.isEasy(), prePlayers,game.getBag()));
            game.groupMultiMessage(new WholeGameMessage(game));
            game.groupMultiMessage(new ChangeTurnMessage(game));
            game.moveToNextPhase();
        }
    }

    /**@return the list of available team's colors*/
    public ArrayList<TowerColor> getAvailableColor() {
        return availableColor;
    }

    /**@return the list of magicians which have still not been chosen*/
    public ArrayList<Mage> getAvailableMages() {
        return availableMages;
    }

    /**It creates a set of islands with two students on each of them, with the exception for the island with mother nature and the one opposite to it.
     * @param nIsole number of islands to create
     * @param bag the bag from which extract the students
     * @return list of created islands*/
    public static List<Island> createIslands(int nIsole, Bag bag){
        List<Island> islands = new ArrayList<>();
        Island isl;
        ArrayList<Student> students = bag.setupStudentsOnIslands(nIsole);
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

    /**It creates the character cards of the game (for expert mode only).
     * @param bag the bag of the game containing all the student tokens
     * @param cardNumber number of character cards
     * @return the list of all the character cards*/
    public ArrayList<CharacterCard> createCharacterCards(Bag bag, int cardNumber){
        ArrayList<CharacterCard> cards = new ArrayList<>(cardNumber);
        int[] randomIds = randomIndexForCharacter();
        for (int i=0; i<3; i++){
            cards.add(FactoryCharacter.createCharacter(randomIds[i], bag));
        }
        return cards;
    }
    /** returns an array containing random values out of the available card number*/
    private int[] randomIndexForCharacter(){
        ArrayList<Integer> ids=new ArrayList<>(Arrays.asList(1,2,6,8,7,9,10,11));
        Collections.shuffle(ids, new Random());
        int[] out= new int[3];
        for (int i = 0; i < 3; i++) {
            out[i]=ids.get(i);
        }
        return out;
    }

    /**It creates the professor tokens of the game, without assigning them to a specific board.
     * @param nColors number of token colors
     * @return set of all the professors of the game*/
    public  Professor[] createProfessor(int nColors){
        Professor[] profs = new Professor[nColors];
        for (int i=0; i<nColors;i++){
            profs[i]= new Professor(i, TokenColor.getColor(i));
        }
        return profs;
    }

    /**It creates the list of players of the game based on <b>prePlayers</b> list.
     * @param easy difficulty of the game
     * @param bag the bag containing the student tokens
     * @param prePlayers the information about customization of players
     *@return list of players of the game*/
    public static Player[] createPlayer(boolean easy , ArrayList<LobbyPlayer> prePlayers, Bag bag){
        int nPlayer=prePlayers.size();
        Player[] players =new Player[nPlayer];

        for (int id=0; id<nPlayer;id++){
            Hand man= Setup.createHand(id, easy, 10);
            TowerColor towerColor;
            if(prePlayers.size()==4 && id>1)
                towerColor= prePlayers.get(id-2).getTowerColor();
            else towerColor= prePlayers.get(id).getTowerColor();
            Board plan = Setup.createBoard(id, nPlayer, towerColor, bag);
            players[id]= new Player(prePlayers.get(id),id, man, plan);
        }
        return players;
    }

    /**It creates a set of empty clouds for the game.
     * @param nPlayer number of players of the game
     * @return the set of the clouds of the game*/
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

    /**It creates the hand of the player.
     * @param playerId id of the player
     * @param easy difficulty mode
     * @param nCards number of available assistant cards
     * @return the hand of the player*/
    private static Hand createHand(int playerId, boolean easy, int nCards){
        ArrayList<AssistantCard> ass ;
        int stIdCards= playerId * nCards  ;
        ass = new ArrayList<>();
        for(int j=0;j<nCards; j++){
            ass.add(new AssistantCard(stIdCards+j,j+1,(j/2)+1, Mage.getMage(playerId)));
        }
        Hand hand =new Hand(ass);
        if (!easy) {
            hand.addCoin();
        }
         return hand;
    }

    /**It creates the board of the player.
     * @param playerId id of the player
     * @param bag the bag from which extract students to put on entrance
     * @param towerColor the color of the player's team
     * @param nPlayer the number of players of the game
     * @return the board of the player*/
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
        for (int i = 0; i < dimEntry; i++) {
            pla.putStudentOnEntrance(bag.getToken());
        }
        return pla;
    }


}
