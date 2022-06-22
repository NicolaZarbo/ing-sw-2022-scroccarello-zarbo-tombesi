package it.polimi.ingsw.messages.servermessages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.view.CentralView;

/**The error message for the client.*/
public class ErrorMessageForClient extends ServerMessage {
private String errorMessage;
int targetPlayerId;

    /**It is used to send information about invalid input to the player.
     * @param playerId the target player
     * @param error the exception caught after a runtime error occurred*/
    public ErrorMessageForClient(int playerId, RuntimeException error) {
        super(error);
        this.errorMessage=error.getMessage();
        this.targetPlayerId=playerId;
        serialize();
    }

    /**It builds the message starting from the json string.
     * @param json the string message*/
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
       view.errorFromServer(this);
    }

    /**@return the error message string*/
    public String getErrorInfo() {
        String info;
        info="player : "+targetPlayerId+" \n error type : "+this.getType()+" error message : " + errorMessage;
        return info;
    }

    /**@return the id of the player to deliver the message*/
    public int getTargetPlayerId() {
        return targetPlayerId;
    }
}
