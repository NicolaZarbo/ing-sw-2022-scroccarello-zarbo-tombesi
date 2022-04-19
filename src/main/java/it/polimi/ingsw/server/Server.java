package it.polimi.ingsw.server;

import it.polimi.ingsw.model.LobbyPlayer;
import it.polimi.ingsw.model.Mage;
import it.polimi.ingsw.model.token.TowerColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private int serverPort;
    private ExecutorService executor = Executors.newFixedThreadPool(128);
    private List<Lobby> lobbies= new ArrayList<>();




    public Server() throws IOException {
        this.serverSocket = new ServerSocket(serverPort);
    }
    public synchronized void lobby(ClientConnection connection, String nickname){
        Lobby last = lobbies.get(lobbies.size()-1);
        if(last.getLobbyDimension()>last.numberOfConnections())
            last.addConnection(connection);
        else {
            connection.asyncSend("how many player? ");
            int lobDim = 2;//receives player number for game
            connection.asyncSend("easy rules? Y/N ");
            boolean easyRules=true;
            lobbies.add(new Lobby(connection, last.getLobbyCode() + 1, lobDim, easyRules));

        }
        connection.asyncSend("Choose tower color ");
        connection.asyncSend("Choose mage ");
        LobbyPlayer prePlayer = new LobbyPlayer(TowerColor.black, Mage.mage1,"giorgioByMoroder");
        if(last.getLobbyDimension()==last.numberOfConnections())
            last.startGame();

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
