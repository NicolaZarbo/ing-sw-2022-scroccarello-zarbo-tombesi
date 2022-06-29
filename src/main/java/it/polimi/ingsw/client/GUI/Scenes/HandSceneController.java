package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import java.util.ArrayList;

/**The handler of player's hand scene.*/
public class HandSceneController extends SceneController{
    public Pane assistant_container;
    public Pane played_container;
    public Circle coinPlace;
    public Text playerCoins;
    public Text xSign;
    public Text help_text;
    public AnchorPane root;
    public Pane card_used_by_others;
    public ImageView activeCharacter;
    public Button characterButton;
    private ArrayList<Integer> discardedCard;
    final GUI gui;
    private ArrayList<Rectangle> assistantCards;
    private final CentralView view;
    private boolean atLeastOneFree;
    public HandSceneController() {
        this.gui= GuiInputManager.getGui();
        this.view=gui.getGame();
    }

    @Override
    public void initialize(){ //fixme sometimes the the played and not played cards aren't shown correctly after some moves, like using a character
        root.setStyle("-fx-background-image: url(images/wallpapers/sky_no_title.png); ");
        root.setCursor(new ImageCursor(new Image("images/pointer/baseArrow.png")));
        setContext();
        played_container.setMouseTransparent(true);
        setCards();
        setDiscarded();
        setCoins();
        setOthersLastPlayed();
    }
    private void initActiveCharacter(){
        if(view.isEasy())
        {
            characterButton.setVisible(false);
            characterButton.setDisable(true);
            activeCharacter.setDisable(true);
        }
        if(view.getActiveCharacter()!=0)
            activeCharacter.setImage(characterImage(view.getActiveCharacter()));
    }
    private Image characterImage(int character){
        String path= switch (character){
            case 1 ->"images/characters/1_monk.png";
            case 2 ->"images/characters/2_farmer.png";
            case 3 ->"images/characters/3_herald.png";
            case 4 ->"images/characters/4_postman.png";
            case 5 ->"images/characters/5_grandma.png";
            case 6 ->"images/characters/6_cent.png";
            case 7 ->"images/characters/7_jester.png";
            case 8 ->"images/characters/8_knight.png";
            case 9 ->"images/characters/9_mushroom.png";
            case 10 ->"images/characters/10_singer.png";
            case 11 ->"images/characters/11_princess.png";
            case 12->"images/characters/12_thief.png";
            default -> {throw new NullPointerException();}
        };
        return new Image(path);
    }

    /**It sets on scene the cards played by other players.*/
    private void setOthersLastPlayed(){
        ArrayList<Text> names= new ArrayList<>();
        ArrayList<Rectangle> cardsLastPlayed= new ArrayList<>();
        for (Node child:card_used_by_others.getChildren()) {
            if(child instanceof Rectangle)
                cardsLastPlayed.add((Rectangle) child);
            if(child instanceof Text)
                names.add((Text)child);
            child.setVisible(false);
        }
        int i =0;
        for (SimplifiedPlayer player:view.getPlayers()) {
            if(player.getId()!=view.getPersonalPlayer().getId() && (view.getPlayedCardThisTurnByPlayerId(player.getId())!=null)) {
                names.get(i).setVisible(true);
                cardsLastPlayed.get(i).setVisible(true);
                names.get(i).setText(player.getUsername().substring(0,Math.min(9,player.getUsername().length())));
                cardsLastPlayed.get(i).setFill(new ImagePattern(getCardImage(1+view.getPlayedCardThisTurnByPlayerId(player.getId()))));
                i++;
            }
        }
    }

    /**It shows on the scene the amount of coins of the player (expert mode only).*/
    private void setCoins(){
        if(view.isEasy()){
            coinPlace.setVisible(false);
            playerCoins.setVisible(false);
            xSign.setVisible(false);
        }else {
            coinPlace.setFill(new ImagePattern(new Image("images/simple_elements/coin.png")));
            playerCoins.setText(view.getPersonalPlayer().getCoin()+"");
        }
    }

    /**It shows the discarded pile of the player.*/
    private void setDiscarded() {
        boolean[] cards = view.getPersonalPlayer().getAssistantCards();
        discardedCard= new ArrayList<>();
        for (int i = 0; i < cards.length; i++) {
            if(!cards[i])
                discardedCard.add(i);
        }
        int i =0;
        for (Node played:played_container.getChildren()) {
            if(i< discardedCard.size()) {
                ((Rectangle) played).setFill(new ImagePattern(getCardImage(discardedCard.get(i) + 1)));
                played.setVisible(true);
            }else played.setVisible(false);
            i++;
        }
    }

