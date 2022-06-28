package it.polimi.ingsw.view;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.messages.clientmessages.*;
import it.polimi.ingsw.messages.servermessages.*;
import it.polimi.ingsw.util.ParameterObject;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**The simplified version of the game. It contains a light version of the model to manage the view of the player.*/
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
    private HashMap<Integer,Integer> playedCardThisTurnByPlayerId;
    private int cardYouPlayed;
    private final UserInterface clientScreen;
    private List<Integer> availableColor;
    private List<Integer> availableMages;
    private int studentMoved;
    private boolean teamPlay;
    private int playersWaitingInLobby;
    private int winner;
    private boolean firstTurn;
    /**1 bag is empty
     * 2 someone used every tower in their board
     * 3 someone used his last card
     * 4 there are only 3 cluster of islands left*/
    private int gameOverReason;


    /**@return the list of simplified islands of the game*/
    public List<SimplifiedIsland> getIslands() {
        return islands;
    }

    /**@return the list of simplified players of the game*/
    public List<SimplifiedPlayer> getPlayers() {
        return players;
    }

    /**@return the list of simplified clouds of the game*/
    public List<Integer[]> getClouds() {
        return clouds;
    }

    /**@return the current position of mother nature in the game*/
    public int getMother() {
        return mother;
    }

    /**@return the nickname of the player*/
    public String getName() {
        return name;
    }

    /**@return •true: the game is in easy mode
     * <p>•false: the game is in expert mode</p>*/
    public boolean isEasy() {
        return easy;
    }

    /**@return the relative simplified player*/
    public SimplifiedPlayer getPersonalPlayer() {
        return personalPlayer;
    }

    /**@param id the id of the player which has played the returned card
     * @return the id of assistant card played by the specified player*/
    public Integer getPlayedCardThisTurnByPlayerId(int id) {
        return playedCardThisTurnByPlayerId.get(id);
    }

    /**It builds the central view starting from the specific user interface.
     * <p>It can be:</p>
     * <p>•CLI client</p>
     * <p>•GUI client</p>
     * <p>Notice that the typology of user interface is decided by the user according to the launch file he runs.</p>*/
    public CentralView(UserInterface userInterface){
        this.clientScreen=userInterface;
        this.state=GameState.setupPlayers;
        this.id=-1;
        winner=-1;
        cardYouPlayed=-1;
        playedCardThisTurnByPlayerId = new HashMap<>();
        firstTurn=true;
    }

    /**It shows the error received from the server.
     * @param message the error message sent by the server*/
    public void errorFromServer(ErrorMessageForClient message){
        if(message.getTargetPlayerName().equalsIgnoreCase(personalPlayer.getUsername())|| message.getTargetPlayerName().equalsIgnoreCase("all")) {
            clientScreen.showError(message.getErrorInfo());
            seeAction();
        }
    }

    /**It builds the view starting from a whole game message.
     * @see WholeGameMessage
     * @param message the message containing all the information about the game*/
    public void setView(WholeGameMessage message) {
        islands=message.getIslands();
        easy= message.isEasy();
        clouds=message.getClouds();
        players=message.getModelPlayers();
        players=message.getModelPlayers();
        mother = message.getMotherNature();
        for (SimplifiedPlayer pl: players) {
            if(pl.getUsername().equals( name))
                personalPlayer=pl;
            if (isTeamPlay())
                pl.setTeam((pl.getId())%2+1);
        }
        characters=message.getCharacters();
        studentsOnCard=message.getCardStudents();
        costOfCard=message.getCardCosts();
        activeCharacter= message.getActiveCharacter();
        clientScreen.showView();
        if(state==GameState.actionMoveStudent||state==GameState.actionMoveMother)
            seeAction();
    }

    /**It is used to display to the player the right input scene.*/
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

    /**It sets the player's nickname.
     * @param name the player's nickname*/
    public void setName(String name){
        this.name=name;
    }

    /**It updates the simplified clouds after receiving a message.
     * @param message the message containing refilling tokens for the simplified clouds*/
    public void cloudUpdate(CloudMessage message){
        this.clouds= message.getClouds();
    }
    public void motherPositionUpdate(MotherPositionMessage message){
        this.mother=message.getMotherPosition();
    }
    public void islandsUpdate(IslandsMessage message){

    this.islands=message.getIslandList();
    }

    /**It adds to card to the list of played card in this turn; in this way the players know which card they can use or not.
     * @param message the message containing played assistant*/
    public void playedAssistantUpdate(PlayedAssistantMessage message){
        playedCardThisTurnByPlayerId.put(message.getPlayerId(),message.getPlayedCardId()%10);
        if(message.getPlayerId()==personalPlayer.getId())
            cardYouPlayed=message.getPlayedCardId()%10;

    }
    /*
@Deprecated //fixme use all board update
    /**It updates the player's board after getting a relative message.
     * @param message the message containing the current board of the player
    public void singleBoardUpdate(SingleBoardMessage message){
        int movablePerTurn;
        if(players.size()==3)
            movablePerTurn=4;
        else movablePerTurn=3;
        for (SimplifiedPlayer p:players){
            if (p.getId() == message.getBoardPlayerId()) {
               p.setBoard(message.getBoard());
               p.setCoin(message.getCoin());
            }
        }
        if(isYourTurn() && state==GameState.actionMoveStudent) {
            studentMoved++;
            if(studentMoved<movablePerTurn && message.getBoardPlayerId()==turnOf)
                clientScreen.askToMoveStudent();
        }

    }

     */

    public void everyBoardUpdate(AllBoardsMessage message){
        int movablePerTurn;
        if(players.size()==3)
            movablePerTurn=4;
        else movablePerTurn=3;
        for (SimplifiedPlayer p:players){
            p.setBoard(message.getBoards().get(p.getId()));
            p.setCoin(message.getBoardCoin().get(p.getId()));
        }
        if(isYourTurn() && state==GameState.actionMoveStudent) {
            studentMoved++;
            if(studentMoved<movablePerTurn )
                clientScreen.askToMoveStudent();
        }
    }

    /**It personalizes the view of the player after getting a proper message.
     * @param message the message containing the information about customization*/
    public void personalizePlayer(PlayerSetUpMessage message){
        turnOf=message.getNewId();
        this.teamPlay=message.isTeamPlay();
        if(teamPlay)
            playersWaitingInLobby=message.getAvailableMages().size()-1;
        else playersWaitingInLobby=message.getAvailableColor().size()-1;
        if(message.getTurnOf().equals(this.name)) {
            this.id=message.getNewId();
            this.availableColor=message.getAvailableColor();
            this.availableMages=message.getAvailableMages();
            clientScreen.showOptionsForPersonalization(message);
        }
    }

    /**It ends the players turn and passes the game to the following player based on the planning order.
     * @param message the message which triggers turn swapping*/
    public void changeTurn(ChangeTurnMessage message){
        this.turnOf=message.getPlayer();
        if(isYourTurn() && state==GameState.planPlayCard && winner==-1){
            clientScreen.showHand();
        }
        if(isYourTurn() && state== GameState.actionMoveStudent && winner==-1) {
            studentMoved=0;
            clientScreen.askToMoveStudent();
        }
    }

    /**It changes the current game state with its following.
     * @param message the swap message containing the next state*/
    public void changePhase(ChangePhaseMessage message){
        this.state=message.getState();
        switch (state){
            case setupPlayers -> {throw new RuntimeException("something went really wrong");}
            case planPlayCard -> {
                playedCardThisTurnByPlayerId = new HashMap<>();
                if( firstTurn) {//&&
                    if(isYourTurn())
                        clientScreen.showHand();
                    else clientScreen.showBoards();
                    firstTurn=false;
                }
            }
            case actionMoveStudent -> {
                activeCharacter=0;
            }
            case actionMoveMother -> {
                if(isYourTurn() && winner==-1)
                    clientScreen.askToMoveMother();
            }
            case actionChooseCloud ->{
                if(isYourTurn() && winner==-1)
                    clientScreen.showClouds();
            }
        }
    }

    /**It prepares the client screen to show the endgame message.
     * @param message the message containing all the victory parameters (winner(s), win condition)*/
    public void gameOver(GameOverMessage message){
        if(players.size()==4)
            winner= message.getWinnerTeam();
        else winner=message.getWinnerID();
        gameOverReason=message.getWinningCondition();
        clientScreen.gameOver();
    }


    @Override
    public void update(ServerMessage message) {
        message.doAction(this);
        if(!isYourTurn())
            clientScreen.refresh();
    }

    /**It activates the assistant card selected by the player.
     * @param cardId the id of the card to play
     * @exception CardNotFoundException if the card the player wants to play is unavailable*/
    public void useAssistantCard(int cardId){
        if(personalPlayer.getAssistantCards()[cardId]) {
            notify(new PlayAssistantMessage(id, cardId+10*id));
            personalPlayer.removeCard(cardId);
        }
        else
            throw new CardNotFoundException("card :"+(cardId+1)+" not available");
    }

    /**It moves mother nature of the specified amount of steps.
     * @param steps the number of steps to move mother nature*/
    public void moveMother(int steps){
        notify(new MoveMotherMessage(steps,personalPlayer.getId()));
    }

    /**It moves a student of a certain color from the entrance to the dining room.
     * @param colorOfStudent the color of the moved student*/
    public void moveStudentToHall(int colorOfStudent) throws NoTokenFoundException{
            int studID=personalPlayer.getBoard().getStudentFromColorInEntrance(colorOfStudent);
            notify(new StudentToHallMessage(personalPlayer.getId(), studID));
    }

    /**It moves a student of a certain color from the entrance to a target island.
     * @param colorOfStudent color of the moved student
     * @param islandId id of the target island
     * @exception IllegalMoveException if the island is not available*/
    public void moveStudentToIsland(int colorOfStudent, int islandId) throws IllegalMoveException,NoTokenFoundException{
        int studID=personalPlayer.getBoard().getStudentFromColorInEntrance(colorOfStudent);
        if( islands.stream().map(SimplifiedIsland::getIslandId).toList().contains(islandId))
            notify(new StudentToIslandMessage(personalPlayer.getId(), studID, islandId));
        else throw new IllegalMoveException("Island not available");
    }

    /**It chooses the cloud to refill entrance.
     * @param cloudId the id of the chosen cloud
     * @exception IllegalMoveException if the cloud is unavailable*/
    public void chooseCloud(int cloudId){
        if(cloudId<clouds.size() && clouds.get(cloudId)[0]!=-1)
            notify(new ChooseCloudMessage(cloudId, personalPlayer.getId()));
        else
            throw new IllegalMoveException("invalid Cloud");
    }

    /**It activates one of the character cards (expert mode only).
     * @param cardId the id of the chosen card
     * @param parameters the parameters required to trigger the card effect*/
    public void playCharacter(int cardId, ParameterObject parameters){
        notify(new CharacterCardMessage(this.id, parameters,cardId));
    }

    /**It sends information about player's customization.
     * @param towerColor the color of the team
     *@param mage the selected magician*/
    public void choosePlayerCustom(int towerColor,int mage){
       notify(new PrePlayerMessage(0,towerColor,mage,name));
    }

    /**@return the list of id of character cards*/
    public List<Integer> getCharacters() {
        return characters;
    }

    /**@return the association between token character card and its list of students*/
    public Map<Integer, List<Integer>> getStudentsOnCard() {
        return studentsOnCard;
    }

    /**@return the association between character card and its cost*/
    public Map<Integer, Integer> getCostOfCard() {
        return costOfCard;
    }

    /**@return the current game state*/
    public GameState getState() {
        return state;
    }

    /**@return •true: is the player's turn to play
     * <p>•false: is not the player's turn to play</p>*/
    public boolean isYourTurn(){
        if(state!=GameState.setupPlayers)
            return this.turnOf==personalPlayer.getId();
        else return this.turnOf==this.id;
    }

    /**@return the id of the assistan card played this round*/
    public int getCardYouPlayed() {
        return cardYouPlayed;
    }

    /**@return the id of the active character card (expert mode only)*/
    public int getActiveCharacter() {
        return activeCharacter;
    }

    /**@return the id of the number of moved tokens in the current round*/
    public int getStudentMoved() {
        return studentMoved;
    }

    /**@return the list of available tower colors*/
    public List<Integer> getAvailableColor() {
        return availableColor;
    }

    /**@return •true: the game is team game
     * <p>•false: the game is single player game</p>*/
    public boolean isTeamPlay() {
        return teamPlay;
    }

    /**@return the index of the island specified by its id
     * @param islandID the id of the searched island*/
    public int getIslandPositionByID(int islandID){
        for (SimplifiedIsland island:islands) {
            if(island.getIslandId()==islandID)
                return islands.lastIndexOf(island);
        }
        return -1;
    }

    /**@return the number of players awaiting into the lobby*/
    public int getPlayersWaitingInLobby() {
        return playersWaitingInLobby;
    }

    /**@return the list of sub-islands of the specified island
     * @param island the target island*/
    public ArrayList<SimplifiedIsland> getEverySubIsland(SimplifiedIsland island){
        ArrayList<SimplifiedIsland> every = new ArrayList<>(island.getSubIslands());
        for (SimplifiedIsland subIsland:island.getSubIslands()) {
            every.addAll(getEverySubIsland(subIsland));
        }
        return every;
    }

    /**@return the simplified island based on the specific id
     * @param id the id of the island
     * @exception NullPointerException if the island is not found*/
    public SimplifiedIsland getIslandById(int id){
        for (SimplifiedIsland island: islands) {
            if(island.getIslandId()==id)
                return island;
            for (SimplifiedIsland subIsland:getEverySubIsland(island)) {
                if(subIsland.getIslandId()==id)
                    return subIsland;
            }
        }
        throw new NullPointerException("no island found");
    }

    /**@return the id of the winning player*/
    public int getWinner() {
        return winner;
    }
    /**@return the code relative to the winning reason*/
    public int getGameOverReason() {
        return gameOverReason;
    }

    /**@return the list of indexes of available magicians*/
    public List<Integer> getAvailableMages() {
        return availableMages;
    }
    public String getTurnPlayer(){
       return players.get(turnOf).getUsername();
    }
}
