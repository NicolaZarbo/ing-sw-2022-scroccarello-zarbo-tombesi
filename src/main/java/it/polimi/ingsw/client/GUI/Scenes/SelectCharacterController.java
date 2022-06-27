package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import javafx.scene.Node;
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

/**The handler of character scene (expert mode only).*/
public class SelectCharacterController extends SceneController{

    public Pane CharacterContainer;
    public Pane studentCharacterContainer;
    public Pane coinImageContainer;
    public Pane valueCharacterContainer;


    public Pane cardContainer1;
    public Pane cardContainer7;
    public Pane cardContainer9;
    public Pane cardContainer10;
    public Pane cardContainer11;
    public Pane cardContainer2;
    public Pane cardContainer8;
    public Pane cardContainer6;


    public ArrayList<Pane> cardContainer;

    public Pane popupPanel;
    public Text ownedMoney;

    private ArrayList<Rectangle> characters;
    private ArrayList<Circle> coins;
    private ArrayList<Text> value;

    private ArrayList<Circle> character1stud;
    private ArrayList<Circle> character7stud;
    private ArrayList<Circle> character11stud;

    GUI gui;
    private int activatingCard;
    private final GuiInputManager inputManager;
    private ArrayList<Integer> selectedStudents;

    private final CentralView view;

    /**It builds the instance of character scene controller.*/
    public SelectCharacterController(){
        selectedStudents=new ArrayList<>();
        this.gui= GuiInputManager.getGui();
        this.view=gui.getGame();
        inputManager=gui.getInputManager();
    }

    @Override
    public void initialize() {
        cardContainer=new ArrayList<Pane>();
        cardContainer.add(cardContainer1);
        cardContainer.add(cardContainer7);
        cardContainer.add(cardContainer9);
        cardContainer.add(cardContainer10);
        cardContainer.add(cardContainer11);
        cardContainer.add(cardContainer2);
        cardContainer.add(cardContainer8);
        cardContainer.add(cardContainer6);
        initCharacters();
        initStudCharacters();
        ownedMoney.setText(view.getPersonalPlayer().getCoin()+" :");
        popupPanel.setVisible(false);
        popupPanel.setMouseTransparent(true);
        if(!view.isYourTurn() || !( view.getState()== GameState.actionMoveStudent || view.getState()== GameState.actionMoveMother)) {
             for(int i=0;i<cardContainer.size();i++)
                 cardContainer.get(i).getChildren().get(0).setDisable(true);

        }
    }

