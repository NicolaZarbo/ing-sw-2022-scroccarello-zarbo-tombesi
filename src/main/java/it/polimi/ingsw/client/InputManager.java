package it.polimi.ingsw.client;

public abstract class InputManager {
    /** called only during your turn, used to activate a method based on the user input*/
    public abstract void decodeInput();
    /** used to show to the user a text sent by the server, used during lobby creation to ask the players name and other info*/
    public abstract void printToScreen(String string);
}
