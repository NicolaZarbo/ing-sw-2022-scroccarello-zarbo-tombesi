package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.view.CentralView;
import javafx.fxml.FXML;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class SetupSceneController extends SceneController{
    public VBox root;
    public Text teamWelcome_text;
    private GUI gui;
    private int wizardchosen, towerchosen;
    @FXML
    private ImageView mage1;
    @FXML
    private ImageView mage2;
    @FXML
    private ImageView mage3;
    @FXML
    private ImageView mage4;
    @FXML
    private ImageView blacktower;
    @FXML
    private ImageView whitetower;
    @FXML
    private ImageView greytower;
    @FXML
    private Label choiceMsg;
    @FXML
    private Text errorMsg;
    @FXML
    private Text waitingmsg;
    @FXML
    private Pane mainpane;

    private List<Integer> availableMages;
    private List<Integer> availableTowers;
    private final DropShadow islandShadow = new DropShadow(8, Color.DARKRED);

    public SetupSceneController() {
    }

    @Override@FXML
    public void initialize() {
        this.gui= GuiInputManager.getGui();
        root.setStyle("-fx-background-image: url(images/wallpapers/LowerQualityLobby.png); -fx-background-size: 640 400 ;-fx-background-repeat: no-repeat; ");
        root.setCursor(new ImageCursor(new Image("images/pointer/basePointer.png")));
        choiceMsg.setText("Choose your color");

        towerchosen=0;
        wizardchosen =0;
        if(gui.getGame().isTeamPlay())
            teamWelcome();
        Image img=new Image("images/towers/black_tower.png");
        blacktower.setImage(img);
        img=new Image("images/towers/white_tower.png");
        whitetower.setImage(img);
        img=new Image("images/towers/grey_tower.png");
        greytower.setImage(img);
        CentralView view = gui.getGame();
        availableTowers = view.getAvailableColor();
        availableMages = view.getAvailableMages();
        blacktower.setVisible(false);
        whitetower.setVisible(false);
        greytower.setVisible(false);
        mage1.setVisible(false);
        setShadow(mage1);
        mage1.setMouseTransparent(true);
        mage2.setVisible(false);
        setShadow(mage2);
        mage2.setMouseTransparent(true);
        mage3.setVisible(false);
        setShadow(mage3);
        mage3.setMouseTransparent(true);
        mage4.setVisible(false);
        setShadow(mage4);
        mage4.setMouseTransparent(true);
        setTowers();
    }
    private void setTowers(){
        for (Integer towerColor:availableTowers) {
            ImageView towerC=blacktower;
            if(towerColor==0)
                towerC= blacktower;
            if(towerColor==1)
                towerC=whitetower;
            if(towerColor==2)
                towerC=greytower;
            towerC.setVisible(true);
            towerC.setMouseTransparent(false);
            setShadow(towerC);
        }
    }

    private void setShadow(ImageView target){
        target.setOnMouseEntered(event -> target.setEffect(new DropShadow(4,Color.DARKRED)));
        target.setOnMouseExited(event1 -> target.setEffect(null));
    }
    private void teamWelcome(){
        teamWelcome_text.setVisible(true);
        int team=1;
        if(gui.getGame().getPlayersWaitingInLobby()%2==0)
            team=2;
        teamWelcome_text.setText("Welcome to team "+team);
    }

    private void showMagicians(){
        errorMsg.setText("");
        choiceMsg.setText("Choose a magician");
        blacktower.setVisible(false);
        blacktower.setMouseTransparent(true);
        whitetower.setVisible(false);
        whitetower.setMouseTransparent(true);
        greytower.setVisible(false);
        greytower.setMouseTransparent(true);
        Image img=new Image("images/wizards/Wizard (1).png");
        mage1.setImage(img);
        img=new Image("images/wizards/Wizard (2).png");
        mage2.setImage(img);
        img=new Image("images/wizards/Wizard (3).png");
        mage3.setImage(img);
        img=new Image("images/wizards/Wizard (4).png");
        mage4.setImage(img);
        for (Integer mage:availableMages) {
            ImageView mageT=mage1;
            if(mage==0)
                mageT=mage1;
            if(mage==1)
                mageT=mage2;
            if(mage==2)
                mageT=mage3;
            if(mage==3)
                mageT=mage4;
            mageT.setVisible(true);
            mageT.setMouseTransparent(false);
        }
    }
    private void addDoubleClickConfirm(MouseEvent event){
        ((ImageView)event.getSource()).setOnMouseClicked(event1 -> {
            confirmChoice(event1);
        });

    }
    @FXML
    public void setMage1(MouseEvent click){
        this.wizardchosen =1;
        restoreAllOpacities();
        mage1.setOpacity(0.5);

        addDoubleClickConfirm(click);
    }
    @FXML
    public void setMage2(MouseEvent click){
        this.wizardchosen =2;
        restoreAllOpacities();
        mage2.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    @FXML
    public void setMage3(MouseEvent click){
        this.wizardchosen =3;
        restoreAllOpacities();
        mage3.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    @FXML
    public void setMage4(MouseEvent click){
        this.wizardchosen =4;
        restoreAllOpacities();
        mage4.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    @FXML public void setBlackTower(MouseEvent click){
        this.towerchosen=1;
        restoreAllOpacities();
        blacktower.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    @FXML public void setWhiteTower(MouseEvent click){
        this.towerchosen=2;
        restoreAllOpacities();
        whitetower.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    @FXML public void setGrayTower(MouseEvent click){
        this.towerchosen=3;
        restoreAllOpacities();
        greytower.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    private void restoreAllOpacities(){
        blacktower.setOpacity(1.0);
        whitetower.setOpacity(1.0);
        greytower.setOpacity(1.0);
        mage1.setOpacity(1.0);
        mage2.setOpacity(1.0);
        mage3.setOpacity(1.0);
        mage4.setOpacity(1.0);
    }
    public void confirmChoice(MouseEvent mouseEvent) {
        if(towerchosen==0){
            errorMsg.setText("Please choose your color");
        }
        else if(mage1.getImage()==null){
            showMagicians();
        }
        else if(wizardchosen==0){
            errorMsg.setText("Please choose a magician");
        }
        else{
            System.out.println("lmao");
            gui.getGame().choosePlayerCustom(towerchosen-1,wizardchosen-1);
            mainpane.setOpacity(0.5);
            waitingmsg.setVisible(true);
        }
    }

}
