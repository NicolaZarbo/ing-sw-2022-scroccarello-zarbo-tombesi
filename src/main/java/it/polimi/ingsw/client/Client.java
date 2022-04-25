package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {

    private String ip;
    private int port;
    private String username;

    public Client(String ip, int port, String username){
        this.ip = ip;
        this.port = port;
        this.username=username;
    }

    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        Scanner socketIn = new Scanner(socket.getInputStream());
        PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);
        String socketLine;
        try{
            socketLine = socketIn.nextLine();
            System.out.println(socketLine);
            while (true){
                String inputLine = stdin.nextLine();
                socketOut.println(inputLine);
                socketOut.flush();
                socketLine = socketIn.nextLine();
                System.out.println(socketLine);
            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed from the client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }

}
