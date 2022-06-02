package it.polimi.ingsw.client.CLI;

import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;

public class AppCli {
    public static void main(String[] args){
        Cli client= new Cli();
        try{
            AnsiConsole.systemInstall();// if on Intellij add "-Djansi.passthrough=true" to VM run configuration of appCli fixme run config
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        finally {
            AnsiConsole.systemUninstall();
        }
    }
}