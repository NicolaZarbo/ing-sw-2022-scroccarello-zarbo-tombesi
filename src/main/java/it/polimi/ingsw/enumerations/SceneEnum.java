package it.polimi.ingsw.enumerations;

public enum SceneEnum {
    WelcomeScene("/WelcomeScene.fxml"),
    LobbyScene("/LobbyScene.fxml"),
    SetupScene("/SetupScene.fxml"),
    HandScene("/HandScene.fxml"),
    MapScene("/MapScene.fxml"),
    BoardSceneX("/boardSceneX.fxml"),
    CharacterScene("/SelectCharacter.fxml"),
    GameOverScene("/GameOver.fxml");

    private String path;

    SceneEnum(String sourcePath) {
        path=sourcePath;
    }

    public String  getPath(){
        return path;
    }
}
