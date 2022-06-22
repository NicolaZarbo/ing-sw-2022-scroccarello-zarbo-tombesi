package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;

/**The interface implemented by user interacting classes.*/
public interface UserInterface {
    /** It shows or updates on the screen the islands, clouds and boards.*/
    void showView();

    /** It shows to the player his hand (coins and assistant cards).*/
    void showHand();

    /**It shows the characters cards, their effect and costs (expert mode only).*/
    void showCharacters();

    /**It shows which tower colors and mages are available during the creation of the player in the lobby.
     * @param message the message containing custom parameters of the player*/
    void showOptionsForPersonalization(PlayerSetUpMessage message);

    /**It shows an error received as json string.
     * @param errorMessage the string message of error*/
    void showError(String errorMessage);

    /** It notifies the player that he must move a student token.*/
    void askToMoveStudent();

    /**It asks the player to move mother nature of a maximum amount of steps (according to the assistant played in the current round).*/
    void askToMoveMother();
    /**It shows the clouds or focuses on them.*/
    void showClouds();

    /**It shows the boards of the players.*/
    void showBoards();

    /**It shows the remaining islands with their tokens and towers.*/
    void showIslands();

    /**It is used to end the game, it shows everyone the winner.*/
    void gameOver();
}
