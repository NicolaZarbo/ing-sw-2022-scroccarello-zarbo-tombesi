package it.polimi.ingsw.model.tokens;

/**The mother nature token. It moves through the islands and it triggers the influence calculation to decide which player has conquered the island.*/
public class MotherNature  {
    private int position;

    /**It builds the mother nature token.
     * @param pos the id of the starting island for mother nature*/
    public MotherNature(int pos){
            this.position=pos;
    }
    /**@return the id of the current island mother nature is on*/
    public int getPosition(){
        return position;
    }
    /**It moves mother nature to the target island.
     * @param newPos id of the target island*/
    public void changePosition(int newPos){
             this.position=newPos;
    }

}
