package it.polimi.ingsw.messages;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.model.Game;

public abstract class FromServerMessage {
protected String json;
protected String type;

    public String getType() {
        return type;
    }

    protected void serialize(){
        Gson gson=new Gson();
        JsonObject jj = new JsonObject();
        type = this.getClass().getSimpleName();
        jj.addProperty("MessageType", type);
        jj.add("MessageContent",gson.toJsonTree(this,this.getClass()));
        this.json= jj.toString();
    }
    /** Called by factory, creates the message from a json string  */
    protected FromServerMessage(String json){
        this.type= this.getClass().getSimpleName();
        JsonObject gg = JsonParser.parseString(json).getAsJsonObject();
        parseMessage(gg);
    }

    public FromServerMessage(RuntimeException error) {
        if(error==null)
            throw new NullPointerException("missing error");
    }

    public FromServerMessage(Game game){
        if(game==null)
            throw new NullPointerException();
    };
    public String getJson(){
        return  json;
    }
    /** Checks the message type, further parsing in subclasses */
    protected void parseMessage(JsonObject gg) {
        if (!this.type.equals(gg.get("MessageType").getAsString()))
            throw new MessageErrorException("needed "+type+", found " + gg.get("MessageType").getAsString());
        this.json=gg.getAsString();
    }



}
