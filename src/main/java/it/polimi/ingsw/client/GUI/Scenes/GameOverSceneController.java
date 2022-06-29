package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.WinCondition;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedBoard;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.Arrays;

/**The handler of game over scene.*/
public class GameOverSceneController extends SceneController {
    public Text txt;
    private final CentralView view;
    public AnchorPane root;
    public Pane text_container;
    public Text reasonText;
    GUI gui;


    /**It builds the instance of the game over controller.*/
    public GameOverSceneController() {
        gui = GuiInputManager.getGui();
        view = gui.getGame();
    }

    @Override
    public void initialize() {
        setBackground();
        setWinner();
        root.setCursor(new ImageCursor(new Image("images/pointer/baseArrow.png")));
        root.setCursor(new ImageCursor(new Image("images/pointer/basePointer.png")));
        text_container.setOnMouseClicked(event -> gui.stop());
        text_container.setOnMouseEntered(event ->txt.setText("Click to close the game"));
        text_container.setOnMouseExited(event -> setWinner());
    }

    /**It sets the background of the winning scene.*/
    private void setBackground(){
        if(view.isTeamPlay()) {
            if(winnerTeamTowerColor()==0)
                root.setStyle("");//todo background blackTeam
            else root.setStyle("");//todo background white team

        }else {
            int studentForBack=winnerBestAllies(view.getPlayers().get(view.getWinner()));
            switch (studentForBack){
                case 0->{
                    root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_red.png) ; -fx-background-size: 830 552");
                    text_container.setTranslateX(-200);
                    text_container.setTranslateY(-150);
                }
                case 1 -> {
                    root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_yellow.png); -fx-background-size: 830 552");
                    text_container.setTranslateX(-80);
                    text_container.setTranslateY(-130);
                }
                case 2->{
                    root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_green.png); -fx-background-size: 830 552");
                    text_container.setTranslateX(80);
                    text_container.setTranslateY(-50);
                }

                case 3-> {
                    root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_blue.png); -fx-background-size: 830 552");
                    text_container.setTranslateX(-150);
                    text_container.setTranslateY(-50);
                }
                case 4->{
                    root.setStyle("-fx-background-image: url(images/wallpapers/gameOver_pink.png); -fx-background-size: 830 552");
                    text_container.setTranslateX(120);
                    text_container.setTranslateY(100);
                }
            }
        }
    }

    /**@return the tower color of the winner team.*/
    private int winnerTeamTowerColor(){
        if( view.getWinner()==view.getPlayers().get(0).getTeam())
            return view.getPlayers().get(0).getTowerColor();
        else return view.getPlayers().get(1).getTowerColor();
    }

    /**@param winner the simplified winner player
     * @return the index of the color the player has the maximum amount of tokens on board*/
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

    /**It sets winning messages on the scene.*/
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
        reasonText.setText(gameOverReason());
    }

    /**@return winning reason in string format*/
    private String gameOverReason(){
        WinCondition reason= view.getGameOverReason();

        return switch (reason){
            case BagEmpty->"Empty bag!";
            case NoTowersLeft->"Winner used every tower";
            case TenTurnsPassed->" out of assistants";
            case ThreeIslandsLeft-> "only 3 cluster of island left";
            default -> "";
        };

    }
}
