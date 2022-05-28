package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class SetupSceneController extends SceneController{
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
    private Text errorWizard;
    @FXML
    private Text errorTower;
    @FXML
    private Label wizardChoice;
    @FXML
    private Label towerChoice;


    public SetupSceneController() {
    }

    @Override@FXML
    public void initialize() {
        this.gui= GuiInputManager.getGui();
        errorTower.setVisible(false);
        errorWizard.setVisible(false);

        towerchosen=0;
        wizardchosen =0;

        Image img=new Image("images/towers/black_tower.png");
        blacktower.setImage(img);
        img=new Image("images/towers/white_tower.png");
        whitetower.setImage(img);
        /*if(gui.getGame().getPlayers().size()==3){
            img=new Image("images/towers/grey_tower.png");
            greytower.setImage(img);
        }*/
        img=new Image("images/towers/grey_tower.png");
        greytower.setImage(img);
        //just for temporary testing
        wizardChoice.setVisible(false);
    }

    private void showMagicians(){
        errorTower.setVisible(false);
        errorWizard.setVisible(false);
        blacktower.setImage(null);
        whitetower.setImage(null);
        greytower.setImage(null);

        Image img=new Image("images/wizards/Wizard (1).png");
        mage1.setImage(img);
        img=new Image("images/wizards/Wizard (2).png");
        mage2.setImage(img);
        img=new Image("images/wizards/Wizard (3).png");
        mage3.setImage(img);
        img=new Image("images/wizards/Wizard (4).png");
        mage4.setImage(img);
    }
    @FXML
    public void setMage1(MouseEvent click){
        this.wizardchosen =1;
        System.out.println("clicked on "+ wizardchosen);
    }
    @FXML
    public void setMage2(MouseEvent click){
        this.wizardchosen =2;
        System.out.println("clicked on "+ wizardchosen);
    }
    @FXML
    public void setMage3(MouseEvent click){
        this.wizardchosen =3;
        System.out.println("clicked on "+ wizardchosen);
    }
    @FXML
    public void setMage4(MouseEvent click){
        this.wizardchosen =4;
        System.out.println("clicked on "+ wizardchosen);
    }
    @FXML public void setBlackTower(MouseEvent click){
        this.towerchosen=1;
    }
    @FXML public void setWhiteTower(MouseEvent click){
        this.towerchosen=2;
    }
    @FXML public void setGrayTower(MouseEvent click){
        this.towerchosen=3;
    }
    public void confirmChoice(MouseEvent mouseEvent) {
        if(towerchosen == 0){
            errorTower.setVisible(true);
            return;
        }
        else{
            towerChoice.setVisible(false);
            wizardChoice.setVisible(true);
            showMagicians();
        }
        if(wizardchosen ==0){
            errorWizard.setVisible(true);
            return;
        }
        else{
            //TODO it should bring the player to the board page, still working on how to implement the scene
        }
    }
}
