package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private final ServerSocket serverSocket;
    private int serverPort=12345;
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(128);
    private final List<Lobby> lobbies= new ArrayList<>();
    private int connections;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(12345);
        connections=0;
    }

    public synchronized void deregisterConnection(ClientConnection connection){
        for (Lobby lob:this.lobbies) {
            lob.removeFromLobby(connection);
            executor.remove(connection);
            connections--;
        }
    }

    public synchronized void lobby(ClientConnection connection, String nickname) throws IOException {
        Lobby last = lobbies.get(lobbies.size()-1);
        if(last.isPlayerConnected(nickname))
            throw new IOException("User not available, try to riconnect");
        if(last.getLobbyDimension()>last.numberOfConnections()) {
            last.addConnection(connection, nickname);
        }
        if(last.getLobbyDimension()==last.numberOfConnections())
            last.startGame();
    }

    public synchronized boolean availableLobby(){
        if(lobbies.isEmpty())
            return false;
        Lobby last = lobbies.get(lobbies.size()-1);
        return (last.getLobbyDimension()>last.numberOfConnections());
    }

    public synchronized void createLobby(ClientConnection connection, String nick,int dimension, String yesEasy) throws IOException {
        if(dimension<1|| dimension>4)//fixme but not so fast
            throw new IOException("min 2 players, max 4");
        boolean easy;
        easy= yesEasy.equals("y");
        int newLobbyCode = lobbies.size();
        Lobby last = new Lobby(connection, newLobbyCode,easy,dimension, nick);
        lobbies.add(last);
        if(last.getLobbyDimension()==last.numberOfConnections())
            last.startGame();
    }

    public void run(){
        System.out.println("Server is running");
        while(connections < 128){
            try {
                Socket newSocket = serverSocket.accept();
                connections++;
                System.out.println("Ready for the new connection - " + connections);
                ClientConnection socketConnection = new ClientConnection(newSocket, this);
                executor.execute(socketConnection);
               // executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println("Connection Error!");
            }
        }
        if(connections>=128){
            System.out.println("Too many players, the server has been stopped");
        }
    }

}
