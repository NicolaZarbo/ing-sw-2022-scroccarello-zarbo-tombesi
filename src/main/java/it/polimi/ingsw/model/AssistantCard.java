package it.polimi.ingsw.model;

public class AssistantCard extends Card {
    private final int id;
    private final Mage mage;
    private final int valTurn;
    private final int moveMother;

     public AssistantCard(int id, int valT, int valM, Mage mage){
         this.id=id;
         this.valTurn=valT;
         this.moveMother =valM;
         this.mage= mage;
     }

    public int getId() {
        return id;
    }

    public int getMoveMother() {
        return moveMother;
    }

    public int getValTurn() {
        return valTurn;
    }

    public Mage getMage() {
        return mage;
    }
}


