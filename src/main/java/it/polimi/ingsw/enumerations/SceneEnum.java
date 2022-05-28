package it.polimi.ingsw.enumerations;

public enum SceneEnum {
    WelcomeScene("/WelcomeScene.fxml"), LobbyScene("/lobbyScene.fxml"), HandScene("/HandScene.fxml"), MapScene("/MapScene.fxml"), WizardScene("/WizardChoiceScene.fxml");

    private String path;

    SceneEnum(String s) {
        path=s;
    }

    public String  getPath(){
        return path;
    }
}
