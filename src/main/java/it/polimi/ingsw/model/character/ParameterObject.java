package it.polimi.ingsw.model.character;


//parameter object pattern
public class ParameterObject {
    private int targetStudentId;
    private  int otherTargetId;
    private int[] targetStudentsOnEntrance, targetStudentsForExchange;
    private int nParam;

    /**use it for Turn Effect Cards, aka:
     * card2, card6, card8
     * */
    public ParameterObject(){}

    /** use it for card7 and card 10
     * otherTarget -> player ID
     * targetStudentsOnEntrance -> student IDs
     * targetStudentsForExhange : card10 -> students on hall, card7 -> students on card
     * */
    public ParameterObject(int otherTargetId, int[] targetStudentsOnEntrance, int[] targetStudentsForExchange) {
        if(targetStudentsForExchange.length==targetStudentsOnEntrance.length && targetStudentsForExchange.length<=3){
            this.otherTargetId = otherTargetId;
            this.targetStudentsOnEntrance = targetStudentsOnEntrance;
            this.targetStudentsForExchange = targetStudentsForExchange;
            nParam=3;
        }
        else
            throw new IllegalArgumentException("array dimension invalid");
    }

    /** use it for card1 and card 11
     * otherTargetId : card1 -> target Island ID, card11 -> player ID
     * */
    public ParameterObject( int targetStudentId, int otherTargetId){
        this.otherTargetId = otherTargetId;
        this.targetStudentId=targetStudentId;
        nParam=2;
    }

    /** use it for card9*/
    public ParameterObject(int otherTargetId) {
        nParam=1;
        this.otherTargetId = otherTargetId;
    }

    public int getTargetStudentId() {
        return targetStudentId;
    }
    /** player id / island id / color id */
    public int getOtherTargetId() {
        return otherTargetId;
    }

    public int[] getTargetStudentsOnEntrance() {
        return targetStudentsOnEntrance;
    }

    public int[] getTargetStudentsForExchange() {
        return targetStudentsForExchange;
    }

    public int getnParam() {
        return nParam;
    }
}
