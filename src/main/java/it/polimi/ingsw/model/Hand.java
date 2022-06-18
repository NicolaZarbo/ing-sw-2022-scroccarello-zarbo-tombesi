package it.polimi.ingsw.model;
import it.polimi.ingsw.exceptions.CardNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
/**The hand of the player. It contains the number of coins owned, his deck of available assistant cards and even a discarded pile of card already played.
 * @see AssistantCard*/
public class Hand {
    private int coin;
    private final ArrayList<AssistantCard> assistant;
    private final LinkedList<AssistantCard> discarded;

    /**It builds the hand of the player by initializing his deck of cards.
     * <p>Notice that the player is not given any coin at the beginning of the game (expert mode only).</p>
     * @param assistInit list of playable assistant cards at the beginning of the game */
    public Hand(ArrayList<AssistantCard> assistInit){
        coin =0;
        assistant =new ArrayList<>();
        discarded=new LinkedList<>();
        assistant.addAll(assistInit);
    }

    /**@return discard pile of the player*/
    public LinkedList<AssistantCard> getDiscarded() {
        return discarded;
    }

    /**@return deck of available cards of the player*/
    public ArrayList<AssistantCard> getAssistant() {
        return assistant;
    }

    /**@return amount of coins owned by the player (expert mode only)*/
    public int getCoin() {
        return coin;
    }

    /**It adds a coin to the hand of the player (expert mode only).*/
    public void addCoin(){
        coin++;
    }

    /**It pays the price of activating a character card (expert mode only).
     * @param cost cost paid to use the card*/
    public void payCoin(int cost){
        this.coin=this.coin-cost;
    }

    /**It verifies if the player has enough coins to activate the effect of a character card.
     * @param cost cost of the card*/
    public boolean enoughCoin(int cost){
        return (this.coin-cost>=0);
    }

    /**It returns the assistant card the player currently wants to play. Also moves it to the discarded pile and then the card is removed from the available cards.
     * @param cardId id of the played card
     * @return card played
     * @exception CardNotFoundException if the selected card is not available*/
    public AssistantCard playAssistant(int cardId){

        for(int i=0;i<assistant.size();i++){
            if(assistant.get(i).getId()==cardId) {
                AssistantCard played=assistant.get(i);
                discarded.add(assistant.get(i));
                assistant.remove(i) ;
                return played;
            }
        }
        throw new CardNotFoundException("no such card found");
    }
}
