package it.polimi.ingsw.server;


import java.io.IOException;

public class AppServer
{
    public static void main( String[] args )
    {
        Server server;
            try {
                if(args.length==1) {
                    Server.serverPort = Integer.parseInt(args[0]);
                }
                server= new Server();
                server.run();
            }catch (IOException exception){
                System.err.println( exception.getMessage()+ exception.getCause()+" ops" );
            }
    }
}
