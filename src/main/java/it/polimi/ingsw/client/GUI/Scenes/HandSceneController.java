package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedPlayer;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class HandSceneController extends SceneController{
    public Pane assistant_container;
    public Pane played_container;
    public Circle coinPlace;
    public Text playerCoins;
    public Text xSign;
    public Text help_text;
    public AnchorPane root;
    public Pane card_used_by_others;
    private ArrayList<Integer> discardedCard;
    GUI gui;
    private ArrayList<Rectangle> assistantCards;
    private final CentralView view;
    private boolean atLeastOneFree;
    public HandSceneController() {
        this.gui= GuiInputManager.getGui();
        this.view=gui.getGame();
    }

    public void initialize(){
        root.setStyle("-fx-background-image: url(images/wallpapers/sky_no_title.png); ");
        setContext();
        played_container.setMouseTransparent(true);
        setCards();
        setDiscarded();
        setCoins();
        setOthersLastPlayed();
    }
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
                cardsLastPlayed.get(i).setFill(new ImagePattern(getCardImage(view.getPlayedCardThisTurnByPlayerId(player.getId()))));
                i++;
            }
        }



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
            text = "this is you Hand \n wait for you turn";
        }
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
        atLeastOneFree= false;
        for (Boolean card:view.getPersonalPlayer().getAssistantCards()) {
            assistantCards.get(i).setVisible(card);
            assistantCards.get(i).setDisable(!card);
            ArrayList<Integer> played= new ArrayList<>();
            for (Integer playerID:view.getPlayers().stream().map(SimplifiedPlayer::getId).toList()) {
                played.add(view.getPlayedCardThisTurnByPlayerId(playerID));
            }
            if(played.contains(i))
                assistantCards.get(i).setOpacity(0.4);
            else {
                assistantCards.get(i).setOpacity(1);
                atLeastOneFree=true;
            }
            setClickChoose(assistantCards.get(i),i);
            i++;
        }
        if(!atLeastOneFree){
            help_text.setText("You don't have a different card from the other ones played\n Choose any of them");
            for (int j=0;j<view.getPersonalPlayer().getAssistantCards().length;j++) {
                assistantCards.get(j).setOpacity(1);
            }
        }
    }
    private void setClickChoose(Rectangle cardAssistant, int cardId){
        cardAssistant.setDisable(false);
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
            }
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

    public void goToCharacters() {
        gui.showCharacters();
    }
}
