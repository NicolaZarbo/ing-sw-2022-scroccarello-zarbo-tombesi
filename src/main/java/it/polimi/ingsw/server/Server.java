package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.LobbySetupMessage;
import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.model.Mage;
import it.polimi.ingsw.model.token.TowerColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final ServerSocket serverSocket;
    private int serverPort;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final List<Lobby> lobbies= new ArrayList<>();

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(serverPort);
    }
    //it would be better to not have the whole method as siyncronized but only some part of it
    public synchronized void lobby(ClientConnection connection, String nickname){
        Lobby last = lobbies.get(lobbies.size()-1);
        if(last.getLobbyDimension()>last.numberOfConnections())
            last.addConnection(connection,nickname);
        if(last.getLobbyDimension()==last.numberOfConnections())
            last.startGame();
    }
    public synchronized boolean availableLobby(){
        Lobby last = lobbies.get(lobbies.size()-1);
        return (last.getLobbyDimension()>last.numberOfConnections());
    }
    public synchronized void createLobby(ClientConnection connection, String nick,int dimension, boolean easy){
        int newLobbyCode = lobbies.size();
        Lobby last = new Lobby(connection, newLobbyCode,easy,dimension, nick);
        lobbies.add(last);
    }

    public void run(){
        int connections = 0;
        System.out.println("Server is running");
        while(connections < 5){
            try {
                Socket newSocket = serverSocket.accept();
                connections++;
                System.out.println("Ready for the new connection - " + connections);
                ClientConnection socketConnection = new ClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
    }
}
