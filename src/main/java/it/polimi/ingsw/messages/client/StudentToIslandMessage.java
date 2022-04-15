package it.polimi.ingsw.messages.client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class StudentToIslandMessage extends ClientMessage {
    private int studentId, finalPositionId;


    //this will be changed to reflect the super constructor using the view
    public StudentToIslandMessage(int playerId, int studentId, int finalPositionId) {
        super(playerId);
        this.studentId = studentId;
        this.finalPositionId = finalPositionId;
        super.serialize();
    }
    public StudentToIslandMessage(String json){
        super(json);
    }

    @Override
    protected void parseMessage(JsonObject gg) {
        Gson gson= new Gson();
        this.studentId=gson.fromJson(gg,this.getClass()).getStudentId();
        this.finalPositionId=gson.fromJson(gg,this.getClass()).getFinalPositionId();
        this.playerId=gson.fromJson(gg,this.getClass()).getPlayerId();
    }

    public int getStudentId() {
        return studentId;
    }

    public int getFinalPositionId() {
        return finalPositionId;
    }

}
