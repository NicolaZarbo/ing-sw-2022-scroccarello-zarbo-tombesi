package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;

/**The message to move a student token to a target island.*/
public class StudentToIslandMessage extends ClientMessage {
    private int studentId, finalPositionId;


    /**It builds the message starting from the information of the player.
     * @param playerId the id of the player sending
     * @param studentId the id of the moved token
     * @param finalPositionId the id where to move the token*/
    public StudentToIslandMessage(int playerId, int studentId, int finalPositionId) {
        super(playerId);
        this.studentId = studentId;
        this.finalPositionId = finalPositionId;
        super.serialize();
    }

    /**It builds the message starting from the json message.
     * @param json the string message*/
    public StudentToIslandMessage(String json){
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerTurn().moveStudentToIsland(this);
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        this.studentId=gson.fromJson(gg,this.getClass()).getStudentId();
        this.finalPositionId=gson.fromJson(gg,this.getClass()).getFinalPositionId();
        this.playerId=gson.fromJson(gg,this.getClass()).getPlayerId();
    }

    /**@return the id of the token to move*/
    public int getStudentId() {
        return studentId;
    }

    /**@return the id of the island where to move the token*/
    public int getFinalPositionId() {
        return finalPositionId;
    }

}
