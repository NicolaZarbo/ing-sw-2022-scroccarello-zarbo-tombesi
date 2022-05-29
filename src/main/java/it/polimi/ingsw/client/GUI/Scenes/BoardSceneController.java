package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BoardSceneController extends SceneController{

    private final GUI gui;

    /**constructor which decides how many boards to display according to the number of players*/
    public BoardSceneController(GUI gui) {
        this.gui=gui;
        try {
            if(gui.getGame().getPlayers().size()==3){
                pane= FXMLLoader.load(getClass().getResource("/BoardScene3.fxml"));
                Scene scene=new Scene(pane);
            }
            else{
                pane= FXMLLoader.load(getClass().getResource("/BoardScene2.fxml"));
                Scene scene=new Scene(pane);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initialize();
    }

    @Override
    public void initialize() {
        /*Button startBtn= (Button) pane.lookup("#sendButton");
        startBtn.setOnAction(event -> {
            String name = ((TextField)pane.lookup("#usernameBox")).getText();
            gui.startConnection(new ByteArrayInputStream(name.getBytes()));
            System.out.println("oooooooooooooooooo");

        });*/
    }

    //metodo per settare tutti i cerchi, rettangoli e esagoni trasparenti poichè all'inizio la board è vuota
    public void setToStart(GUI gui){
    /*    if(gui.getGame().getPlayers().size()==3) {
            AnchorPane first = pane.lookup("#boardPlayer1");
            AnchorPane second = pane.lookup("#boardPlayer2");
            AnchorPane third = pane.lookup("#boardPlayer3");
            for(int i=1;i<10;i++) {
                first.lookup("#hall" + i).setFill("Color.TRANSPARENT");
                second.lookup("#hall" + i).setFill("Color.TRANSPARENT");
                third.lookup("#hall" + i).setFill("Color.TRANSPARENT");


            }

        }
*/
    }

}
