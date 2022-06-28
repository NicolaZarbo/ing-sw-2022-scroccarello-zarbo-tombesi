package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.exceptions.CardNotFoundException;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.messages.servermessages.*;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.tokens.MotherNature;
import it.polimi.ingsw.model.tokens.Professor;
import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

/**<p>Central class of the model.</p>
 * <p>It manages (in a logic sense) the game between the players, which is physically handled on a server, which is the reason it is an <b>Observable</b> of <b>ServerMessage</b></p>
 * <p>A different type of game is created according to the difficulty chosen and to the number of players</p>*/
public class Game extends Observable<ServerMessage> {

    private final boolean easy;
    private final int nPlayers;
    private final List<Island> islands;
    private  Player[] players;
    private final Cloud[] clouds;
    private final MotherNature motherNature;
    private int currentPlayerId;
    private final HashMap<Integer, AssistantCard> cardPlayedThisRound;
    private List<Integer> playIngOrder;
    private final Bag bag;
    private final Professor[] teachers;
    private final List<CharacterCard> characters;
    private int cardBonusActive;
    private TokenColor targetColor;
    private final Action actionPhase;
    private final Setup setupPhase;
    private final Planning planningPhase;
    private GameState actualState;
    private MultipleServerMessage multiMessage;


    /** Creates the game according to the selected rules.
     * @param easy difficulty chosen by players;
     *             <p>• true: game is in easy mode</p>
     *             <p>• false: game is in expert mode</p>
     *             <p> </p>
     * @param numberOfPlayer number of players;
     *                       <p>supported formats are:</p>
     *                       <p>• 2 players 1vs1 game</p>
     *                       <p>• 3 players 1vs1vs1 game</p>
     *                       <p>• 4 players 2vs2 game</p>*/
    public Game(boolean easy, int numberOfPlayer){
        this.nPlayers =numberOfPlayer;
        actionPhase= new Action(this);
        planningPhase= new Planning(this);
        setupPhase = new Setup(this);
        this.easy=easy;
        this.bag=new Bag(26,5);
        this.islands=Setup.createIslands(12,bag);
        this.clouds= Setup.createClouds(nPlayers);
        this.players =new Player[numberOfPlayer];
        this.teachers= setupPhase.createProfessor(5);
        this.motherNature=new MotherNature(islands.get(0).getID());
        if(!easy){
            this.characters= setupPhase.createCharacterCards(bag,8);
        }
        else{characters=null;}
        this.cardBonusActive=0;
        this.cardPlayedThisRound=new HashMap<>();
        planningPhase.setCloud();
        actualState=GameState.setupPlayers;
        multiMessage=null;
        currentPlayerId=0;
        //this.playIngOrder= Arrays.stream(this.players).map(Player::getId).toList();TODO
    }

    @Override
    protected void notify(ServerMessage message) {
        super.notify(message);
    }

    /**It prepares the message to send to the server.
     * @param message the message to send*/
    public void groupMultiMessage(ServerMessage message){
        if (multiMessage!= null)
            multiMessage.addMessage(message);
        else multiMessage= new MultipleServerMessage(message);
    }

    /**It notifies the server with the serialized multiMessage.
     * @exception NullPointerException if it tries to send a null message*/
    public void sendMultiMessage(){
        if(multiMessage==null)
            throw new NullPointerException("Sending a null message");
        multiMessage.serialize();
        this.notify(multiMessage);
        multiMessage=null;
    }

    /**It changes the state of the game to the following phase based on the states order
     * @see GameState*/
    public void moveToNextPhase(){
        int before = actualState.ordinal();
        actualState=GameState.values()[before + 1];
        groupMultiMessage(new ChangePhaseMessage(this));
        sendMultiMessage();
    }

