package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.ServerConnection;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;

/**The class to run the Command Line Interface app.*/
public class AppCli {

    /**It runs the Command Line Interface app.*/
    public static void main(String[] args){
        Cli client= new Cli();
        try{
            if(args.length == 2) {
                ServerConnection.ip = args[0];
                ServerConnection.port = Integer.parseInt(args[1]);
            }
            AnsiConsole.systemInstall();// if on Intellij add "-Djansi.passthrough=true" to VM run configuration of appCli fixme run config
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        finally {
            AnsiConsole.systemUninstall();//since jlink has some problems with this library, comment its usage in
                                            //this class when deploying the javafx application with maven
        }
    }
}