    /**It initializes every character element of the scene. Every element is given an image resource, an effect triggered on click, a description of the cost.*/
    private void initCharacters(){
        characters=new ArrayList<>();
        coins=new ArrayList<>();
        value=new ArrayList<>();
        Map<Integer, Integer> costs=view.getCostOfCard();
        for(int i=0;i<8;i++){
            characters.add((Rectangle)cardContainer.get(i).getChildren().get(0) );
            coins.add((Circle)cardContainer.get(i).getChildren().get(4) );
            value.add((Text)cardContainer.get(i).getChildren().get(6) );
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
                cardVisibility(1,characters.get(i));
            }else if(i==1) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/7_jester.png")));
                value.get(i).setText(costs.get(7)+"");
                characters.get(i).setOnMouseClicked(event -> effect7());
                cardVisibility(7,characters.get(i));
            }else if(i==2) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/9_mushroom.png")));
                value.get(i).setText(costs.get(9)+"");
                characters.get(i).setOnMouseClicked(event -> effect9());
                cardVisibility(9,characters.get(i));
            }else if(i==3) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/10_singer.png")));
                value.get(i).setText(costs.get(10)+"");
                characters.get(i).setOnMouseClicked(event -> effect10());
                cardVisibility(10,characters.get(i));
            }else if(i==4) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/11_princess.png")));
                value.get(i).setText(costs.get(11)+"");
                characters.get(i).setOnMouseClicked(event -> effect11());
                cardVisibility(11,characters.get(i));
            } else if(i==5) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/2_farmer.png")));
                value.get(i).setText(costs.get(2)+"");
                characters.get(i).setOnMouseClicked(event -> noParameterEffect(2));
                cardVisibility(2,characters.get(i));
            }else if(i==6) {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/8_knight.png")));
                characters.get(i).setOnMouseClicked(event -> noParameterEffect(8));
                value.get(i).setText(costs.get(8)+"");
                cardVisibility(8,characters.get(i));
            }else {
                characters.get(i).setFill(new ImagePattern(new Image("images/characters/6_cent.png")));
                characters.get(i).setOnMouseClicked(event -> noParameterEffect(6));
                value.get(i).setText(costs.get(6)+"");
                cardVisibility(6,characters.get(i));
            }
            coins.get(i).setFill(new ImagePattern(new Image("images/simple_elements/coin.png")));
        }
    }

    /**It sets the visibility of the card according to their cost and the possibility the user has to afford it.
     * @param cardNumber the id of the card
     * @param card the related element on the scene*/
    private void cardVisibility(int cardNumber, Rectangle card){
        if(view.getCostOfCard().get(cardNumber)>view.getPersonalPlayer().getCoin()) {
            card.setOpacity(0.5);
            card.setMouseTransparent(true);
        }
        else  {
            card.setOpacity(1);
            card.setMouseTransparent(false);
        }
    }

    /**It triggers effect of the card 1 on click.
     * @see it.polimi.ingsw.model.characters.Character1*/
    private void effect1(){
        activatingCard=1;
        character1stud.forEach(s->{
            s.setDisable(false);
            s.setOpacity(1);
        });
        studentCharacterContainer.setMouseTransparent(false);
        CharacterContainer.setMouseTransparent(true);
        inputManager.setCardEffectActivation();
    }

    /**It triggers effect of the card 7 on click.
     * @see it.polimi.ingsw.model.characters.Character7*/
    private void effect7(){
        studentCharacterContainer.setMouseTransparent(false);
        CharacterContainer.setMouseTransparent(true);
        activatingCard=7;
        character7stud.forEach(s->{
            s.setDisable(false);
            s.setOpacity(1);
        });
        inputManager.setCardEffectActivation();
        inputManager.setCardInActivation(7);
        showMoveButton();
        panelGoToBoard();
    }

    /**It shows the go to board on the popup panel-*/
    private void panelGoToBoard(){
        popupPanel.getChildren().forEach(pane->{
            pane.setVisible(false);
            pane.setMouseTransparent(true);
        });
        Node goToBoard=popupPanel.getChildren().get(0);
        goToBoard.setMouseTransparent(false);
        goToBoard.setVisible(true);
    }

    /**It shows the colors on the popup panel.*/
    private void panelColors(){
        popupPanel.getChildren().forEach(pane->{
            pane.setVisible(false);
            pane.setMouseTransparent(true);
        });
        Node colors=popupPanel.getChildren().get(1);
        colors.setVisible(true);
        colors.setMouseTransparent(false);
        colors.setTranslateY(-30);
    }

    /**It triggers effect of the card 9 on click.
     * @see it.polimi.ingsw.model.characters.Character9*/
    private void effect9(){
        int targetColor=-1;
        inputManager.useCharacter9(targetColor);
        showMoveButton();
        panelColors();
    }

    /**It triggers effect of the card 10 on click.
     * @see it.polimi.ingsw.model.characters.Character10*/
    private void effect10(){
        inputManager.setCardInActivation(10);
        inputManager.setCardEffectActivation();
        gui.showBoards();
        //todo show the entrance and dining and have the player choose up to 2 studs
    }

    /**It triggers effect of the card 11 on click.
     * @see it.polimi.ingsw.model.characters.Character11*/
    private void effect11() {
        studentCharacterContainer.setMouseTransparent(false);
        CharacterContainer.setMouseTransparent(true);
        activatingCard = 11;
        character11stud.forEach(s->{
            s.setDisable(false);
            s.setOpacity(1);
        });
        inputManager.setCardEffectActivation();
    }

    /**It triggers effect of the card 2, 6, 8 on click. No parameters are required.
     * @see it.polimi.ingsw.model.characters.TurnEffectCharacter*/
    private void noParameterEffect(int number){
        inputManager.usaCharacterNoParameter(number);
    }


    /**It initializes student tokens on token characters' cards.*/
    private void initStudCharacters() {
        character1stud = new ArrayList<>();
        character7stud = new ArrayList<>();
        character11stud = new ArrayList<>();
        int id;
        Map<Integer, List<Integer>> studentsOnCard = view.getStudentsOnCard();
        for (int i = 0; i < 14; i++) {
            Circle stud =(Circle) studentCharacterContainer.getChildren().get(i);
            stud.setStyle("-fx-stroke-width: 0");
            stud.setDisable(true);
            stud.setVisible(false);
            stud.setOpacity(0.4);
            stud.setOnMouseEntered(event ->  stud.setEffect(new DropShadow(3, Color.DARKRED)));
            stud.setOnMouseExited(event ->  stud.setEffect(null));
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
                        Circle stud=studOfCardList.get(j);
                        studOfCardList.get(j).setOnMouseClicked(event -> {
                            clickSelectStudent(finalId);
                            stud.setOpacity(0.2);
                        });
                    }
                }
        }
    }

    /**It triggers token selection.
     * @param id the selected id*/
    private void clickSelectStudent(int id){
        if (activatingCard != 1 && activatingCard != 7 && activatingCard != 11) {
            return;
        }
        selectedStudents.add(id);
        if(activatingCard==1){
            inputManager.saveSelectedStud(selectedStudents.get(0));
            gui.showIslands();
        }
        if(activatingCard==7 && inputManager.getNumberOfStudentSelectedFromCharacter()==3){
            moveToBoard();
        }
        if(activatingCard==11){
            inputManager.useCharacter11(selectedStudents.get(0));
            goBack();
        }
    }

    /**@param idStudent the id of the target student
     * @return relative image of the token colored student*/
    static Image getImage(int idStudent) {
        Image stud;
        switch(idStudent/26){
            case 0->stud=new Image("images/students/student3d/red.png");
            case 1 ->stud=new Image("images/students/student3d/yellow.png");
            case 2->stud=new Image("images/students/student3d/green.png");
            case 3->stud=new Image("images/students/student3d/blue.png");
            case 4->stud=new Image("images/students/student3d/pink.png");
            default -> {
                throw new NullPointerException("not an iD");
            }
        }
        return stud;
    }

    /**It changes the scene into <i>hand scene</i>>*/
    public void goBack() {
        inputManager.resetEffectActivation();
        activatingCard=0;
        gui.showHand();
    }

    /**It shows the move button.*/
    protected void showMoveButton() {
        popupPanel.setVisible(true);
        popupPanel.setMouseTransparent(false);
    }


    /**It hides the move button*/
    protected void hideMoveButtons() {
        popupPanel.setVisible(false);
        popupPanel.setMouseTransparent(true);
    }

    /**It moves students from the carc to the entrance.
     * @see it.polimi.ingsw.model.characters.Character7*/
    public void moveToBoard(){
        if(activatingCard==7 ){
            if(selectedStudents.size()>0)
                inputManager.saveSelectedStud(selectedStudents);
            else return;
        }
        gui.showBoards();
    }

    /**It sets as active nerfed color red.
     * @see it.polimi.ingsw.model.characters.Character9*/
    public void selectedRed(){
        inputManager.useCharacter9(0);
        hideMoveButtons();
    }
    /**It sets as active nerfed color pink.*/
    public void selectedPink(){
        inputManager.useCharacter9(5);
        hideMoveButtons();
    }
    /**It sets as active nerfed color yellow.*/
    public void selectedYellow(){
        inputManager.useCharacter9(1);
        hideMoveButtons();
    }
    /**It sets as active nerfed color blue.*/
    public void selectedBlue(){
        inputManager.useCharacter9(3);
        hideMoveButtons();
    }
    /**It sets as active nerfed color green.*/
    public void selectedGreen(){
        inputManager.useCharacter9(2);
        hideMoveButtons();
    }

}
