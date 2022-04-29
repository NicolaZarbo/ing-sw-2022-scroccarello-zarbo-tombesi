package it.polimi.ingsw.messages.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.view.CentralView;

public class ErrorMessageForClient extends ServerMessage {
private String errorMessage;
int targetPlayerId;

    /** used to send information about invalid input to the player*/
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
        Gson gson=new Gson();
        this.errorMessage=gson.fromJson(gg.get("errorMessage"),String.class);
        this.targetPlayerId=gson.fromJson(gg.get("targetPlayerId"),Integer.class);
    }

    @Override
    public void doAction(CentralView view) {
       // view.errorFromServer(this);
    }

    /** returns an error message string*/
    public String getErrorInfo() {
        String info;
        info="player : "+targetPlayerId+" \n error type : "+this.getType()+" error message : " + errorMessage;
        return info;
    }
}
