package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.exceptions.MessageErrorException;
import it.polimi.ingsw.messages.servermessages.ErrorMessageForClient;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.RemoteView;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**The lobby which contains connected clients and lets them customize their player.*/
public class Lobby {
    private final ArrayList<ClientConnection> connections = new ArrayList<>();
   // private final int lobbyCode;
    private final int lobbyDimension;
    private final ArrayList<RemoteView> playersViews;
    private final boolean easy;

    /**It creates the lobby with the number of players and adding the first player connection.
     * @param nickname the nickname chosen by the player
     * @param easy it states if the game is in easy or expert mode
     * @param firstConnection the connection handler of the first player connected
     * @param lobbyCode the code of the lobby
     * @param lobbyDimension the number of accepted players*/
    public Lobby(ClientConnection firstConnection, int lobbyCode, boolean easy, int lobbyDimension, String nickname) {
        connections.add(firstConnection);
        playersViews=new ArrayList<>();
        playersViews.add(new RemoteView(firstConnection,nickname));
        //this.lobbyCode = lobbyCode;
        this.easy=easy;
        this.lobbyDimension=lobbyDimension;
    }

    /**It adds a client connection to the lobby.
     * @param connection the connection handler of the player
     * @param nickname the nickname of the player*/
    public void addConnection(ClientConnection connection , String nickname){
        connections.add(connection);
        playersViews.add(new RemoteView(connection, nickname));
    }

    /**It starts a new game as soon as the lobby is filled with the right number of players.*/
    public void startGame(){
        Game model = new Game(easy, getLobbyDimension());
        Controller controller = new Controller(model);
        model.getSetupPhase().setPreOrder(playersViews.stream().map(RemoteView::getNickname).collect(Collectors.toList()));
        for (RemoteView view: playersViews) {
            model.addObserver(view);
            view.addObserver(controller);
            view.setErrorReceiver(controller);
        }
        model.getSetupPhase().startPersonalisation();
    }

    /**It removes a client connection from the lobby.
     * @param connection the connection to remove*/
    public void removeFromLobby(ClientConnection connection, String reason){
        if(this.connections.contains(connection)){
            int indexOf= connections.indexOf(connection);
            connections.remove(connection);
            String text = "player : "+playersViews.remove(indexOf).getNickname()+" got disconnected ("+reason+")\n close the application";
            ErrorMessageForClient disconnectMess= new ErrorMessageForClient("all", new MessageErrorException(text));
            for (ClientConnection conn:connections) {
                conn.asyncSend(disconnectMess.getJson());
            }
        }
    }

    /**@return the maximum number of players of the lobby*/
    public int getLobbyDimension() {
        return lobbyDimension;
    }

    /**@return the current number of clients connected*/
    public int numberOfConnections(){
        return connections.size();
    }

    public ArrayList<ClientConnection> getConnections() {
        return connections;
    }

    /**It states if a certain player is connected to the lobby or not.
     * @param nick nickname of the player to check
     * @return •true: the player is connected to the lobby
     * <p>•false: the player is not connected to the lobby</p>*/
    public boolean isPlayerConnected(String nick){
        for (RemoteView v:playersViews) {
            if(v.getNickname().equalsIgnoreCase(nick))
                return true;
        }
        return false;
    }
}
