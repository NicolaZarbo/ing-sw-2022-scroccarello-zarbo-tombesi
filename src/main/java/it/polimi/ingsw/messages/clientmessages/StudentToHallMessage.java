package it.polimi.ingsw.messages.clientmessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.Controller;

/**The message to move a student token fromt entrance to dining room.*/
public class StudentToHallMessage extends ClientMessage {

    private int studentId;

    /**It builds the message starting from the json message.
     * @param json the string message*/
    public StudentToHallMessage(String json) {
        super(json);
    }

    @Override
    public void doAction(Controller controller) {
        controller.getControllerTurn().moveStudentToHall(this);
    }

    /**It builds the message starting from the information of the player.
     * @param playerId the id of the player sending
     * @param studentId the id of the token to move*/
    public StudentToHallMessage(int playerId, int studentId) {
        super(playerId);
        this.studentId = studentId;
        super.serialize();
    }

    /**@return the id of the moved token*/
    public int getStudentId() {
        return studentId;
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        this.studentId=gson.fromJson(gg,this.getClass()).getStudentId();
        this.playerId=gson.fromJson(gg,this.getClass()).getPlayerId();

    }
}