    /**It changes the current playing player based on the playingOrder.*/
    public void changePlayerTurn(){
        int actualIndex=playIngOrder.indexOf(currentPlayerId);
        actualIndex+=1;
        if(actualState==GameState.actionChooseCloud){
            if(!isLastPlayerTurn()){
                actualState=GameState.actionMoveStudent;
                currentPlayerId=playIngOrder.get(actualIndex%nPlayers);
                if(players[0].getHand().getAssistant().size()==0)
                    gameOver();
            }
            else{
                currentPlayerId=playIngOrder.get(0);
                actualState=GameState.planPlayCard;
                planningPhase.setCloud();
            }

            groupMultiMessage(new ChangePhaseMessage(this));
            groupMultiMessage(new ChangeTurnMessage(this));
        }else
        if (actualState == GameState.planPlayCard) {
            if(!isLastPlayerTurn()){
                currentPlayerId=playIngOrder.get(actualIndex%nPlayers);
                groupMultiMessage(new ChangeTurnMessage(this));
            }else {
                planningPhase.roundOrder();
                actualState=GameState.actionMoveStudent;
                currentPlayerId=playIngOrder.get(0);

                groupMultiMessage(new ChangePhaseMessage(this));
                groupMultiMessage(new ChangeTurnMessage(this));
            }

        }
        sendMultiMessage();
    }

    /** @param playerId the id of the target player
     * @return the assistant card played by the player*/
    public AssistantCard getPlayedCard(int playerId){
        return cardPlayedThisRound.get(playerId);
    }

    /**@return the current state of the game*/
    public GameState getActualState() {
        return actualState;
    }

    /**It initializes the set of players and sets the order for the current round.
     * @param players set of players*/
    public void setPlayers(Player[] players) {
        this.players = players;
        this.playIngOrder= Arrays.stream(players).map(Player::getId).toList();
    }

    /**It states if the round is over
     * @return •true if the round is over
     *          <p>•false otherwise</p>*/
    public boolean isLastPlayerTurn(){
        return this.playIngOrder.indexOf(this.currentPlayerId)==this.nPlayers-1;
    }

    /**@return planning component of the game*/
    public Planning getPlanningPhase() {
        return planningPhase;
    }

    /**@return action component of the game*/
    public Action getActionPhase() {
        return actionPhase;
    }

    /**@return setup component of the game*/
    public Setup getSetupPhase() {
        return setupPhase;
    }

    /**@return set of cards played by each player*/
    public HashMap<Integer, AssistantCard> getAllCardPlayedThisRound() {
        return cardPlayedThisRound;
    }

    /**It adds the reference to the played card in function of the player who has played it
     * @param playedCard the card played
     * @param playerId the id of the corresponding player*/
    public void addCardPlayedThisRound(int playerId, AssistantCard playedCard) {
        this.cardPlayedThisRound.put(playerId,playedCard);
    }

    /**@return list of character cards. For expert mode only*/
    public List<CharacterCard> getCharacters(){return characters;}

   /**@param id the id of the target character card
    * @return the target character card
    * @exception CardNotFoundException the id of the card is not valid*/
    public CharacterCard getCharacter(int id) {
        for (CharacterCard card: this.characters) {
            if(card.getId()==id)
                return card;
        }
        throw new CardNotFoundException();
    }

    /**@return the list of player ids ordered to play this round*/
    public List<Integer> getPlayIngOrder() {
        return playIngOrder;
    }

    /**It sets the playing order for this round.
     * @param playIngOrder list of player ids ordered based on the assistant card played by each of them
     * @see Planning */
    public void setPlayIngOrder(List<Integer> playIngOrder) {
        this.playIngOrder = playIngOrder;
    }

    /**It states if a turn effect character has been played this round. For expert mode only.
     * @param bonus the id of the target card to see if it has been played
     * @return •true if the card has been played
     * <p>•false otherwise</p>*/
    public boolean isBonusActive(int bonus) {
        return cardBonusActive==bonus;
    }

    /**It returns the color under the target of the effect of the character card number 9. For expert mode only.
     * @return the color which is nerfed by the card effect
     * @see it.polimi.ingsw.model.characters.Character9*/
    public TokenColor getTargetColor() {
        return targetColor;
    }

