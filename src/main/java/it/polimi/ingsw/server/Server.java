package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**The central node which manages all the lobbies, and relative players and games, in different threads.*/
public class Server {
    private final ServerSocket serverSocket;
    public static int serverPort=50000;
    private final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(128);
    private final List<Lobby> lobbies= new ArrayList<>();
    private int connections;

    /**It creates the server.
     * @exception IOException if an I/O error occurs
     * @exception IllegalArgumentException if the port number is not acceptable*/
    public Server() throws IOException, IllegalArgumentException {
        if(!isGoodPort(serverPort))
        {
            throw new IllegalArgumentException("bad port number");
        }
        this.serverSocket = new ServerSocket(serverPort);
        serverSocket.setSoTimeout(10*60*1000);
        connections=0;
    }

    /**It states if the port number is acceptable or not.
     *@return •true: the port is acceptable
     * <p>•false: the port number is less than 49152 or greater than 65535</p>*/
    private boolean isGoodPort(int port){
        return port > 49152 && port<65535;
    }

    /**It removes the specific client connection from the lobby.
     * @param connection the client connection to remove*/
    public synchronized void deregisterConnection(ClientConnection connection, String reason){
        for (Lobby lob:this.lobbies) {

            if(lob.getConnections().contains(connection)) {
                lob.removeFromLobby(connection, reason);
                connections--;
                lobbies.remove(lob);
                System.out.println("Lobby closed");
                lob.getConnections().forEach(executor::remove);
                return;
            }
        }
    }


    /**It adds the player to the first available lobby,
     * @param connection the client connection to add
     * @param nickname the nickname chosen by the client
     *                 */
    public synchronized void lobby(ClientConnection connection, String nickname)  {
        Lobby last = lobbies.get(lobbies.size()-1);
        if(last.isPlayerConnected(nickname))
            throw new IllegalArgumentException("Username not available, try to reconnect");
        if(last.getLobbyDimension()>last.numberOfConnections()) {
            last.addConnection(connection, nickname);
        }
        if(last.getLobbyDimension()==last.numberOfConnections())
            last.startGame();
    }

    /**It states if there is an available lobby or not.
     * @return •true: there is one opened lobby
     * <p>•false: there are no lobbies to join (no one created or every lobby is full)</p>*/
    public synchronized boolean availableLobby(){
        if(lobbies.isEmpty())
            return false;
        Lobby last = lobbies.get(lobbies.size()-1);
        return (last.getLobbyDimension()>last.numberOfConnections());
    }

    /**It creates a new lobby according to the settings chosen by the client.
     * @param connection the client connection
     * @param dimension the number of players to fill the lobby
     * @param nick the nickname of the creator (first) player
     * @param yesEasy the indication that the game is in easy mode or in expert mode */
    public synchronized void createLobby(ClientConnection connection, String nick,int dimension, String yesEasy) throws IOException {
        if(dimension<2|| dimension>4)
            throw new IOException("min 2 players, max 4");
        boolean easy;
        easy= yesEasy.equals("y");
        int newLobbyCode = lobbies.size();
        Lobby last = new Lobby(connection, newLobbyCode,easy,dimension, nick);
        lobbies.add(last);
        if(last.getLobbyDimension()==last.numberOfConnections())
            last.startGame();
    }

    /**It starts the server on the network and prepares it to receive new connections.*/
    public void run(){
        System.out.println("Server is running");
        while(connections < 128){
            try {
                Socket newSocket = serverSocket.accept();
                connections++;
                System.out.println("Ready for the new connection - " + connections);
                ClientConnection socketConnection = new ClientConnection(newSocket, this);
                executor.execute(socketConnection);

            } catch (IOException e) {
                System.out.println("Connection Error!"+e.getMessage());
            }
        }
        System.out.println("Too many players, the server has been stopped");
    }

}
