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

import java.util.*;

/**The handler of player customization during setup phase.*/
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
    private List<ImageView> towers;
    private List<ImageView> mages;
    private final DropShadow islandShadow = new DropShadow(8, Color.DARKRED);

    /**It creates the base instance of setup controller.*/
    public SetupSceneController() {
    }

    @Override@FXML
    public void initialize() {
        this.gui= GuiInputManager.getGui();
        root.setStyle("-fx-background-image: url(images/wallpapers/LowerQualityLobby.png); -fx-background-size: 640 400 ;-fx-background-repeat: no-repeat; ");
        root.setCursor(new ImageCursor(new Image("images/pointer/basePointer.png")));
        choiceMsg.setText("Choose your color");
        towerchosen=0;
        wizardchosen=0;
        CentralView view = gui.getGame();
        availableTowers = view.getAvailableColor();
        availableMages = view.getAvailableMages();
        initClickable();
        if(view.isTeamPlay()) {
            teamWelcome();
            if(availableTowers.size()==0) {
                showMagicians();
                towerchosen=1;
            }
            else setTowers();
        }else
            setTowers();
    }

    /**It initializes as clickable all the elements which can be clicked.*/
    private void initClickable(){
        mages= new ArrayList<>();
        mages.add(mage1);
        mages.add(mage2);
        mages.add(mage3);
        mages.add(mage4);
        mages.forEach(magus->{magus.setVisible(false);
            setShadow(magus);
            magus.setMouseTransparent(true);});
        towers= new ArrayList<>();
        towers.add(blacktower);
        towers.add(whitetower);
        towers.add(greytower);
        towers.forEach(pillar->{
            pillar.setVisible(false);
            pillar.setMouseTransparent(true);
        });
    }

    /**It fills the tower elements.*/
    private void setTowers(){
        Image img=new Image("images/towers/black_tower.png");
        blacktower.setImage(img);
        img=new Image("images/towers/white_tower.png");
        whitetower.setImage(img);
        img=new Image("images/towers/grey_tower.png");
        greytower.setImage(img);
        for (Integer towerColor:availableTowers) {
            ImageView towerC= towers.get(towerColor);
            towerC.setVisible(true);
            towerC.setMouseTransparent(false);
            setShadow(towerC);
        }
    }

    /**It sets on mouse event shadow effect on a target element.
     * @param target the element to set the effect on*/
    private void setShadow(ImageView target){
        target.setOnMouseEntered(event -> target.setEffect(new DropShadow(4,Color.DARKRED)));
        target.setOnMouseExited(event1 -> target.setEffect(null));
    }


    /**It shows the welcome message.*/
    private void teamWelcome(){
        teamWelcome_text.setVisible(true);
        int team=1;
        if(gui.getGame().getPlayersWaitingInLobby()%2==0)
            team=2;
        teamWelcome_text.setText("Welcome to team "+team);
    }

    /**It shows the magicians of the game.*/
    private void showMagicians(){
        errorMsg.setText("");
        choiceMsg.setText("Choose a magician");
        towers.forEach(pillar->{pillar.setVisible(false);
            pillar.setMouseTransparent(true);});
        Image img=new Image("images/wizards/Wizard (1).png");
        mage1.setImage(img);
        img=new Image("images/wizards/Wizard (2).png");
        mage2.setImage(img);
        img=new Image("images/wizards/Wizard (3).png");
        mage3.setImage(img);
        img=new Image("images/wizards/Wizard (4).png");
        mage4.setImage(img);
        for (Integer mage:availableMages) {
            ImageView mageT=mages.get(mage);
            mageT.setVisible(true);
            mageT.setMouseTransparent(false);
        }
    }

    /**It adds double click event (as confirm) to the mouse event.
     * @param event the event to which attack the listener*/
    private void addDoubleClickConfirm(MouseEvent event){
        ((ImageView)event.getSource()).setOnMouseClicked(event1 -> {
            confirmChoice();
        });

    }
    /**It selects the mage 1 on click.*/
    @FXML
    public void setMage1(MouseEvent click){
        this.wizardchosen =1;
        restoreAllOpacities();
        mage1.setOpacity(0.5);

        addDoubleClickConfirm(click);
    }
    /**It selects the mage 2 on click.*/
    @FXML
    public void setMage2(MouseEvent click){
        this.wizardchosen =2;
        restoreAllOpacities();
        mage2.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    /**It selects the mage 3 on click.*/
    @FXML
    public void setMage3(MouseEvent click){
        this.wizardchosen =3;
        restoreAllOpacities();
        mage3.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    /**It selects the mage 4 on click.*/
    @FXML
    public void setMage4(MouseEvent click){
        this.wizardchosen =4;
        restoreAllOpacities();
        mage4.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    /**It selects the tower choice black on click.*/
    @FXML
    public void setBlackTower(MouseEvent click){
        this.towerchosen=1;
        restoreAllOpacities();
        blacktower.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }
    /**It selects the tower choice white on click.*/
    @FXML
    public void setWhiteTower(MouseEvent click){
        this.towerchosen=2;
        restoreAllOpacities();
        whitetower.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }

    /**It selects the tower choice gray on click.*/
    @FXML
    public void setGrayTower(MouseEvent click){
        this.towerchosen=3;
        restoreAllOpacities();
        greytower.setOpacity(0.5);
        addDoubleClickConfirm(click);
    }

    /**It restores all opacities to natural value of elements.*/
    private void restoreAllOpacities(){
        blacktower.setOpacity(1.0);
        whitetower.setOpacity(1.0);
        greytower.setOpacity(1.0);
        mage1.setOpacity(1.0);
        mage2.setOpacity(1.0);
        mage3.setOpacity(1.0);
        mage4.setOpacity(1.0);
    }

    /**It triggers the confirmation of the selection of the custom parameters of the player (it triggers the lobby-player creation).*/
    public void confirmChoice() {
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
            gui.getGame().choosePlayerCustom(towerchosen-1,wizardchosen-1);
            mainpane.setOpacity(0.5);
            mainpane.setDisable(true);
            waitingmsg.setVisible(true);
        }
    }

}
