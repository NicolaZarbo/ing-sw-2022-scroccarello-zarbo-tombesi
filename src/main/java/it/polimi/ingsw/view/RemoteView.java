package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.MessageFactory;
import it.polimi.ingsw.messages.client.ClientMessage;
import it.polimi.ingsw.messages.server.ErrorMessageForClient;
import it.polimi.ingsw.messages.server.ServerMessage;
import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.model.Mage;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import it.polimi.ingsw.server.Lobby;

import java.util.ArrayList;

public class RemoteView extends Observable<ClientMessage> implements Observer<ServerMessage> {
    private LobbyPlayer player;
    private String nickname;
    private ClientConnection connection;
    private Lobby lobby;
    private ArrayList<Mage> availableMages ;
    private ArrayList<LobbyPlayer> prePlayers;

    public void setPlayer(LobbyPlayer player) {
        this.player = player;
    }

    @Override
    public void update(ServerMessage message) {
        sendToClient(message);
    }
    //is this a valid way to send error messages??
    //should we instead use the catch in message receiver or the update on serverMessage?
    /**@param observable adds a ErrorMessageForClient Observer to this observable*/
    public void setErrorReceiver(Observable<ErrorMessageForClient> observable){
        observable.addObserver(new InputErrorReceiver());
    }

    private class InputErrorReceiver implements Observer<ErrorMessageForClient>{
        @Override
        public void update(ErrorMessageForClient message) {
            connection.asyncSend(message.getJson());
        }
    }

    private class MessageReceiver implements Observer<String> {
        @Override
        public void update(String message) {
            try{
                ClientMessage clientMessage = MessageFactory.getMessageFromClient(message);
                doAction(clientMessage);
            }catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e){
                connection.asyncSend("Error!");
            }
        }

    }
    /** notifies the central controller*/
    private void doAction(ClientMessage mess){
        notify(mess);
    }
    public void sendToClient(GenericMessage message){
        connection.asyncSend(message.getJson());
    }
    public RemoteView(ClientConnection connection, String nickname) {
        this.connection= connection;
        this.nickname=nickname;
        connection.addObserver(new MessageReceiver());

    }

    public String getNickname() {
        return nickname;
    }


}
