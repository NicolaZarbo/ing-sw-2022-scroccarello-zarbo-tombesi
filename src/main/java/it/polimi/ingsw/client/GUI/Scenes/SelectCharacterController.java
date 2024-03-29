package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**The handler of character scene (expert mode only).*/
public class SelectCharacterController extends SceneController{

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

    private List<Integer> charactersPlayable;
    private ArrayList<Circle> coins;
    private ArrayList<Text> value;

    private ArrayList<Circle> character1stud;
    private ArrayList<Circle> character7stud;
    private ArrayList<Circle> character11stud;

    final GUI gui;
    private int activatingCard;
    private final GuiInputManager inputManager;
    private ArrayList<Integer> selectedStudents;

    private final CentralView view;

    /**It builds the instance of character scene controller.*/
    public SelectCharacterController(){
        this.gui= GuiInputManager.getGui();
        this.view=gui.getGame();
        inputManager=gui.getInputManager();
    }

    @Override
    public void initialize() {
        cardContainer=new ArrayList<>();
        selectedStudents =new ArrayList<>();
        charactersPlayable=new ArrayList<>();
        cardContainer.add(cardContainer1);
        cardContainer.add(cardContainer7);
        cardContainer.add(cardContainer9);
        cardContainer.add(cardContainer10);
        cardContainer.add(cardContainer11);
        cardContainer.add(cardContainer2);
        cardContainer.add(cardContainer8);
        cardContainer.add(cardContainer6);
        for (Pane item : cardContainer) {
            item.setVisible(false);
            item.setMouseTransparent(true);
        }
        charactersPlayable=view.getCharacters();
        initCharacters();
        initStudCharacters();
        ownedMoney.setText(view.getPersonalPlayer().getCoin()+" :");
        popupPanel.setVisible(false);
        popupPanel.setMouseTransparent(true);
        if(!view.isYourTurn() || !( view.getState()== GameState.actionMoveStudent || view.getState()== GameState.actionMoveMother)) {
            for (Pane item : cardContainer)
                item.getChildren().get(1).setDisable(true);

        }
    }
    private Image characterImage(int characterNumber){
        return new Image( switch (characterNumber){
            case 1-> "images/characters/1_monk.png";
            case 2-> "images/characters/2_farmer.png";
            case 6-> "images/characters/6_cent.png";
            case 7-> "images/characters/7_jester.png";
            case 8-> "images/characters/8_knight.png";
            case 9-> "images/characters/9_mushroom.png";
            case 11-> "images/characters/11_princess.png";
            case 10-> "images/characters/10_singer.png";
            default -> throw new IllegalStateException("Unexpected value: " + characterNumber);
        });
    }
    private void setEventToCharacter(Rectangle character, int number){
        switch (number){
            case 1-> character.setOnMouseClicked(event->effect1());
            case 2-> character.setOnMouseClicked(event->noParameterEffect(2));
            case 6-> character.setOnMouseClicked(event->noParameterEffect(6));
            case 7-> character.setOnMouseClicked(event->effect7());
            case 8-> character.setOnMouseClicked(event->noParameterEffect(8));
            case 9-> character.setOnMouseClicked(event->effect9());
            case 11-> character.setOnMouseClicked(event->effect11());
            case 10-> character.setOnMouseClicked(event->effect10());
        }
    }
    /**It initializes every character element of the scene. Every element is given an image resource, an effect triggered on click, a description of the cost.*/
    private void initCharacters(){
        characters=new ArrayList<>();
        coins=new ArrayList<>();
        value=new ArrayList<>();
        Map<Integer, Integer> cardNumberToPositionInPane=new HashMap<>(Map.of(1,0,2,5,6,7,7,1,8,6,9,2,10,3,11,4));
        int shown=0;
        for (Integer card: charactersPlayable) {
            int positionInScene=cardNumberToPositionInPane.get(card);
            Pane cardPane=cardContainer.get(positionInScene);
            cardPane.setVisible(true);
            cardPane.setMouseTransparent(false);
            setCharacterPosition(cardPane,shown);
            
            Rectangle rectangleCard=(Rectangle)cardPane.getChildren().get(1);
            setRectangleOfCharacter(rectangleCard,card);
            setCoinCostOfCharacter(cardPane,card);
            shown++;
        }


    }
    private void setCharacterPosition(Pane cardPane,int pos){
        cardPane.setLayoutY(200);
        int xPosition= 75+pos*300;
        cardPane.setLayoutX(xPosition);
        cardPane.setScaleX(1.2);
        cardPane.setScaleY(1.2);
    }
    private void setRectangleOfCharacter(Rectangle characterRectangle, int cardNumber){
        characterRectangle.setFill(new ImagePattern(characterImage(cardNumber)));
        setEventToCharacter(characterRectangle, cardNumber);
        characters.add(characterRectangle);
        cardVisibility(cardNumber,characterRectangle);
        int finalI = characters.size()-1;
        DropShadow cardShadow = new DropShadow(3, Color.DARKRED);
        if (view.isYourTurn() && !inputManager.isActivatingCardEffect()) {
            characters.get(characters.size()-1).setOnMouseEntered(event -> characters.get(finalI).setEffect(cardShadow));
            characters.get(characters.size()-1).setOnMouseExited(event -> characters.get(finalI).setEffect(null));
        }
    }
    private void setCoinCostOfCharacter(Pane cardContainer, int cardNumber){
        coins.add((Circle) cardContainer.getChildren().get(5));
        value.add((Text) cardContainer.getChildren().get(7));
        value.get(value.size()-1).setText(""+view.getCostOfCard().get(cardNumber));
        coins.get(coins.size()-1).setFill(new ImagePattern(new Image("images/simple_elements/coin.png")));
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
            s.setMouseTransparent(false);
        });
        for (Pane item : cardContainer) item.getChildren().get(1).setMouseTransparent(true);
        inputManager.setCardEffectActivation();
    }

    /**It triggers effect of the card 7 on click.
     * @see it.polimi.ingsw.model.characters.Character7*/
    private void effect7(){
        for (Pane item : cardContainer) item.getChildren().get(1).setMouseTransparent(true);
        activatingCard=7;
        character7stud.forEach(s->{
            s.setDisable(false);
            s.setOpacity(1);
            s.setMouseTransparent(false);
        });
        inputManager.setCardEffectActivation();
        inputManager.setCardInActivation(7);
        showMoveButton();
        panelGoToBoard();
    }

    /**It shows the go-to board on the popup panel-*/
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
    }

    /**It triggers effect of the card 11 on click.
     * @see it.polimi.ingsw.model.characters.Character11*/
    private void effect11() {
        for (Pane item : cardContainer) item.getChildren().get(1).setMouseTransparent(true);
        activatingCard = 11;
        character11stud.forEach(s->{
            s.setDisable(false);
            s.setOpacity(1);
            s.setMouseTransparent(false);
        });
        inputManager.setCardEffectActivation();
    }

    /**It triggers effect of the card 2, 6, 8 on click. No parameters are required.
     * @see it.polimi.ingsw.model.characters.TurnEffectCharacter*/
    private void noParameterEffect(int number){
        inputManager.usaCharacterNoParameter(number);
    }

    /** Inserts in the corresponding characterStudent list the circle representing the students*/
    private void setStudentOfCharacter(int number, int position) {
        if(!(number==1||number==7||number==11))
            throw new IllegalArgumentException("this is not a student character");
        ArrayList<Circle> out= new ArrayList<>();
        for (int i = 8; i < cardContainer.get(position).getChildren().size(); i++) {
            Circle stud =(Circle) cardContainer.get(position).getChildren().get(i);
            stud.setStyle("-fx-stroke-width: 0");
            stud.setDisable(true);
            stud.setVisible(false);
            stud.setOpacity(0.4);
            stud.setOnMouseEntered(event ->  stud.setEffect(new DropShadow(3, Color.DARKRED)));
            stud.setOnMouseExited(event ->  stud.setEffect(null));
            out.add(stud);
        }
        if(number==1)
            character1stud=out;
        else if (number==7) {
            character7stud=out;
        }
        else character11stud=out;
    }
    /**It initializes student tokens on token characters' cards.*/
    private void initStudCharacters() {
        character1stud = new ArrayList<>();
        character7stud = new ArrayList<>();
        character11stud = new ArrayList<>();
        HashMap<Integer,Integer> cardNumberToPosition=new HashMap<>(Map.of(1,0,7,1,11,4));
        int id;
        Map<Integer, List<Integer>> studentsOnCard = view.getStudentsOnCard();
        for (Integer character:charactersPlayable) {
            if(character==1||character==7||character==11)
                setStudentOfCharacter(character,cardNumberToPosition.get(character));
        }
        if(!(charactersPlayable.contains(1) || charactersPlayable.contains(7) || charactersPlayable.contains(11))) {
            return;
        }
        for (Integer character : charactersPlayable) {
            if (character == 1 || character == 7 || character == 11) {
                List<Circle> studOfCardList;
                List<Integer> viewStudOnCard;
                if (character == 1) {
                    studOfCardList = character1stud;
                    viewStudOnCard = new ArrayList<>(studentsOnCard.get(1));
                } else if (character == 7) {
                    studOfCardList = character7stud;
                    viewStudOnCard = new ArrayList<>(studentsOnCard.get(7));
                } else {
                    studOfCardList = character11stud;
                    viewStudOnCard = new ArrayList<>(studentsOnCard.get(11));
                }
                for (int j = 0; j < studOfCardList.size(); j++)
                    if (viewStudOnCard.get(j) != null) {
                        Image img;
                        id = viewStudOnCard.get(j);
                        if (id != -1) {
                            img = getImage(id);
                            Circle charStudent=studOfCardList.get(j);
                            charStudent.setFill(new ImagePattern(img));
                            charStudent.setVisible(true);
                            charStudent.setDisable(true);
                            int finalId = id;
                            charStudent.setOnMouseClicked(event -> {
                                clickSelectStudent(finalId);
                                charStudent.setOpacity(0.2);
                            });
                        }
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
            default -> throw new NullPointerException("not a good id");
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

    /**It moves students from the card to the entrance.
     * @see it.polimi.ingsw.model.characters.Character7*/
    public void moveToBoard(){
        if(activatingCard==7 ){
            if(selectedStudents.size()>0)
                inputManager.saveSelectedStud(selectedStudents);
            else return;
        }
        gui.showBoards();
    }

    /**It selects color red for the card effect.
     * @see it.polimi.ingsw.model.characters.Character9*/
    public void selectedRed(){
        inputManager.useCharacter9(0);
        hideMoveButtons();
    }
    /**It selects color pink for the card effect*/
    public void selectedPink(){
        inputManager.useCharacter9(4);
        hideMoveButtons();
    }
    /**It selects color yellow for the card effect*/
    public void selectedYellow(){
        inputManager.useCharacter9(1);
        hideMoveButtons();
    }
    /**It selects color blue for the card effect.*/
    public void selectedBlue(){
        inputManager.useCharacter9(3);
        hideMoveButtons();
    }
    /**It selects color green for the card effect.*/
    public void selectedGreen(){
        inputManager.useCharacter9(2);
        hideMoveButtons();
    }



}
