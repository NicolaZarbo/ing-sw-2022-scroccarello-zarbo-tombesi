package it.polimi.ingsw.enumerations;

/**The enumeration of the scenes of the GUI. Each of them is related to a specific <i>.fxml</i> file.*/
public enum SceneEnum {
    /**The opening scene of the game. The player has to submit a unique username and eventually has to change <i>IP</i> and <i>port</i> of the server.*/
    WelcomeScene("/WelcomeScene.fxml"),

    /**The joining lobby scene. If the lobby is not available, the player has to customize and create it.*/
    LobbyScene("/LobbyScene.fxml"),

    /**The setup scene. The player has to choose its custom parameters such as the tower color and the magician.*/
    SetupScene("/SetupScene.fxml"),

    /**The hand of the player scene. It shows the player his available cards, his discarded pile and his coins.*/
    HandScene("/HandScene.fxml"),

    /**The scene of the map of the game. It contains the islands and the clouds of the game, with the relative tokens on them.*/
    MapScene("/MapScene.fxml"),

    /**The board of the player scene. It shows to the player his board with its tokens (student, professor and remaining towers).*/
    BoardSceneX("/boardSceneX.fxml"),

    /**The character scene (expert mode only). It allows the player to activate the effect of a character card, if he has enough money.*/
    CharacterScene("/SelectCharacter.fxml"),

    /**The last scene of the game. It alerts the players that the game is over and shows the name of the winner.*/
    GameOverScene("/GameOver.fxml");

    private final String path;

    /**It fills the scene with the relative path.
     * @param sourcePath the absolute path to the <i>.fxml</i> file*/
    SceneEnum(String sourcePath) {
        path=sourcePath;
    }

    /**@return the path of the <i>.fxml</i> relative file of the scene*/
    public String  getPath(){
        return path;
    }
}
