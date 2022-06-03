package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.view.CentralView;
import javafx.scene.text.Text;

public class GameOverSceneController extends SceneController {
    public Text txt;
    private final CentralView view;


    public GameOverSceneController() {
        GUI gui = GuiInputManager.getGui();
        view = gui.getGame();
    }

    @Override
    public void initialize() {
        String text;
        if(view.isTeamPlay()){
            if(view.getWinner()==1)
                text="Team 1 won!";
            else text="Team 2 won!";
        }else {
            if(view.getWinner()==view.getPersonalPlayer().getId())
                text="You Won, Congratulations!";
            else text="player : "+view.getPlayers().get(view.getWinner()).getUsername()+" Won!";
        }
        txt.setText(text);
    }
}
