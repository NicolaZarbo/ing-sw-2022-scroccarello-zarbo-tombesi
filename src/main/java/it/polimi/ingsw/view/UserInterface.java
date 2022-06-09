package it.polimi.ingsw.view;

import it.polimi.ingsw.messages.servermessages.PlayerSetUpMessage;

public interface UserInterface {
    /** shows or updates on the screen the islands, clouds and boards*/
    void showView();
    /** shows to the player his coins and AssistantCards*/
    void showHand();
    /** shows the charactersCard, their effect and cost*/
    void showCharacters();
    /** shows which tower colors and mages are available during the creation of the player*/
    void showOptionsForPersonalization(PlayerSetUpMessage message);
    /** shows an error which arrived from the client*/
    void showError(String errorMessage);
    /** tell's the player they have to move a student*/
    void askToMoveStudent();
    /** prompts the request of the number of steps*/
    void askToMoveMother();
    /** shows / focuses on the clouds*/
    void showClouds();
    void showBoards();
    void showIslands();
    /** used to end a game, it should show everyone who won*/
    void gameOver();
}
