package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.Mage;

/**<p>It represents the single assistant card owned by the player.</p>
 * <p>It can be either in the deck of available cards or in the discarded pile.</p>
 * <p>The card is played in the planning phase to decide the turn order and the possible movements for mother nature.</p>
 * <p>Each card can be played once and then they are placed in the discarded pile.</p>*/
public class AssistantCard extends Card {
    private final int id;
    private final Mage mage;
    private final int valTurn;
    private final int moveMother;

    /**It creates the corresponding assistant card.
     * @param id id of the card
     * @param mage relative magician of the assistant
     * @param valM maximum amount of steps to move mother nature
     * @param valT turn value of the card
     * */
    public AssistantCard(int id, int valT, int valM, Mage mage){
         this.id=id;
         this.valTurn=valT;
         this.moveMother =valM;
         this.mage= mage;
    }

    /**@return id of the card*/
    public int getId() {
        return id;
    }

    /**@return maximum number of steps to move mother nature*/
    public int getMoveMother() {
        return moveMother;
    }

    /**@return turn value of the card*/
    public int getValTurn() {
        return valTurn;
    }

    /**@return relative mage of the assistant*/
    public Mage getMage() {
        return mage;
    }

}


