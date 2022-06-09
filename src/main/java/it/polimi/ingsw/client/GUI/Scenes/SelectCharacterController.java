package it.polimi.ingsw.client.GUI.Scenes;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class SelectCharacterController extends SceneController{

    public Pane general;

    private ArrayList<Rectangle> characters;

    private ArrayList<Circle> character1stud;

    private ArrayList<Circle> character7stud;

    private ArrayList<Circle> character11stud;

    @Override
    public void initialize() {

    }

    public void initCharacters(){
        characters=new ArrayList<>();
            ArrayList<Rectangle> chars=new ArrayList<>();
    }
}
