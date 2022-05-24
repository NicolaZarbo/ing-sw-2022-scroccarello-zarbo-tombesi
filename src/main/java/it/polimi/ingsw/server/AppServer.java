package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Server;

import javax.imageio.IIOException;
import java.io.IOException;

public class AppServer
{
    public static void main( String[] args )
    {
        Server server;
            try {
                server= new Server();
                server.run();
            }catch (IOException exception){
                System.err.println( exception.getMessage()+ exception.getCause()+" ops" );
            }
    }
}
