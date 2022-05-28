package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.io.File;

public class WizardSceneController extends SceneController{
    private GUI gui;
    private int chosen;

    @FXML
    private ImageView mage1;
    @FXML
    private ImageView mage2;
    @FXML
    private ImageView mage3;
    @FXML
    private ImageView mage4;
    @FXML
    private Text errorMsg;

    public WizardSceneController() {
    }

    @Override@FXML
    public void initialize() {
        this.gui= GuiInputManager.getGui();
        errorMsg.setVisible(false);
        Image img=new Image("images/wizards/Wizard (1).png");
        mage1.setImage(img);
        img=new Image("images/wizards/Wizard (2).png");
        mage2.setImage(img);
        img=new Image("images/wizards/Wizard (3).png");
        mage3.setImage(img);
        img=new Image("images/wizards/Wizard (4).png");
        mage4.setImage(img);

        chosen=0;
    }
    @FXML
    public void setMage1(MouseEvent click){
        this.chosen=1;
        System.out.println("clicked on "+chosen);
    }
    @FXML
    public void setMage2(MouseEvent click){
        this.chosen=2;
        System.out.println("clicked on "+chosen);
    }
    @FXML
    public void setMage3(MouseEvent click){
        this.chosen=3;
        System.out.println("clicked on "+chosen);
    }
    @FXML
    public void setMage4(MouseEvent click){
        this.chosen=4;
        System.out.println("clicked on "+chosen);
    }

    public void confirmChoice(MouseEvent mouseEvent) {
        if(chosen==0){
            errorMsg.setVisible(true);
        }
        else{

        }
    }
}
