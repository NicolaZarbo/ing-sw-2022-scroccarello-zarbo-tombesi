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
    private Label towerChoice;
    @FXML
    private Label choiceMsg;
    @FXML
    private Text errorMsg;


    public SetupSceneController() {
    }

    @Override@FXML
    public void initialize() {
        this.gui= GuiInputManager.getGui();
        choiceMsg.setText("Choose your color");

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

    }

    private void showMagicians(){
        errorMsg.setText("");
        choiceMsg.setText("Choose a magician");
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
        restoreAllOpacities();
        mage1.setOpacity(0.5);
    }
    @FXML
    public void setMage2(MouseEvent click){
        this.wizardchosen =2;
        restoreAllOpacities();
        mage2.setOpacity(0.5);
    }
    @FXML
    public void setMage3(MouseEvent click){
        this.wizardchosen =3;
        restoreAllOpacities();
        mage3.setOpacity(0.5);
    }
    @FXML
    public void setMage4(MouseEvent click){
        this.wizardchosen =4;
        restoreAllOpacities();
        mage4.setOpacity(0.5);
    }
    @FXML public void setBlackTower(MouseEvent click){
        this.towerchosen=1;
        restoreAllOpacities();
        blacktower.setOpacity(0.5);
    }
    @FXML public void setWhiteTower(MouseEvent click){
        this.towerchosen=2;
        restoreAllOpacities();
        whitetower.setOpacity(0.5);
    }
    @FXML public void setGrayTower(MouseEvent click){
        this.towerchosen=3;
        restoreAllOpacities();
        greytower.setOpacity(0.5);
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
        }
    }
}