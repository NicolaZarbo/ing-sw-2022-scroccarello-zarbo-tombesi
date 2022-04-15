package it.polimi.ingsw.messages.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class StudentToHallMessage extends ClientMessage {

    private int studentId;

    public StudentToHallMessage( int playerId, int studentId) {
        this.playerId = playerId;
        this.studentId = studentId;
        super.serialize();
    }

    public int getPlayerId() {
        return playerId;
    }

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
