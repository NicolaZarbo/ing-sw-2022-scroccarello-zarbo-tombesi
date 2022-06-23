package it.polimi.ingsw.server;

import it.polimi.ingsw.exceptions.TimeOutConnectionException;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
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
        new Thread(this::pong).start();
         try {
           this.in =new Scanner(clientSocket.getInputStream());
            this.out= new PrintWriter(clientSocket.getOutputStream());
            //clientSocket.setSoTimeout(1000*60);
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
                }

            }
        } catch (IOException | NoSuchElementException e) {
           // System.err.println(e.getMessage()+ "  ai!");
        }
         finally {
            close();
        }

    }
    private void pong(){
        while (true){
            try {
                Thread.sleep(20*1000);
                 send("pong");
                 System.out.println("pong");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
        String result ;
        String read = "ping";
        if (isActive()) {
            if (in.hasNextLine()) {
                while(read.equalsIgnoreCase("ping"))
                    read = in.nextLine();
                result = read;
                return result;
            }
        }
        throw new NoSuchElementException();
    }
    /** Used instead of the scanner for more control on timeout*/
    private String readSocketIn(){
        StringBuilder builder= new StringBuilder();
        int read=2;
        try {
            read=  clientSocket.getInputStream().read();
            while((char)read!='\n'){
                builder.append((char)read);
                read= clientSocket.getInputStream().read();
            }
        }catch (SocketTimeoutException timeoutException){
            throw new TimeOutConnectionException();
        } catch (IOException e) {//fixme not a good catch
            System.out.println(e.getMessage());
        }
        if(read==0 || read==-1) {
            close();
            throw new TimeOutConnectionException();
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
    private void close(){
        if(!active)
            return;
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
