package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import javax.imageio.IIOException;
import java.io.IOException;

/**
 * Hello world!
 *
 */
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
        System.out.println( "Hello World!" );
    }
}
