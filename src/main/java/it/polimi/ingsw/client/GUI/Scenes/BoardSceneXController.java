package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.view.CentralView;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

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
    public Text context_text;
    public Pane context_container;
    private final GUI gui;
    private final CentralView view;
    private List<Pane> boardsContainers;
    public BoardSceneXController() {
        this.gui= GuiInputManager.getGui();
        view=gui.getGame();
    }

    @Override
    public void initialize() {
        root.setStyle("-fx-background-image:url(images/wallpapers/sky_no_title.png); -fx-background-position: center; -fx-background-size: 1280 796");
        root.setCursor(new ImageCursor(new Image("images/pointer/baseArrow.png")));
        setContextText();
        boardsContainers = new ArrayList<>();
        boardsContainers.add(up_left);
        boardsContainers.add(up_right);
        boardsContainers.add(down_left);
        boardsContainers.add(down_right);
        setDisposition();
        Pane setter;
        try {
            for (counterBoard=0; counterBoard < view.getPlayers().size(); counterBoard++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/singleBoard.fxml"));
                setter=loader.load();
                boardsContainers.get(counterBoard).getChildren().add(setter);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setContextText(){
        String text;
        if(view.isYourTurn()) {
            switch (view.getState()) {
                case actionChooseCloud -> text = "Go to map to choose a cloud";
                case actionMoveStudent -> text = "Move a student in entrance by selecting it";
                case actionMoveMother -> text = "Go to Map to move Mother Nature";
                case planPlayCard -> text = "Go to hand to choose a card";
                default -> text = "this are the boards";
            }
            if(gui.getInputManager().isActivatingCardEffect() && gui.getInputManager().getCardInActivation()==7)
                text="Select some students to exchange to the jester";
            if(gui.getInputManager().isActivatingCardEffect() && gui.getInputManager().getCardInActivation()==10)
                text="Select up to 2 student from both entrance and dining room";
            if(gui.getInputManager().isActivatingCardEffect())
                setCancelActivationOnExit();

            }else text=view.getTurnPlayer()+" is now playing";
        context_text.setText(text);
    }
    private void setCancelActivationOnExit(){
        moveToMap.setOnMouseClicked(event -> {
            gui.getInputManager().resetEffectActivation();
            goToMap();});
        moveToHand.setOnMouseClicked(event -> {
            gui.getInputManager().resetEffectActivation();
            goToHand();});
    }

    /**It sets the displacement of the board scene based on the number of players. */
    private void setDisposition(){
        switch (view.getPlayers().size()){

            case 2->disposition2();
            case 3 ->disposition3();
            default -> disposition4();
        }
    }

    /**It is the board disposition of a two players' game.*/
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

        context_container.setTranslateY(-540);
        context_container.setTranslateX(120);

    }
    private void disposition4(){
        for (Pane container: boardsContainers) {
            container.setScaleX(0.7);
            container.setScaleY(0.7);
        }
        int personal=view.getPersonalPlayer().getId();
        boardsContainers.get(personal).setScaleX(1.3);
        boardsContainers.get(personal).setScaleY(1.3);

    }



    /**It sets the disposition of a three players' game.*/
    private void disposition3(){
        Pane personal= boardsContainers.get(view.getPlayers().lastIndexOf(view.getPersonalPlayer()));
        up_left.setTranslateX(280);
        up_right.setTranslateY(330);
        down_left.setTranslateY(56);
        for (Pane container: boardsContainers) {
            container.setScaleX(0.7);
            container.setScaleY(0.7);
        }
        personal.setScaleX(1.4);
        personal.setScaleY(1.4);
        context_container.setTranslateY(-600);
        context_container.setTranslateX(190);
        down_right.setVisible(false);

    }

    /**It changes the scene into <i>map scene</i>>.*/
    public void goToMap() {
        gui.showIslands();
    }

    /**It changes the scene into <i>hand scene</i>>*/
    public void goToHand() {
        gui.showHand();
    }
}
