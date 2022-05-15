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


    public synchronized void deregisterConnection(ClientConnection connection){
        for (Lobby lob:this.lobbies) {
            lob.getConnections().remove(connection);
            executor.remove(connection);
            connections--;
        }
    }
    public Server() throws IOException {
        this.serverSocket = new ServerSocket(12345);
        connections=0;
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
        if(lobbies.isEmpty())
            return false;
        Lobby last = lobbies.get(lobbies.size()-1);
        return (last.getLobbyDimension()>last.numberOfConnections());
    }
    public synchronized void createLobby(ClientConnection connection, String nick,int dimension, String yesEasy){
        boolean easy;
        if(yesEasy.equals("y"))
            easy=true;
        else easy= false;
        int newLobbyCode = lobbies.size();
        Lobby last = new Lobby(connection, newLobbyCode,easy,dimension, nick);
        lobbies.add(last);
        if(last.getLobbyDimension()==last.numberOfConnections())//this is used for fast testing FIXME
            last.startGame();
    }

    public void run(){
        System.out.println("Server is running");
        while(connections < 120){
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
    }
}
