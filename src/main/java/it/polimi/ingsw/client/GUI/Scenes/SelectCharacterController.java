package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private int activatingCard;
    private final GuiInputManager inputManager;
    private List<Integer> selectedStudents;

    private final CentralView view;
    public SelectCharacterController(){
        selectedStudents=new ArrayList<>();
        this.gui= GuiInputManager.getGui();
        this.view=gui.getGame();
        inputManager=gui.getInputManager();
    }
    @Override
    public void initialize() {
        initCharacters();
        initStudCharacters();
        if(!view.isYourTurn() || !( view.getState()== GameState.actionMoveStudent || view.getState()== GameState.actionMoveMother)) {
            CharacterContainer.setDisable(true);
            CharacterContainer.setOpacity(0.5);
        }
    }

    private void initCharacters(){
        characters=new ArrayList<>();
        coins=new ArrayList<>();
        value=new ArrayList<>();
        Map<Integer, Integer> costs=view.getCostOfCard();
        for(int i=0;i<8;i++){
            characters.add((Rectangle)CharacterContainer.getChildren().get(i) );
            coins.add((Circle)coinImageContainer.getChildren().get(i) );
            value.add((Text)valueCharacterContainer.getChildren().get(i) );
            DropShadow cardShadow = new DropShadow(3, Color.DARKRED);
            int finalI = i;
            if(view.isYourTurn() && !inputManager.isActivatingCardEffect()) {
                characters.get(i).setOnMouseEntered(event -> characters.get(finalI).setEffect(cardShadow));
                characters.get(i).setOnMouseExited(event -> characters.get(finalI).setEffect(null));
            }
            if(i==0) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/1_monk.png")));
                value.get(i).setText(costs.get(1)+"");
                characters.get(i).setOnMouseClicked(event -> effect1());
            }else if(i==1) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/7_jester.png")));
                value.get(i).setText(costs.get(7)+"");
                characters.get(i).setOnMouseClicked(event -> effect7());
            }else if(i==2) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/9_mushroom.png")));
                value.get(i).setText(costs.get(9)+"");
                characters.get(i).setOnMouseClicked(event -> effect9());
            }else if(i==3) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/10_singer.png")));
                value.get(i).setText(costs.get(10)+"");
                characters.get(i).setOnMouseClicked(event -> effect10());
            }else if(i==4) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/11_princess.png")));
                value.get(i).setText(costs.get(11)+"");
                characters.get(i).setOnMouseClicked(event -> effect11());
            } else if(i==5) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/2_farmer.png")));
                value.get(i).setText(costs.get(2)+"");
                characters.get(i).setOnMouseClicked(event -> noParameterEffect(2));
            }else if(i==6) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/8_knight.png")));
                characters.get(i).setOnMouseClicked(event -> noParameterEffect(8));
                value.get(i).setText(costs.get(8)+"");
            }else {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/6_cent.png")));
                characters.get(i).setOnMouseClicked(event -> noParameterEffect(6));
                value.get(i).setText(costs.get(6)+"");
            }
            coins.get(i).setFill(new ImagePattern(new Image("images/simple_elements/coin.png")));
        }
    }
    private void effect1(){
        activatingCard=1;
        character1stud.forEach(s->s.setDisable(false));
        studentCharacterContainer.setMouseTransparent(false);
        CharacterContainer.setMouseTransparent(true);
        inputManager.setCardEffectActivation();
    }
    private void effect7(){
        studentCharacterContainer.setMouseTransparent(false);
        CharacterContainer.setMouseTransparent(true);
        activatingCard=7;
        character7stud.forEach(s->s.setDisable(false));
        inputManager.setCardEffectActivation();
        //todo show board to select students after choosing those here
    }
    private void effect9(){
        //todo show some color for the player to choose
        int targetColor=-1;
        inputManager.useCharacter9(targetColor);
    }
    private void effect10(){
        //todo show the entrance and dining and have the player choose up to 2 studs
    }
    private void effect11() {
        studentCharacterContainer.setMouseTransparent(false);
        CharacterContainer.setMouseTransparent(true);
        activatingCard = 11;
        character11stud.forEach(s->s.setDisable(false));
        inputManager.setCardEffectActivation();
    }
    /** Used for card 2-6-8  */
    private void noParameterEffect(int number){
        inputManager.usaCharacterNoParameter(number);
    }



    private void initStudCharacters() {
        character1stud = new ArrayList<>();
        character7stud = new ArrayList<>();
        character11stud = new ArrayList<>();
        int id;
        Map<Integer, List<Integer>> studentsOnCard = view.getStudentsOnCard();
        for (int i = 0; i < 14; i++) {
            Circle stud =(Circle) studentCharacterContainer.getChildren().get(i);
            stud.setDisable(true);
            stud.setVisible(false);
            stud.setStyle("-fx-stroke-width: 0");
            if(i<4)
                character1stud.add(stud);
            else if(i<10)
                character7stud.add(stud);
            else character11stud.add(stud);
        }
        for (int i = 0; i < studentsOnCard.size(); i++) {
            List<Circle> studOfCardList=character1stud;
            List<Integer> viewStudOnCard=new ArrayList<>(studentsOnCard.get(1));
            if(i==1){
                studOfCardList=character7stud;
                viewStudOnCard=new ArrayList<>(studentsOnCard.get(7));
            }
            if(i==2){
                studOfCardList=character11stud;
                viewStudOnCard=new ArrayList<>(studentsOnCard.get(11));
            }
            for (int j = 0; j < studOfCardList.size(); j++)
                if (viewStudOnCard.get(j) != null) {
                    Image img;
                    id = viewStudOnCard.get(j);
                    if (id != -1) {
                        img = getImage(id);
                        studOfCardList.get(j).setFill(new ImagePattern(img));
                        studOfCardList.get(j).setVisible(true);
                        studOfCardList.get(j).setDisable(false);
                        int finalId = id;
                        studOfCardList.get(j).setOnMouseClicked(event -> clickSelectStudent(finalId));
                    }
                }
        }
    }
    private void clickSelectStudent(int id){
        if (activatingCard != 1 && activatingCard != 7 && activatingCard != 11) {
            return;
        }
        selectedStudents.add(id);
        if(activatingCard==1){
            inputManager.saveSelectedStud(selectedStudents.get(0));
            gui.showIslands();
        }
        if(activatingCard==7){

        }
        if(activatingCard==11){
            inputManager.useCharacter11(selectedStudents.get(0));
            goBack();
        }
    }


    static Image getImage(int idStudent) {
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

    public void goBack() {
        inputManager.resetEffectActivation();
        gui.showHand();
    }
}
