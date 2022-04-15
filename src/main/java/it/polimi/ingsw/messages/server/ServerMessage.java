package it.polimi.ingsw.messages.server;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.model.Game;

public abstract class ServerMessage {
protected String json;
protected String messageType;

    public String getType() {
        return messageType;
    }

    protected void serialize(){
        Gson gson=new Gson();
        JsonObject jj ;
        messageType = this.getClass().getSimpleName();
        jj=gson.toJsonTree(this,this.getClass()).getAsJsonObject();
        this.json= jj.toString();
    }
    /** Called by factory, creates the message from a json string  */
    public ServerMessage(String json){
        this.messageType = this.getClass().getSimpleName();
        JsonObject gg = JsonParser.parseString(json).getAsJsonObject();
        if (!this.messageType.equals(gg.get("messageType").getAsString()))
            throw new MessageErrorException("needed "+ messageType +", found " + gg.get("messageType").getAsString());
        this.json=gg.toString();
        parseMessage(gg);
    }

    public ServerMessage(RuntimeException error) {
        if(error==null)
            throw new NullPointerException("missing error");
    }

    public ServerMessage(Game game){
        if(game==null)
            throw new NullPointerException();
    }
    public String getJson(){
        return  json;
    }
    /** Checks the message type, further parsing in subclasses */
    protected abstract void parseMessage(JsonObject gg) ;



}
