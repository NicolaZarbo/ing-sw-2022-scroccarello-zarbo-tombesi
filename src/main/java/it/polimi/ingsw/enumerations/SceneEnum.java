package it.polimi.ingsw.enumerations;

public enum SceneEnum {
    FirstScene("/usernameScene.fxml"), LobbyScene("/lobbyScene.fxml"), HandScene("/HandScene.fxml"), MapScene("/MapScene.fxml");

    private String path;

    SceneEnum(String s) {
        path=s;
    }

    public String  getPath(){
        return path;
    }
}
