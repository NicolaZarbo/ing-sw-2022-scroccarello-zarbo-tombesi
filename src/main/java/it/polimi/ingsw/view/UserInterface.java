package it.polimi.ingsw.view;

public interface UserInterface {
    /** shows on the screen the islands, clouds and boards*/
    public void showView();
    /** shows to the player his coins and AssistantCards*/
    public void showHand();
    /** shows the charactersCard, their effect and cost*/
    public void showCharacters();
    /** shows which tower colors and mages are available during the creation of the player*/
    public void showOptionsForPersonalization();
}
