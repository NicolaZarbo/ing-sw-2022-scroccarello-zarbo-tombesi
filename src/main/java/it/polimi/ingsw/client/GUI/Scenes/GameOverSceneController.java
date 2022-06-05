package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedBoard;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Arrays;

public class GameOverSceneController extends SceneController {
    public Text txt;
    private final CentralView view;
    public AnchorPane root;


    public GameOverSceneController() {
        GUI gui = GuiInputManager.getGui();
        view = gui.getGame();
    }

    @Override
    public void initialize() {
       setWinner();
    }
    private void setBackground(){
        if(view.isTeamPlay()) {
            if(winnerTeamTowerColor()==0)
                root.setStyle("");//todo background blackTeam
            else root.setStyle("");//todo background white team

        }else {
            int studentForBack=winnerBestAllies(view.getPlayers().get(view.getWinner()));
            switch (studentForBack){
                case 0->root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_red.png)");
                case 1->root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_yellow.png)");
                case 2->root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_green.png)");
                case 3->root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_blue.png)");
                case 4->root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_pink.png)");
            }
        }
    }
    private int winnerTeamTowerColor(){
        if( view.getWinner()==view.getPlayers().get(0).getTeam())
            return view.getPlayers().get(0).getTowerColor();
        else return view.getPlayers().get(1).getTowerColor();
    }
    private int winnerBestAllies(SimplifiedPlayer winner){
        SimplifiedBoard winnerBoard= winner.getBoard();
        int[] colorStudents= new int[5];
        Arrays.fill(colorStudents,0);
        for (int i = 0; i < 5; i++) {
            for (Integer stud:winnerBoard.getDiningRoom()[i]) {
                if(stud!=-1)
                    colorStudents[i]++;
            }
        }
        int max = colorStudents[0];
        int index = 0;
        for (int i = 0; i < colorStudents.length; i++)
        {
            if (max < colorStudents[i])
            {
                max = colorStudents[i];
                index = i;
            }
        }
        return index;

    }
    private void setWinner() {
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
