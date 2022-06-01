package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.InputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;

import java.util.ArrayList;

public class GuiInputManager extends InputManager {

    private CentralView game;
    private boolean canDoAction;
    private static boolean isLobbyAvailable;
    private static boolean isConnected;
    private static GUI gui;



    private ArrayList<Integer> selectedStudents;
    private int selectedIsland;
    private int selectedCloud;
    private static boolean canChooseIsland;
    public ArrayList<Integer> getSelectedStudents() {
        return selectedStudents;
    }

    public GuiInputManager(CentralView game,GUI gui) {
        isLobbyAvailable=false;
        isConnected=false;
        this.game = game;
        GuiInputManager.gui =gui;
    }
    public static GUI getGui(){
        return gui;
    }

    @Override
    public void decodeInput() {
        canDoAction=true;    //  after every time you send a message set canDoAction to false
    }
/** prints in a label in the connection page info from server*/
    @Override
    public void printToScreen(String string) {
        if(string.equals("no lobby available. Creating new lobby, number of players?"))
            isConnected=true;
        if(string.equals("connected to lobby")){
            isLobbyAvailable=true;
            isConnected=true;
        }

        if(string.contains("connection closed")){
            //TODO show a message at screen
        }
    }
    /** used to refrain the user from spamming actions before receiving the state update*/
    private void waitForAnswer(){this.canDoAction=false;}
    public static boolean isIsConnected() {
        return isConnected;
    }

    //these methods could be either put insides the scenes controllers or kept here
    @Override
    protected void caseSetupPlayers() {

    }

    @Override
    protected void casePlanPlayCard() {

    }

    @Override
    protected void caseActionMoveMother() {

    }
    public static void setChooseIsland(boolean can){
        canChooseIsland=can;
    }
    public static boolean canChooseIsland(){
        return canChooseIsland;
    }

    @Override
    protected void caseActionMoveStudent() {

    }
    public void moveToBoard(int color){
        if(!canDoAction)
            return;
        game.moveStudentToHall(color);
        waitForAnswer();
    }
    public void moveMotherTo(int islandID){
        int steps=(game.getMother()-islandID)%game.getIslands().size();
        if(steps<game.getCardYouPlayed())
            game.moveMother(steps);
        canChooseIsland=false;
        waitForAnswer();
    }
    public void moveToIsland(int islandId){
        if(!canDoAction)
            return;;
        int studColor=selectedStudents.get(0);
        game.moveStudentToIsland(studColor,islandId);
        selectedStudents=new ArrayList<>();//cleaned
        waitForAnswer();
    }
    public void useAssistantCard(int cardID){
        if(!canDoAction && game.getState()== GameState.planPlayCard)
            return;
        game.useAssistantCard(cardID);
        waitForAnswer();
    }
    public void cloudChoose(int cloudID){
        if(!canDoAction && game.getState()==GameState.actionChooseCloud)
            return;
        game.chooseCloud(cloudID);
        waitForAnswer();
    }

    /** used to keep track of the selected students when you need to move to a different scene*/
    public void saveSelectedStud(ArrayList<Integer> selectedStudents){this.selectedStudents=selectedStudents;}

    @Override
    protected void caseActionChooseCloud() {

    }

    public static boolean isLobbyAvailable(){
        return isLobbyAvailable;
    }
}
