package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.printers.Printer;
import it.polimi.ingsw.client.ServerConnection;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.util.Scanner;

/**The class to run the Command Line Interface app.*/
public class AppCli {

    /**It runs the Command Line Interface app.*/
    public static void main(String[] args){
        Cli client= new Cli();
        try{
            AnsiConsole.systemInstall();// if on Intellij add "-Djansi.passthrough=true" to VM run configuration of appCli fixme run config
            if(args.length == 2) {
                ServerConnection.ip = args[0];
                ServerConnection.port = Integer.parseInt(args[1]);
            }else {
                System.out.println("No connection info given\n  Insert ip now "+Printer.CYAN+" OR"+Printer.RST+" just press enter to connect to localhost");
                String input = new Scanner(System.in).nextLine();
                if(!input.equalsIgnoreCase("")) {
                    System.out.println("insert the port");
                    ServerConnection.port = Integer.parseInt(new Scanner(System.in).nextLine());
                }
            }
            client.run();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }catch (IllegalArgumentException err){
            System.out.println(Printer.RED+"no connection "+err.getMessage()+Printer.RST);
        }
        finally {
            AnsiConsole.systemUninstall();//since jlink has some problems with this library, comment its usage in
                                            //this class when deploying the javafx application with maven
        }
    }
}