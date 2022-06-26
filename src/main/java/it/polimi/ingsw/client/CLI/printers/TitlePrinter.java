package it.polimi.ingsw.client.CLI.printers;

/**The class which prints the game title.*/
public class TitlePrinter implements Printer{
    /**It prints the game title on the screen.*/
    public static String print(){
        return """
                
                \t\t\t\t    ▄████████    ▄████████  ▄█     ▄████████ ███▄▄▄▄       ███      ▄█     ▄████████\s
                \t\t\t\t   ███    ███   ███    ███ ███    ███    ███ ███▀▀▀██▄ ▀█████████▄ ███    ███    ███\s
                \t\t\t\t   ███    █▀    ███    ███ ███▌   ███    ███ ███   ███    ▀███▀▀██ ███▌   ███    █▀ \s
                \t\t\t\t  ▄███▄▄▄      ▄███▄▄▄▄██▀ ███▌   ███    ███ ███   ███     ███   ▀ ███▌   ███       \s
                \t\t\t\t ▀▀███▀▀▀     ▀▀███▀▀▀▀▀   ███▌ ▀███████████ ███   ███     ███     ███▌ ▀███████████\s
                \t\t\t\t   ███    █▄  ▀███████████ ███    ███    ███ ███   ███     ███     ███           ███\s
                \t\t\t\t   ███    ███   ███    ███ ███    ███    ███ ███   ███     ███     ███     ▄█    ███\s
                \t\t\t\t   ██████████   ███    ███ █▀     ███    █▀   ▀█   █▀     ▄████▀   █▀    ▄████████▀ \s
                \t\t\t\t                ███    ███                                                          \s
                
                """;
    }

}
