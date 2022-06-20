package it.polimi.ingsw.util;


/**The parameter object pattern used for character cards' effects-*/
public class ParameterObject {
    private int targetStudentId;
    private  int otherTargetId;
    private int[] targetStudentsOnEntrance, targetStudentsForExchange;
    private final int nParam;

    /**It is used for Turn Effect Cards since they only set active the bonus value of the turn.
     *<p> card 2, card 6, card 8</p>
     * */
    public ParameterObject(){
        nParam=0;
    }

    /** It is used for card 7 and card 10.
     * @param otherTargetId player id
     * @param targetStudentsForExchange <p>•for card 10: students on hall</p>
     *                                  <p>•for card 7: students on card</p>
     * @param targetStudentsOnEntrance  list of students on entrance
     * @exception IllegalArgumentException if the array's lengths don't respect the card's limitations
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

    /** It is used for card 1 and card 11.
     * @param targetStudentId id of the target student
     * @param otherTargetId <p>•for card 1: target island id</p>
     *                     <p>•for card 11: player id</p>
     * */
    public ParameterObject( int targetStudentId, int otherTargetId){
        this.otherTargetId = otherTargetId;
        this.targetStudentId=targetStudentId;
        nParam=2;
    }

    /**It is used for card 9.
     * @param otherTargetId the target color's number*/
    public ParameterObject(int otherTargetId) {
        nParam=1;
        this.otherTargetId = otherTargetId;
    }

    /**@return the target student id*/
    public int getTargetStudentId() {
        return targetStudentId;
    }

    /**@return •target player id (card 7, card 10, card 11)
     * <p>•target island id (card 1) </p>
     * <p>•target color number (card 9)</p>*/
    public int getOtherTargetId() {
        return otherTargetId;
    }

    /**@return list of selected students from the entrance*/
    public int[] getTargetStudentsOnEntrance() {
        return targetStudentsOnEntrance;
    }

    /**@return list of student tokens to exchange*/
    public int[] getTargetStudentsForExchange() {
        return targetStudentsForExchange;
    }

    /**It is used to check if the right type of parameter object is being used.
     * @return number of parameters */
    public int getnParam() {
        return nParam;
    }
}
