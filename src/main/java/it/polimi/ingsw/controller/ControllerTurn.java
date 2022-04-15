package it.polimi.ingsw.controller;
import it.polimi.ingsw.messages.client.ChooseCloudMessage;
import it.polimi.ingsw.messages.client.MoveMotherMessage;
import it.polimi.ingsw.messages.server.ErrorMessageForClient;
import it.polimi.ingsw.messages.client.StudentToHallMessage;
import it.polimi.ingsw.messages.client.StudentToIslandMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.observer.Observable;

public class ControllerTurn extends Observable<ErrorMessageForClient> {

    private int idPlayerNow;
    private final Game game;
    private final Turn modelTurn;

    private void sendMessageError(RuntimeException e) {
        ErrorMessageForClient error =new ErrorMessageForClient(this.idPlayerNow,e);
        this.notify(error);
    }

    public void moveStudentToHall(StudentToHallMessage message){
        try {
            modelTurn.moveInHall(this.idPlayerNow, message.getStudentId());
        }catch (NullPointerException e){
            sendMessageError(e);
        }
    }
    public void moveStudentToIsland(StudentToIslandMessage message){
        try {
            modelTurn.moveToIsland(this.idPlayerNow,message.getStudentId(),message.getFinalPositionId());
        }catch(NullPointerException e){
             sendMessageError(e);
        }
    }

    public void moveMotherNature(MoveMotherMessage message){
        if(message.getSteps()>game.getPlayedCard(game.getCurrentPlayerId()).getMoveMother())
            sendMessageError(new RuntimeException("too many steps!"));
        else {
            try {
                modelTurn.moveMotherNature(message.getSteps());
            }catch (RuntimeException e){
                sendMessageError(e);
            }

        }
    }

    public void chooseCloud(ChooseCloudMessage message){
        try {
            modelTurn.moveFromCloudToEntrance(message.getCloudId(), this.idPlayerNow);
        }catch (RuntimeException e){
            sendMessageError(e);
        }
    }
    public void changeTurn(){
        this.idPlayerNow= game.getCurrentPlayerId();
    }
    public ControllerTurn(Game game){
        this.game=game;
        this.modelTurn =new Turn(game);
    }

    /*
    public boolean canUseCharacter(){
        int cost=0;
        // int cost= game.getCharacters(character).getCost();
        return game.getPlayer(idPlayerNow).getHand().enoughCoin(cost);
    }
    public void playCharacter(int character, ArrayList<Integer> inputPar) {
        Turn.useCharacter(character,inputPar,game);
        //to notify changes in the game an observer is needed

    }
    */

}
