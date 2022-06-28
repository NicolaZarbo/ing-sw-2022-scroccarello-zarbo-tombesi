package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.client.ServerConnection;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Shadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;

/**The handler of the opening scene of the game.*/
public class WelcomeSceneController extends SceneController {
    @FXML
    private AnchorPane root;
    public Pane connectionOptions;
    public Pane optionsContainer;
    public Text error_serverOptions;
    public TextField ip_server;
    public TextField port_server;
    private GUI gui;
    public TextField usernameBox;
    public Text errorMsg;
    public Pane mainpane;
    public Pane lobbymessage;
    private boolean customConnection;

    @Override
    public void initialize() {
        this.gui = GuiInputManager.getGui();
        root.setStyle("-fx-background-image:url(images/wallpapers/Eriantys.jpg); -fx-background-position: center; -fx-background-size: 1236 863");
        mainpane.setStyle("-fx-background-image:url(images/simple_elements/Username.png); -fx-background-position: center; -fx-background-size: 438 144; -fx-border-width: 2");
        usernameBox.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                startGame();
            }
        });
        connectionOptions.setOnMouseEntered(event -> {
            DropShadow test= new DropShadow();
            test.setColor(Color.RED);
            test.setBlurType(BlurType.THREE_PASS_BOX);
            connectionOptions.setEffect(test);
            connectionOptions.setOnMouseExited(event1 -> {
                connectionOptions.setEffect(null);
            });

        });
        Platform.runLater(()->{
            usernameBox.requestFocus();
        });
    }

    /**It starts the game by establishing the connection with the server. It sends first information such as the user's nickname and eventually notifies him that the connection has been failing or the username is unavailable. It contains a banner to modify connection options (IP and port).*/
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
                    mainpane.setDisable(true);
                    this.mainpane.setOpacity(0.5);
                    this.lobbymessage.setVisible(true);
                }

            }
            catch(RuntimeException e){
                errorMsg.setText("Username not available, please choose another one " + e.getMessage());
            }
        }
    }

    /**It verifies if connection parameters are admittible.*/
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
            if(port>65535 || port<49152){
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

    /**It activates the banner to insert connection options.*/
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
