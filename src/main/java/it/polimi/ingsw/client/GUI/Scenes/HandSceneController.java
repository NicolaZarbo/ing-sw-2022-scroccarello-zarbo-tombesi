package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class HandSceneController extends SceneController{
    public Pane assistant_container;
    public Pane played_container;
    public Circle coinPlace;
    public Text playerCoins;
    public Text xSign;
    public Text help_text;
    private ArrayList<Integer> discardedCard;
    GUI gui;
    private ArrayList<Rectangle> assistantCards;
    private final CentralView view;
    public HandSceneController() {
        this.gui= GuiInputManager.getGui();
        this.view=gui.getGame();
    }

    public void initialize(){
        setContext();
        played_container.setMouseTransparent(true);
        setCards();
        setDiscarded();
        setCoins();
    }
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
    private void setContext(){
        String text;
        if(view.isYourTurn()) {
            switch (view.getState()) {
                case actionChooseCloud -> text = "Go to map to choose a cloud";
                case actionMoveStudent -> text = "Go to board to move your students";
                case actionMoveMother -> text = "Go to Map to move Mother Nature";
                case planPlayCard -> text = "Pick a card";
                default -> text = "this is you Hand";
            }
        }else text="this is you Hand \n wait for you turn";
       help_text.setText(text);
    }
    private void setCards(){
        assistantCards= new ArrayList<>();
        for (Node card:assistant_container.getChildren()) {
            assistantCards.add((Rectangle) card);
            int cardNumber= Integer.parseInt(card.getId().substring(13));
            ( (Rectangle) card).setFill(new ImagePattern(getCardImage(cardNumber)));
            card.setVisible(false);
        }
        int i = 0;
        for (Boolean card:view.getPersonalPlayer().getAssistantCards()) {
            assistantCards.get(i).setVisible(card);
            if(view.getPlayedCardThisTurn().contains(i))
                assistantCards.get(i).setOpacity(0.4);
            else assistantCards.get(i).setOpacity(1);
            setClickChoose(assistantCards.get(i),i);
            i++;
        }
    }
    private void setClickChoose(Rectangle cardAssistant, int cardId){
        cardAssistant.setDisable(false);
        cardAssistant.setOnMouseClicked(mouseEvent -> {
            if(view.getState()== GameState.planPlayCard )
                if (view.getPlayedCardThisTurn().contains(cardId))
                    help_text.setText("You can't use that card!!\n Pick another");
                else gui.getInputManager().useAssistantCard(cardId);
        });
    }
    private Image getCardImage(int cardNumber){
        return new Image("images/assistants/assistant_"+cardNumber+".png");
    }
    public void showboards(){
        gui.showBoards();
    }
    public void showMap(){
        gui.showIslands();
    }
}
