package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.LobbySetupMessage;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection extends Observable<String> implements Runnable{
    private Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private boolean active ;
    private Server server;
   // private  int lobbyCode;

    public ClientConnection(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server=server;
        this.active=true;
    }

    public synchronized boolean isActive() {
        return active;
    }

    @Override
    public void run() {
        try {
            this.in =new Scanner(clientSocket.getInputStream());
            this.out= new PrintWriter(clientSocket.getOutputStream());
            send("Welcome!\nWhat is your name?");
            String read = in.nextLine();
            String name = read.toUpperCase();
            if(server.availableLobby()){
                server.lobby(this, name);
            }else {
                send("no lobby available\nCreating new lobby, number of player?");
                String nPlayer = in.nextLine();
                send("difficulty easy? y/n");
                String difficulty = in.nextLine();
                server.createLobby(this,name, Integer.parseInt(nPlayer), Boolean.parseBoolean(difficulty));
            }

            while(isActive()) {
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }finally {
            close();
        }

    }
    public void asyncSend(final String message){
        new Thread(() -> send(message)).start();
    }

    private synchronized void send(String s) {
        try {
            out.println(s);
            out.flush();
        } catch(RuntimeException e){
            System.err.println(e.getMessage());
        }

    }
    private void close(){
        closeConnection();
        System.out.println("Unregistering client...");
        server.deregisterConnection(this);
        System.out.println("Done!");
    }
    public synchronized  void closeConnection(){
        send("Connection closed from Server");
        try {
            clientSocket.close();
        }catch (IOException exception){
            send(exception.getMessage());
            System.err.println(exception.getMessage());
        }
        active=false;
    }
}
