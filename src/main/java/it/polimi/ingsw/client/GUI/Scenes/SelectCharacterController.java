package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectCharacterController extends SceneController{

    public Pane CharacterContainer;

    public Pane studentCharacterContainer;

    public Pane coinImageContainer;

    public Pane valueCharacterContainer;

    private ArrayList<Rectangle> characters;

    private ArrayList<Circle> coins;

    private ArrayList<Text> value;



    private ArrayList<Circle> character1stud;

    private ArrayList<Circle> character7stud;

    private ArrayList<Circle> character11stud;

    GUI gui;

    private final CentralView view;
    public SelectCharacterController(){
        this.gui= GuiInputManager.getGui();
        this.view=gui.getGame();
    }
    @Override
    public void initialize() {

    }

    private void initCharacters(){
        characters=new ArrayList<>();
        coins=new ArrayList<>();
        value=new ArrayList<>();
        Map<Integer, Integer> costs=view.getCostOfCard();
        for(int i=0;i<8;i++){
            characters.add((Rectangle)CharacterContainer.getChildren() );
            coins.add((Circle)coinImageContainer.getChildren() );
            value.add((Text)valueCharacterContainer.getChildren() );
            if(i==0) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/1_monk.png")));
                value.get(i).setText(costs.get(1)+"");
            }else if(i==1) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/7_jester.png")));
                value.get(i).setText(costs.get(7)+"");

            }else if(i==2) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/9_mushroom.png")));
                value.get(i).setText(costs.get(9)+"");

            }else if(i==3) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/10_singer.png")));
                value.get(i).setText(costs.get(10)+"");

            }else if(i==4) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/11_princess.png")));
                value.get(i).setText(costs.get(11)+"");

            } else if(i==5) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/2_farmer.png")));
                value.get(i).setText(costs.get(2)+"");

            }else if(i==6) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/8_knight.png")));
                value.get(i).setText(costs.get(8)+"");

            }else {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/6_cent.png")));
                value.get(i).setText(costs.get(6)+"");

            }
            coins.get(i).setFill(new ImagePattern(new Image("images/simple_elements/coin.png")));

        }



    }



    private void initStudCharacters() {
        character1stud = new ArrayList<>();
        character7stud = new ArrayList<>();
        character11stud = new ArrayList<>();
        int id;
        Map<Integer, List<Integer>> studentsOnCard = view.getStudentsOnCard();
        for (int i = 0; i < 14; i++) {
            if (i < 4) {
                character1stud.add((Circle) studentCharacterContainer.getChildren());
                character1stud.get(i).setDisable(true);
                character1stud.get(i).setVisible(false);
                character1stud.get(i).setStyle("-fx-stroke-width: 0");
            } else if (i >= 4 && i < 10) {
                character7stud.add((Circle) studentCharacterContainer.getChildren());
                character7stud.get(i).setDisable(true);
                character7stud.get(i).setVisible(false);
                character7stud.get(i).setStyle("-fx-stroke-width: 0");
            } else {
                character11stud.add((Circle) studentCharacterContainer.getChildren());
                character11stud.get(i).setDisable(true);
                character11stud.get(i).setVisible(false);
                character11stud.get(i).setStyle("-fx-stroke-width: 0");
            }
        }
        for (int i = 0; i < studentsOnCard.size(); i++) {
            if (i == 0) {
                for (int j = 0; j < character1stud.size(); j++)
                    if (studentsOnCard.get(i).get(j) != null) {
                        Image img;
                        id = studentsOnCard.get(i).get(j);
                        if (id != -1) {

                            img = getStudentImage(id);
                            character1stud.get(i).setFill(new ImagePattern(img));
                            character1stud.get(i).setVisible(true);
                            character1stud.get(i).setDisable(false);

                        }

                    }


            }else if(i==1){
                for (int j = 0; j < character7stud.size(); j++)
                    if (studentsOnCard.get(i).get(j) != null) {
                        Image img;
                        id = studentsOnCard.get(i).get(j);
                        if (id != -1) {

                            img = getStudentImage(id);
                            character7stud.get(i).setFill(new ImagePattern(img));
                            character7stud.get(i).setVisible(true);
                            character7stud.get(i).setDisable(false);

                        }

                    }
            }else if(i==2){
                for (int j = 0; j < character7stud.size(); j++)
                    if (studentsOnCard.get(i).get(j) != null) {
                        Image img;
                        id = studentsOnCard.get(i).get(j);
                        if (id != -1) {

                            img = getStudentImage(id);
                            character7stud.get(i).setFill(new ImagePattern(img));
                            character7stud.get(i).setVisible(true);
                            character7stud.get(i).setDisable(false);

                        }

                    }

            }
        }
    }
    private Image getStudentImage(int idStudent){
        Image stud;
        switch(idStudent/26){
            case 0->{
                stud=new Image("images/students/student3d/red.png");
            }
            case 1 ->{
                stud=new Image("images/students/student3d/yellow.png");
            }
            case 2->{
                stud=new Image("images/students/student3d/green.png");

            }
            case 3->{
                stud=new Image("images/students/student3d/blue.png");
            }
            case 4->{
                stud=new Image("images/students/student3d/pink.png");
            }
            default -> {
                throw new NullPointerException("not an iD");
            }
        }
        return stud;
    }
}
