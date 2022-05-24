package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.messages.servermessages.ErrorMessageForClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Lobby {
    private final ArrayList<ClientConnection> connections = new ArrayList<>();
    private final int lobbyCode;
    private final int lobbyDimension;
    private Game model;
    private final ArrayList<RemoteView> playersViews;
    private Controller controller;
    private final boolean easy;

    /** creates the lobby with the number of player and the first player connection*/
    public Lobby(ClientConnection firstConnection, int lobbyCode, boolean easy, int lobbyDimension, String nickname) {
        connections.add(firstConnection);
        playersViews=new ArrayList<>();
        playersViews.add(new RemoteView(firstConnection,nickname));
        this.lobbyCode = lobbyCode;
        this.easy=easy;
        this.lobbyDimension=lobbyDimension;
    }
    public void addConnection(ClientConnection connection , String nickname){
        connections.add(connection);
        playersViews.add(new RemoteView(connection, nickname));
    }

    public ArrayList<ClientConnection> getConnections() {
        return connections;
    }
    public void startGame(){
        model = new Game(easy, getLobbyDimension());
        controller = new Controller(model);
        model.getSetupPhase().setPreOrder(playersViews.stream().map(RemoteView::getNickname).collect(Collectors.toList()));
        for (RemoteView view: playersViews) {
            model.addObserver(view);
            view.addObserver(controller);
            view.setErrorReceiver(controller);
        }
        model.getSetupPhase().startPersonalisation();
    }
    public int getLobbyCode() {
        return lobbyCode;
    }
    public void removeFromLobby(ClientConnection connection){
        if(this.connections.contains(connection)){
            int indexOf= connections.indexOf(connection);
            connections.remove(connection);
            ErrorMessageForClient disconnectMess= new ErrorMessageForClient(-1, new MessageErrorException("player :"+playersViews.remove(indexOf).getNickname()+" got disconnected"));
            for (ClientConnection conn:connections) {
                conn.asyncSend(disconnectMess.getJson());
            }
        }
    }
    public int getLobbyDimension() {
        return lobbyDimension;
    }
    public int numberOfConnections(){
        return connections.size();
    }
    public boolean isPlayerConnected(String nick){
        for (RemoteView v:playersViews) {
            if(v.getNickname().equalsIgnoreCase(nick))
                return true;
        }
        return false;
    }
}
