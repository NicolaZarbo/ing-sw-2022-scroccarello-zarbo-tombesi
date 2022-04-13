package it.polimi.ingsw.controller;
import it.polimi.ingsw.messages.client.MoveMotherMessage;
import it.polimi.ingsw.messages.server.ErrorMessageForClient;
import it.polimi.ingsw.messages.client.StudentToHallMessage;
import it.polimi.ingsw.messages.client.StudentToIslandMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Turn;

public class ControllerTurn {
    //in case we decide to use  observer pattern:
    // every method that modifies something in the model could call a notify call to an observer in controller and exchange the information through that
    //instead of having to return the new state
    private int idPlayerNow;
    private final Game game;

    private void sendMessageError(RuntimeException e){
        ErrorMessageForClient error =new ErrorMessageForClient(this.idPlayerNow,e);
        //send(this.idPlayerNow)
    }//TODO

    public void moveStudentToHall(StudentToHallMessage message){
        try {
            Turn.moveInHall(this.idPlayerNow, message.getStudentId(), this.game);
        }catch (NullPointerException e){
            sendMessageError(e);
        }
    }
    public void moveStudentToIsland(StudentToIslandMessage message){
        try {
            Turn.moveToIsland(this.idPlayerNow,message.getStudentId(),message.getFinalPositionId(),this.game);
        }catch(NullPointerException e){
             sendMessageError(e);
        }
    }

    public void moveMotherNature(MoveMotherMessage message){
        if(message.getSteps()>game.getPlayedCard(game.getCurrentPlayerId()).getMoveMother())
            sendMessageError(new RuntimeException("too many steps!"));
        else {
            try {
                Turn.moveMotherNature(message.getSteps(), game);
            }catch (RuntimeException e){
                sendMessageError(e);
            }

        }
    }

    public void chooseCloud(int cloudId){
        Turn.moveFromCloudToEntrance(this.game,cloudId,this.idPlayerNow);
    }
    public void changeTurn(){
        this.idPlayerNow= game.getCurrentPlayerId();
    }
    public ControllerTurn(Game game){
        this.game=game;
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
