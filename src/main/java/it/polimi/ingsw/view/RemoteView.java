package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.clientmessages.ClientMessage;
import it.polimi.ingsw.messages.servermessages.ErrorMessageForClient;
import it.polimi.ingsw.messages.servermessages.ServerMessage;
import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

/**The remote view for the client connection. It receives messages for the client and is involved in sending server messages.*/
public class RemoteView extends Observable<ClientMessage> implements Observer<ServerMessage> {
    private LobbyPlayer player;
    private String nickname;
    private ClientConnection connection;

    /**It fills the handler with the pre-information about the player.
     * @param player the information about player in the lobby*/
    public void setPlayer(LobbyPlayer player) {
        this.player = player;
    }

    @Override
    public void update(ServerMessage message) {
        sendToClient(message);
    }

    /**It initializes an error handler for the client.
     * @param observable attaches an error observer to this observable*/
    public void setErrorReceiver(Observable<ErrorMessageForClient> observable){
        observable.addObserver(new InputErrorReceiver());
    }

    /**The class which sends error messages for client.*/
    private class InputErrorReceiver implements Observer<ErrorMessageForClient>{
        @Override
        public void update(ErrorMessageForClient message) {
            connection.asyncSend(message.getJson());
        }
    }


    /**It notifies the observers with the client message.
     * @param mess the notified message*/
    private void doAction(ClientMessage mess){
        notify(mess);
    }

    /**It sends to the client a generic message.
     * @param message the message to send*/
    public void sendToClient(GenericMessage message){
        connection.asyncSend(message.getJson());
    }

    /**It builds the handler with the player information and adds an observer for the client connection which receives messages from the server.
     * @param nickname the nickname chosen by the client
     * @param connection the information about client connection*/
    public RemoteView(ClientConnection connection, String nickname) {
        this.connection= connection;
        this.nickname=nickname;
        connection.addObserver(new MessageReceiver());
    }

    /**The class which has to receive string messages from the client and convert them to real messages.*/
    private class MessageReceiver implements Observer<String> {
        @Override
        public void update(String message) {
            try{
                ClientMessage clientMessage = MessageFactory.getMessageFromClient(message);
                doAction(clientMessage);
            }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                System.err.println(e.getMessage()+" "+ e.getCause());
            }
        }
    }

    /**@return the nickname chosen by the client*/
    public String getNickname() {
        return nickname.toLowerCase();
    }


}
