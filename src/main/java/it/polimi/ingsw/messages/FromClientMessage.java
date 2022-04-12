package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.exceptions.MessageErrorException;

public class FromClientMessage {
    protected int playerId; // ip, id, string of characters?
    protected String json;
    protected String type ;


    protected void serialize(int playerRealIdentifier){
        type= this.getClass().getSimpleName();
        Gson gson = new Gson();
        JsonObject jj = new JsonObject();
        jj.addProperty("PlayerSender",playerRealIdentifier);
        jj.addProperty("MessageType", type);
        jj.add("MessageContent",gson.toJsonTree(this,this.getClass()));
        this.json= jj.toString();
    }
    protected void parseMessage(JsonObject gg) {
        if (!this.type.equals(gg.get("MessageType").getAsString()))
            throw new MessageErrorException("needed "+type+", found " + gg.get("MessageType").getAsString());
        this.json=gg.toString();
    }
    //what is now view will be the simplified game from the client
   /* public FromClientMessage (View view, int realPlayerIdentifier){
         if(view == null || realPlayerIdentifier==null)
            throw new NullPointerException();
      }

    */


}
