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

    private int[] value;

    private ArrayList<Circle> character1stud;

    private ArrayList<Circle> character7stud;

    private ArrayList<Circle> character11stud;

    @Override
    public void initialize() {

    }

    private void initCharacters(){
        characters=new ArrayList<>();
        coins=new ArrayList<>();
        value=new int[8];
        for(int i=0;i<8;i++){
            characters.add((Rectangle)CharacterContainer.getChildren() );
            coins.add((Circle)coinImageContainer.getChildren() );
            value[i]=0;
            if(i==0) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/1_monk.png")));
            }else if(i==1) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/7_jester.png")));
            }else if(i==2) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/9_mushroom.png")));
            }else if(i==3) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/10_singer.png")));
            }else if(i==4) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/11_princess.png")));
            } else if(i==5) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/2_farmer.png")));
            }else if(i==6) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/8_knight.png")));
            }else {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/6_cent.png")));
            }
            coins.get(i).setFill(new ImagePattern(new Image("images/simple_elements/coin.png")));

        }



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
