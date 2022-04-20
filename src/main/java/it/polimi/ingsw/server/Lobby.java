package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.List;

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
        model.getSetupPhase().setPreOrder(playersViews.stream().map(RemoteView::getNickname).toList());
        for (RemoteView view: playersViews) {
            model.addObserver(view);
            model.getActionPhase().addObserver(view);
            model.getSetupPhase().addObserver(view);
            model.getPlanningPhase().addObserver(view);
            view.addObserver(controller);
            view.setErrorReceiver(controller);
        }
    }
    public int getLobbyCode() {
        return lobbyCode;
    }

    public int getLobbyDimension() {
        return lobbyDimension;
    }
    public int numberOfConnections(){
        return connections.size();
    }
}
