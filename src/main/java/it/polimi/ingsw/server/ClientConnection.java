package it.polimi.ingsw.server;

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
    private  int lobbyCode;

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
            String name = read;
            server.lobby(this, name);

            while(isActive()) {
                read = in.nextLine();
                notify(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void asyncSend(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                send(message);
            }
        }).start();
    }

    private void send(String s) {
        try {
            out.println(s);
            out.flush();
        } catch(RuntimeException e){
            System.err.println(e.getMessage());
        }

    }
}
