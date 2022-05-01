package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.client.Client;

import java.io.IOException;

public class AppCli {
    public static void main(String[] args){
        Cli client= new Cli();
        try{
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
