package it.polimi.ingsw.model.character;


//parameter object pattern
public class ParameterObject {
    private int targetStudentId;
    private  int otherTargetId;
    private int[] targetStudentsOnEntrance, targetStudentsForExhange;

    public ParameterObject(){

    }
    /** otherTarget -> player ID, targetStudentsForExhange : card10 -> students on hall, card7 -> students on card */
    public ParameterObject(int otherTargetId, int[] targetStudentsOnEntrance, int[] targetStudentsForExchange) {
        if(targetStudentsForExchange.length==targetStudentsOnEntrance.length && targetStudentsForExchange.length<=3){
            this.otherTargetId = otherTargetId;
            this.targetStudentsOnEntrance = targetStudentsOnEntrance;
            this.targetStudentsForExhange = targetStudentsForExchange;
        }
        else
            throw new IllegalArgumentException("array dimension invalid");
    }
    /**  otherTargetId : card1 -> target Island ID, card11 -> player ID  */
    public ParameterObject( int targetStudentId, int otherTargetId){

        this.otherTargetId = otherTargetId;
        this.targetStudentId=targetStudentId;
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
        return targetStudentsForExhange;
    }
}
