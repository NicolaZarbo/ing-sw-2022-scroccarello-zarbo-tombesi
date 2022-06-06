package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.client.ServerConnection;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;


public class WelcomeSceneController extends SceneController {
    public Pane connectionOptions;
    public Pane optionsContainer;
    public Text error_serverOptions;
    public TextField ip_server;
    public TextField port_server;
    private GUI gui;
    @FXML
    private TextField usernameBox;
    @FXML
    private Text errorMsg;
    @FXML
    private Pane mainpane;
    @FXML
    private Pane lobbymessage;
    private boolean customConnection;

    public void initialize() {
        this.gui = GuiInputManager.getGui();
        usernameBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                startGame();
            }
        });
        Platform.runLater(()->{
            usernameBox.requestFocus();
        });
    }
    @FXML
    public void startGame(){
        if(customConnection ){
            if(!checkConnectionOptions())
                return;
            ServerConnection.ip=ip_server.getText();
            ServerConnection.port=Integer.parseInt(port_server.getText());
        }
        String usr=usernameBox.getText();
        System.out.println(usr);
        if(usr.equals("")){
            this.errorMsg.setText("Please insert the username");
        }
        else{
            try{
                gui.startConnection(new ByteArrayInputStream(usr.getBytes()));
                if(GuiInputManager.isLobbyAvailable()){
                    this.mainpane.setOpacity(0.5);
                    this.lobbymessage.setVisible(true);
                }

            }
            catch(RuntimeException e){
                errorMsg.setText("Username not available, please choose another one " + e.getMessage());
            }
        }
    }
    private boolean checkConnectionOptions(){
        String[] ipv4St=ip_server.getText().split("\\.",4);
        int[] ipv4= new int[4];
        for (int i = 0; i < 4; i++) {
            try {
                ipv4[i]=Integer.parseInt(ipv4St[i]);
                if( ipv4[i]<0 || ipv4[i]>254)
                {
                    error_serverOptions.setVisible(true);
                    error_serverOptions.setText("bad ip format");
                    return false;
                }
            }catch (NumberFormatException e){
                error_serverOptions.setVisible(true);
                error_serverOptions.setText("bad ip format");
                return false;
            }
        }
        try {
            int port =Integer.parseInt( port_server.getText());
            if(port<65535 && port>49152){
                error_serverOptions.setVisible(true);
                error_serverOptions.setText("bad port");
                return false;
            }

        }catch (NumberFormatException e){
            error_serverOptions.setVisible(true);
            error_serverOptions.setText("bad port");
            return false;
        }
        return true;
    }

    public void openOptions() {
        connectionOptions.setOnMouseClicked(new EventHandler<>() {
            /** double click closes pane*/
            public void handle(MouseEvent event) {
                customConnection=false;
                optionsContainer.setVisible(false);
                optionsContainer.setDisable(true);
                connectionOptions.removeEventHandler(MouseEvent.MOUSE_CLICKED,this);
                connectionOptions.setOnMouseClicked(mouseEvent ->openOptions());
            }
        });
        customConnection=true;
        optionsContainer.setVisible(true);
        optionsContainer.setDisable(false);

    }
}
