package it.polimi.ingsw.client;

/**The general handler of client input. It is used to decode the user input or to show some information received.*/
public abstract class InputManager {

    /**It is called only during your turn; it is used to activate a method based on the user input.*/
    public abstract void decodeInput();

    /**It is used to show to the user the information sent by the server; it is used during lobby creation to ask the players username and other info.
     * @param string the message to print to screen*/
    public abstract void printToScreen(String string);
}
