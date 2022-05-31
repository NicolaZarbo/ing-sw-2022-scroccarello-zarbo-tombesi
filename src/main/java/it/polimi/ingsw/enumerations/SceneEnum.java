package it.polimi.ingsw.enumerations;

public enum SceneEnum {
    WelcomeScene("/WelcomeScene.fxml"),
    LobbyScene("/LobbyScene.fxml"),
    SetupScene("/SetupScene.fxml"),
    BoardScene2("/BoardScene2.fxml"),
    BoardScene3("/BoardScene3.fxml"),
    BoardScene4("/BoardScene4.fxml"),
    HandScene("/HandScene.fxml"),
    MapScene("/MapScene.fxml");

    private String path;

    SceneEnum(String s) {
        path=s;
    }

    public String  getPath(){
        return path;
    }
}
