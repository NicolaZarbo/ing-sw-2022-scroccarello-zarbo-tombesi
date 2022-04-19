package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.LobbyPlayer;

import java.util.ArrayList;

public class Lobby {
    private ArrayList<ClientConnection> connections = new ArrayList<>();
    private int lobbyCode, lobbyDimension;
    private Game model;
    private ArrayList<LobbyPlayer> prePlayers= new ArrayList<>(lobbyDimension);
    private Controller controller;
    private boolean easy;

    public Lobby(ClientConnection firstConnection, int lobbyCode, int lobbyDimension, boolean easy) {
        connections.add(firstConnection);
        this.lobbyDimension=lobbyDimension;
        this.easy=easy;
        this.lobbyCode = lobbyCode;
    }
    public void addConnection(ClientConnection connection){
        connections.add(connection);
    }
    public void addPrePlayer(LobbyPlayer prePlayer){
        prePlayers.add(prePlayer);
    }

    public ArrayList<ClientConnection> getConnections() {
        return connections;
    }
    public void startGame(){
        model = new Game(easy,prePlayers);
        controller = new Controller(model);
        //foreach connection create remote view
        //foreach view add observer of model and from controller
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
