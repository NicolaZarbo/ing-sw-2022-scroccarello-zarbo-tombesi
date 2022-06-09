package it.polimi.ingsw.enumerations;

public enum SceneEnum {
    WelcomeScene("/WelcomeScene.fxml"),
    LobbyScene("/LobbyScene.fxml"),
    SetupScene("/SetupScene.fxml"),
    BoardScene2("/BoardScene2.fxml"),
    BoardScene3("/BoardScene3.fxml"),
    BoardScene4("/BoardScene4.fxml"),
    HandScene("/HandScene.fxml"),
    MapScene("/MapScene.fxml"),
    BoardSceneX("/boardSceneX.fxml"),
    GameOverScene("/GameOver.fxml");

    private String path;

    SceneEnum(String sourcePath) {
        path=sourcePath;
    }

    public String  getPath(){
        return path;
    }
}