    /**It sets the target color under the effect of character card number 9. For expert mode only.
     * @param targetColor nerfed color*/
    public void setTargetColor(TokenColor targetColor) {
        this.targetColor = targetColor;
    }

    /**It sets as active the corresponding character effect. For expert mode only.
     * @param cardBonus id of the turn effect character activated*/
    public void setCardBonusActive(Integer cardBonus) {
        this.cardBonusActive=(cardBonus) ;
    }

    /**It sets to 0 the active bonus. For expert mode only.*/
    public void resetBonus(){
        this.cardBonusActive=0;
        this.targetColor=null;
    }

    /**It returns the bonus active (expert mode only).
     @return id of the <i>turn effect character</i> which triggered the bonus
     @see it.polimi.ingsw.model.characters.TurnEffectCharacter*/
    public int getCardBonusActive() {
        return cardBonusActive;
    }

    /**It returns the target island based on its id.
     * @param id id of the island to find
     * @return the corresponding island
     * @exception NullPointerException the island is not available*/
    public Island getIsland(int id) throws NullPointerException{
        for (Island island: this.islands) {
            if(island.getID()==id)
                return island;
        }
        throw new NullPointerException("island not available");
    }
    /**@return the bag containing the remaining student tokens*/
    public Bag getBag(){
        return bag;
    }

    /**@return list of the islands in the game*/
    public List<Island> getIslands() {return this.islands;}

    /**@return •true if the game is in easy mode
     * <p>•false if the game is in expert mode</p>*/
    public boolean isEasy() {
        return easy;
    }

    /**@return number of players*/
    public int getNPlayers() {
        return nPlayers;
    }

    /**@return mother nature token
     * @see MotherNature*/
    public MotherNature getMotherNature(){
        return this.motherNature;
    }

    /**@return list of clouds*/
    public Cloud[] getClouds(){
        return this.clouds;
    }
    /**@return list of players*/
    public Player[] getPlayers(){
        return this.players;
    }

    /**It returns the player corresponding to the id.
     * @param playerId id of the target player
     * @return target player*/
    public Player getPlayer(int playerId){
        if(actualState==GameState.setupPlayers)
            throw new IllegalMoveException("players aren't available yet");
        for (Player player : this.players)
            if (player.getId() == playerId)
                return player;
        throw new NullPointerException("not existing player ");
    }

    /**It returns the professor of the target color if it is on the game.
     * @param color color of the professor
     * @exception NullPointerException if the professor is not on the game*/
    public Professor getFromGame(TokenColor color){
        Professor temp;
        if(isProfessorOnGame(color)){
            temp=teachers[color.ordinal()];
            teachers[color.ordinal()]=null;
            return temp;
        }else throw new NullPointerException();
    }

    /** Invoked when a <i>end game check</i> is passed because of one of the following reasons:
     * <p>• bag is empty</p>
     * <p>• someone used every tower in their board</p>
     * <p>• someone used his last card</p>
     * <p>• there are only 3 cluster of islands left</p>
     * <p>The winner is </p>*/
    public void gameOver(){// todo test for 4 player etc
        int winner;
        int min;
        if(nPlayers==4){
            if(players[1].getBoard().towersLeft()>players[0].getBoard().towersLeft()){
                winner=1;
            }else winner=2;
        }else {
            List<Integer> towerForPlayer= Arrays.stream(players).map(Player::getBoard).map(Board::towersLeft).toList();
            min=towerForPlayer.stream().min(Integer::compareTo).orElse(-1);
            winner=towerForPlayer.lastIndexOf(min);
        }
        groupMultiMessage(new GameOverMessage(this,winner));
        //sendMultiMessage();
    }
    /**It states if the target professor has joined one board
     *@param color color of the target professor
     * @return •true: the professor is on the game
     *      <p>•false: the professor is in none of the boards</p>*/
    public boolean isProfessorOnGame(TokenColor color){
        return teachers[color.ordinal()] != null;
    }


    /**@return the id of the player currently playing*/
    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

}
