package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;


public class WelcomeSceneController extends SceneController {
    private GUI gui;
    @FXML
    private TextField usernameBox;
    @FXML
    private Text errorMsg;

    public void initialize(){
        this.gui= GuiInputManager.getGui();
        this.errorMsg.setVisible(false);
    }
    @FXML
    public void startGame(){
        String usr=usernameBox.getText();
        System.out.println(usr);
        if(usr==""){
            this.errorMsg.setVisible(true);
        }
        else{
            gui.startConnection(new ByteArrayInputStream(usr.getBytes()));
        }
    }
}
