package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**The handler of the client connection. It exchanges string messages with the client through the socket.*/
public class ClientConnection extends Observable<String> implements Runnable{
    private final Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private boolean active ;
    private final Server server;

    /**It builds the handler.
     * @param clientSocket the socket of the client
     * @param server the reference to the central server*/
    public ClientConnection(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server=server;
        this.active=true;
    }

    /**@return •true: the connection is on
     * <p>•false: the connection is off</p>*/
    public synchronized boolean isActive() {
        return active;
    }

    @Override
    public void run() {
         try {
           this.in =new Scanner(clientSocket.getInputStream());
            this.out= new PrintWriter(clientSocket.getOutputStream());
            send("Welcome! What is your name?");
            String read = readFromSocket();
            String name = read.toUpperCase();
            if(server.availableLobby()){
                server.lobby(this, name);
            }else {
                send("no lobby available. Creating new lobby, number of players?");
                String nPlayer = readFromSocket();
                send("difficulty easy? y/n");
                String difficulty = readFromSocket();
                server.createLobby(this,name, Integer.parseInt(nPlayer), difficulty.toLowerCase());
            }
             send("connected to lobby");
            while(isActive()) {
                if (in.hasNextLine()) {
                    read = in.nextLine();
                    actOnMessage(read);
                }//fixme, this ensure correct unregistering in case of orderly disconnection, but could it bring any problem?


            }
        } catch (IOException | NoSuchElementException e) {
           // System.err.println(e.getMessage()+ "  ai!");
        }finally {
            close();
        }

    }

    /**It sends the message read. It eventually closes the connection.
     * @param read the message arrived from the socket*/
    private void actOnMessage(String read){
        if(read.equalsIgnoreCase("close_connection"))
            close();
        else notify(read);
    }

    /**It reads the string message from the socket.
     * @return the read message*/
    private synchronized String readFromSocket() {
        String result = "null";
        String read;
        while (isActive()) {
            if (in.hasNextLine()) {//fixme same as the other fixme
                read = in.nextLine();
                result = read;
                break;
            }
        }
        return result;
    }

    /**It sends an asynchronous message to the client in a dedicated thread
     * @param message the message to send*/
    public void asyncSend(final String message){
        new Thread(() -> send(message)).start();
    }

    /**It sends through the socket the string message.
     * @param s the string message to send*/
    private synchronized void send(String s) {
        try {
            out.println(s);
            out.flush();
        } catch(RuntimeException e){
            System.err.println(e.getMessage() +" ugh");
        }

    }

    /**It invokes the client connection closure.*/
    private void close(){
        System.out.println("Unregistering client...");
        closeConnection();
        System.out.println("Done!");
    }

    /**It closes the connection and notifies the client.*/
    public synchronized  void closeConnection(){
        send("Connection closed from Server");
        try {
            clientSocket.close();
            server.deregisterConnection(this);
        }catch (IOException exception){
            send(exception.getMessage());
            System.err.println(exception.getMessage());
        }
        active=false;
    }
}
