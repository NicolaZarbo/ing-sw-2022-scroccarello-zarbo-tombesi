package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.view.CentralView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardSceneXController extends SceneController{

    public Pane up_left;
    public Pane down_left;
    public Pane up_right;
    public Pane down_right;
    public static int counterBoard;
    public Button moveToMap;
    public Button moveToHand;
    public AnchorPane root;
    private GUI gui;
    private CentralView view;
    public BoardSceneXController() {
        this.gui= GuiInputManager.getGui();
        view=gui.getGame();
    }

    @Override
    public void initialize() {
        root.setStyle("-fx-background-image:url(images/wallpapers/sky_no_title.png); -fx-background-position: center; -fx-background-size: 1280 796");
        List<Pane> containers= new ArrayList<>();
        containers.add(up_left);
        containers.add(up_right);
        containers.add(down_left);
        containers.add(down_right);
        setDisposition();
        Pane setter;
        try {
            for (counterBoard=0; counterBoard < view.getPlayers().size(); counterBoard++) {
                setter=FXMLLoader.load(getClass().getResource("/singleBoard.fxml"));
                containers.get(counterBoard).getChildren().add(setter);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setDisposition(){
        switch (view.getPlayers().size()){
            case 1->disposition1();
            case 2->disposition2();
            case 3 ->disposition3();
            default -> disposition4();
        }
    }
    private void disposition2(){
        up_left.setTranslateX(280);
        up_right.setTranslateX(-280);
        up_right.setTranslateY(330);
        if(view.getPlayers().get(0)==view.getPersonalPlayer()) {
            up_left.setScaleX(1.5);
            up_left.setScaleY(1.5);
        }else{
            up_right.setScaleX(1.5);
            up_right.setScaleY(1.5);
        }
        down_left.setVisible(false);
        down_right.setVisible(false);
    }
    private void disposition4(){

    }
    private void disposition1(){
        up_left.setScaleX(2);
        up_left.setScaleY(2);
        up_left.setTranslateX(280);
        up_left.setTranslateY(100);
        down_left.setVisible(false);
        down_right.setVisible(false);
        up_right.setVisible(false);
    }
    private void disposition3(){
        Pane personal=containerList.get(view.getPlayers().lastIndexOf(view.getPersonalPlayer()));
        up_left.setTranslateX(280);
        up_right.setTranslateY(330);
        down_left.setTranslateY(56);
        for (Pane container:containerList) {
            container.setScaleX(0.7);
            container.setScaleY(0.7);
        }
        personal.setScaleX(1.4);
        personal.setScaleY(1.4);


        down_right.setVisible(false);
    }


    public void goToMap() {
        gui.showIslands();
    }

    public void goToHand() {
        gui.showHand();
    }
}
