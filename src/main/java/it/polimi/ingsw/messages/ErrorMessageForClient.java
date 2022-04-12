package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ErrorMessageForClient extends FromServerMessage{
private String errorMessage;
int targetPlayerId;

    public ErrorMessageForClient(int playerId, RuntimeException error) {
        super(error);
        this.errorMessage=error.getMessage();
        this.targetPlayerId=playerId;
        serialize();
    }

    public ErrorMessageForClient(String json){
        super(json);

    }

    @Override
    protected void parseMessage(JsonObject gg) {
        super.parseMessage(gg);
        Gson gson=new Gson();
        this.errorMessage=gson.fromJson(gg.get("errorMessage"),String.class);
        this.targetPlayerId=gson.fromJson(gg.get("targetPlayerId"),Integer.class);
    }

    public String getErrorInfo() {
        String info;
        info="player : "+targetPlayerId+" \n error type : "+this.getType()+" error message : " + errorMessage;
        return info;
    }
}