    /**It sets the text message to advise the player what he has to do based on game state.*/
    private void setContext(){
        String text;
        if(view.isYourTurn()) {
            assistant_container.setOpacity(1);
            switch (view.getState()) {
                case actionChooseCloud -> text = "Go to map to choose a cloud";
                case actionMoveStudent -> text = "Go to board to move your students";
                case actionMoveMother -> text = "Go to Map to move Mother Nature";
                case planPlayCard -> text = "Pick a card";
                default -> text = "this is you Hand";
            }
        }else {
            assistant_container.setOpacity(0.4);
            text = "this is you Hand \n "+view.getTurnPlayer()+" is now playing";
        }
       help_text.setText(text);
    }

    /**It shows the list of available cards.*/
    private void setCards(){
        assistantCards= new ArrayList<>();
        for (Node card:assistant_container.getChildren()) {
            assistantCards.add((Rectangle) card);
            setShadow((Rectangle) card);
            int cardNumber= Integer.parseInt(card.getId().substring(13));
            ( (Rectangle) card).setFill(new ImagePattern(getCardImage(cardNumber)));
            card.setVisible(false);
        }
        int i = 0;
        atLeastOneFree= false;
        for (Boolean card:view.getPersonalPlayer().getAssistantCards()) {
            assistantCards.get(i).setVisible(card);
            assistantCards.get(i).setDisable(!card);
            ArrayList<Integer> played= new ArrayList<>();
            for (Integer playerID:view.getPlayers().stream().map(SimplifiedPlayer::getId).toList()) {
                played.add(view.getPlayedCardThisTurnByPlayerId(playerID));
            }
            if(played.contains(i)) {
                assistantCards.get(i).setOpacity(0.4);
                assistantCards.get(i).setDisable(true);
            }
            else {
                assistantCards.get(i).setOpacity(1);
                atLeastOneFree=true;
            }
            if(view.getCardYouPlayed()==i)
                assistantCards.get(i).setOpacity(0.4);

            setClickChoose(assistantCards.get(i),i);
            i++;
        }
        if(!atLeastOneFree){
            help_text.setText("You don't have a different card from the other ones played\n Choose any of them");
            for (int j=0;j<view.getPersonalPlayer().getAssistantCards().length;j++) {
                assistantCards.get(j).setOpacity(1);
                assistantCards.get(i).setDisable(false);
            }
        }



    }

    /**It triggers the choice of the player which clicked the element. If the player cannot choose the card (either because it's not his turn, or the card is unavailable) he is notified with an error message.
     * @param cardAssistant the clicked scene element
     * @param cardId the id of the selected card*/
    private void setClickChoose(Rectangle cardAssistant, int cardId){
        cardAssistant.setCursor(new ImageCursor(new Image("images/pointer/basePointer.png")));
        cardAssistant.setOnMouseClicked(mouseEvent -> {
            if(view.getState()== GameState.planPlayCard && view.isYourTurn()) {
                ArrayList<Integer> played= new ArrayList<>();
                for (Integer playerID:view.getPlayers().stream().map(SimplifiedPlayer::getId).toList()) {
                    played.add(view.getPlayedCardThisTurnByPlayerId(playerID));
                }
                if (played.contains(cardId) && atLeastOneFree)
                    help_text.setText("You can't use that card!!\n Pick another");
                else
                    gui.getInputManager().useAssistantCard(cardId);
            }
            if(view.getState()== GameState.planPlayCard) {
                cardAssistant.setScaleX(1.08);
                cardAssistant.setScaleY(1.08);
                help_text.setText("Wait for your turn");
                assistant_container.setMouseTransparent(true);
            }
        });
    }

    /**It sets shadow effect on the element.
     * @param clickable the element on which add effect*/
    private void setShadow(Rectangle clickable){
        DropShadow islandShadow = new DropShadow(8, Color.DARKRED);
        clickable.setOnMouseEntered(event -> clickable.setEffect(islandShadow));
        clickable.setOnMouseExited(event -> clickable.setEffect(null));
    }

    /**@param cardNumber the id of the card
     * @return the image of the card*/
    private Image getCardImage(int cardNumber){
        return new Image("images/assistants/assistant_"+cardNumber+".png");
    }

    /**It changes the scene into <i>board scene</i>>.*/
    public void showboards(){
        gui.showBoards();
    }

    /**It changes the scene into <i>map scene</i>>.*/
    public void showMap(){
        gui.showIslands();
    }

    /**It changes the scene into <i>character scene</i>> (expert mode only).*/
    public void goToCharacters() {
        gui.showCharacters();
    }
}
