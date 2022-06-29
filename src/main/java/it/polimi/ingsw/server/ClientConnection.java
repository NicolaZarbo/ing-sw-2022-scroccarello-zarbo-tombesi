package it.polimi.ingsw.server;

import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**The handler of the client connection. It exchanges string messages with the client through the socket.*/
public class ClientConnection extends Observable<String> implements Runnable{
    private final Socket clientSocket;
    private PrintWriter out;
    private Scanner in;
    private InputStream inSocket;
    private boolean active ;
    private final Server server;
    Thread ponger;

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
    public synchronized void removeConnection(){
        active=false;
    }

    @Override
    public void run() {
        ponger=new Thread(this::pong);
        ponger.start();
         try {
             inSocket=clientSocket.getInputStream();
             clientSocket.setSoTimeout(1000*60);
             //this.in =new Scanner(clientSocket.getInputStream());
             this.out= new PrintWriter(clientSocket.getOutputStream());
            send("Welcome! What is your name?");
            String read = readFromSocket();
            String name = read;///
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
                    read = readFromSocket();
                    actOnMessage(read);
            }
        } catch (IOException | NoSuchElementException e) {
             close("socket exception");
        }catch (IllegalArgumentException ex){
             close(ex.getMessage());
        }
         close("closed by server");
    }
    private void pong(){
        while (active){
            try {
                Thread.sleep(30*1000);
                 send("pong");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**It sends the message read. It eventually closes the connection.
     * @param read the message arrived from the socket*/
    private void actOnMessage(String read){
        if(read.equalsIgnoreCase("close_connection"))
            close("disconnected from client");
        else notify(read);
    }

    /**It reads the string message from the socket.
     * @return the read message*/
    private  String readFromSocket() {
        String result ;
        String read = "ping";
        if (isActive()) {
                while(read.equalsIgnoreCase("ping")){
                    read = readSocketIn();
                    System.out.println("ping?"+read);//fixme remove  before deploy
                }
                result = read;
                return result;
        }
        throw new NoSuchElementException();
    }
    /** Used instead of the scanner for more control on timeout*/
    private String readSocketIn(){
        StringBuilder builder= new StringBuilder();
        try {
            int read=  inSocket.read();
            while((char)read!='\n'){
                builder.append((char)read);
                read= inSocket.read();
            }
            if(read==0 || read==-1 || ((char)read)==' ') {
                close("lost connection");
                return "";
            }
        }catch (SocketTimeoutException timeoutException){
            close("timed out");
            return "";
        } catch (IOException e) {
            close("connection error");
            return "";
        }
        String out=builder.toString();
        return out.substring(0, out.length() - 1);
    }
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
    private void close(String reason){
        if(!active)
            return;
        System.out.println("Unregistering client...");
        send("Connection closed from Server ("+reason+")");
        try {
            clientSocket.close();
            server.deregisterConnection(this,reason);
        }catch (IOException exception){
            send(exception.getMessage());
            System.err.println(exception.getMessage());
        }
        active=false;
        System.out.println("Done!");
    }

}
