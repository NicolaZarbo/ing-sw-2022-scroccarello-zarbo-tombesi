package it.polimi.ingsw.client.GUI.Scenes;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class SelectCharacterController extends SceneController{

    public Pane CharacterContainer;

    public Pane studentCharacterContainer;

    public Pane coinImageContainer;

    public Pane valueCharacterContainer;

    private ArrayList<Rectangle> characters;

    private ArrayList<Circle> coins;

    private ArrayList<Circle> character1stud;

    private ArrayList<Circle> character7stud;

    private ArrayList<Circle> character11stud;

    @Override
    public void initialize() {

    }

    private void initCharacters(){
        characters=new ArrayList<>();
        coins=new ArrayList<>();
        for(int i=0;i<8;i++){
            characters.add((Rectangle)CharacterContainer.getChildren() );
            coins.add((Circle)coinImageContainer.getChildren() );

            coinPlace.setFill(new ImagePattern(new Image("images/simple_elements/coin.png")));
            charactercost.setText(view.getPersonalPlayer().getCoin()+"");
        }
        for(int i=0;i<coins.size();i++)
            coins.get(i).setFill(new ImagePattern(new Image("images/simple_elements/coin.png")));

    }



    private void initStudCharacters(){
        character1stud=new ArrayList<>();
        character7stud=new ArrayList<>();
        character11stud=new ArrayList<>();
        for (int i=0;i<14;i++) {
            if(i<4) {
                character1stud.add((Circle) studentCharacterContainer.getChildren());
            }else if(i>=4 && i<10){
                character7stud.add((Circle) studentCharacterContainer.getChildren());
            }else{
                character11stud.add((Circle) studentCharacterContainer.getChildren());
            }
        }
    }
}
