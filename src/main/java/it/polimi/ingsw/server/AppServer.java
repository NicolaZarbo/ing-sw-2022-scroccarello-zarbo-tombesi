package it.polimi.ingsw.server;


import java.io.IOException;

/**The class to run the server app.*/
public class AppServer
{
    /**It runs the server app.*/
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
            } catch (IllegalArgumentException err){
                System.out.println(err.getMessage()+" \n Please insert a correct port Number");
            }
    }
}
