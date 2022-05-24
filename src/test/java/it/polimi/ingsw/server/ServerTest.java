package it.polimi.ingsw.server;

import junit.framework.TestCase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest extends TestCase {
    private Server serverTest;

    public synchronized void setUp() throws Exception {
        super.setUp();
        this.serverTest=new Server();
    }

   /* public synchronized void testStressRun() {
    try{
        ServerThread thr=new ServerThread(this.serverTest);
        thr.start();
        int rng=(int)Math.random()*100;
        for(int i=1;i<200;i++){
            ClientConnection client=new ClientConnection(new Socket("127.0.0.".concat(String.valueOf(i)),12345),this.serverTest);
            assertTrue(client.isActive());
            ClientThread cthr=new ClientThread(client);
            cthr.start();
            try{
                if(serverTest.availableLobby()){
                    serverTest.lobby(client,String.valueOf(i));
                }
                else{
                    serverTest.createLobby(client,String.valueOf(i),i%5,"n");
                }
                if(i==rng){
                    serverTest.deregisterConnection(client);
                }
            }
            catch(IOException e){
                assertTrue(i%5<2 || i%5>4);
            }
        }
    }
    catch(IOException e){
        e.printStackTrace();
    }
    }


    private class ServerThread extends Thread{
        Server server;

        public ServerThread(Server s){
            super();
            this.server=s;
        }

        public void run(){
            server.run();
        }
    }

    private class ClientThread extends Thread{
        ClientConnection client;

        public ClientThread(ClientConnection cc){
            super();
            this.client=cc;
        }

        public void run(){
            client.run();
        }
    }
*/
}