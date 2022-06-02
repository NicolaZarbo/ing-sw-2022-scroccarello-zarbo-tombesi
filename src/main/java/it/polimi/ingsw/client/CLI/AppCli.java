package it.polimi.ingsw.client.CLI;

import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;

public class AppCli {
    public static void main(String[] args){
        Cli client= new Cli();
        try{
            AnsiConsole.systemInstall();
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
        finally {
            AnsiConsole.systemUninstall();
        }
    }
}
