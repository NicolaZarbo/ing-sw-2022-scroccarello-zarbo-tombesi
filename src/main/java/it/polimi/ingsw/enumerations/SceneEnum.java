package it.polimi.ingsw.enumerations;

public enum SceneEnum {
    WelcomeScene("/WelcomeScene.fxml"), LobbyScene("/LobbyScene.fxml"), HandScene("/HandScene.fxml"), MapScene("/MapScene.fxml"), SetupScene("/SetupScene.fxml");

    private String path;

    SceneEnum(String s) {
        path=s;
    }

    public String  getPath(){
        return path;
    }
}
