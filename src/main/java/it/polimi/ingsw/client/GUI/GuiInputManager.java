package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.InputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class GuiInputManager extends InputManager {

    private CentralView game;
    private boolean canDoAction;
    private static boolean isLobbyAvailable;
    private static boolean isConnected;
    private static GUI gui;



    private ArrayList<Integer> selectedStudents;
    private int singleStudent;
    private List<Integer> selectedStudentFromElsewhere;
    private boolean cardEffectActivation;
    private int selectedIsland;
    private int selectedCloud;
    private static boolean canChooseIsland;
    public ArrayList<Integer> getSelectedStudents() {
        return selectedStudents;
    }

    public GuiInputManager(CentralView game,GUI gui) {
        singleStudent=-1;
        isLobbyAvailable=false;
        isConnected=false;
        this.game = game;
        GuiInputManager.gui =gui;
        selectedStudents=new ArrayList<>();
        selectedStudentFromElsewhere=new ArrayList<>();
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
    public void moveToBoard(){
        if(!canDoAction)
            return;
        game.moveStudentToHall(singleStudent);
        singleStudent=-1;
        waitForAnswer();
    }
    public boolean isActivatingCardEffect(){
        return cardEffectActivation;
    }
    public void selectManyStudentFromEntrance(List<Integer> listOfColorsStudent){
        selectedStudentFromElsewhere= new ArrayList<>();
        for (Integer colorStud:listOfColorsStudent) {
            game.getPersonalPlayer().getBoard().getStudentFromColorInEntrance(colorStud);
        }
    }
    public boolean hasSelectedStudent(){
        return singleStudent!=-1;
    }
    public void moveMotherTo(int islandID){//fixme doesn't work after merging
        int islandPos=game.getIslandPositionByID(islandID);
        if(islandPos==-1)
            return;
        int steps;
        if(islandPos>game.getMother())
            steps=(islandPos-game.getMother())%game.getIslands().size();
        else steps=islandPos+(game.getIslands().size()-game.getMother());
        if(steps<0)
            steps=-steps;
        if(steps<=(game.getCardYouPlayed()+2)/2) {
            game.moveMother(steps);
            canChooseIsland = false;
            waitForAnswer();
        }
    }
    public void moveToIsland(int islandId){
        if(!canDoAction || game.getState()!= GameState.actionMoveStudent || !game.isYourTurn())
            return;
        if(!game.getIslands().contains(game.getIslandById(islandId)))
            game.moveStudentToIsland(singleStudent,getFatherIsland(islandId).getIslandId());
        else {
            game.moveStudentToIsland(singleStudent, islandId);
        }
        singleStudent=-1;
        waitForAnswer();
    }
    public void useAssistantCard(int cardID){
        if(!canDoAction || game.getState()!= GameState.planPlayCard || !game.isYourTurn())
            return;
        game.useAssistantCard(cardID);
        waitForAnswer();
    }
    public void cloudChoose(int cloudID){
        if(!canDoAction || game.getState()!=GameState.actionChooseCloud || !game.isYourTurn())
            return;
        game.chooseCloud(cloudID);
        waitForAnswer();
    }

    /** used to keep track of the selected students when you need to move to a different scene*/
    public void saveSelectedStud(ArrayList<Integer> selectedStudents){this.selectedStudents=new ArrayList<>(selectedStudents);}
    public void saveSelectedStud(int selectedStudent){this.singleStudent=selectedStudent;}
    private SimplifiedIsland getFatherIsland(int islandId){
        SimplifiedIsland sub= game.getIslandById(islandId);
        for (SimplifiedIsland main: game.getIslands()) {
            if(game.getEverySubIsland(main).contains(sub))
                return main;
        }
        throw new NullPointerException();
    }
    /**
     * Used solely for character that need no additional parameters
     * IE : 2-6-8
     * */
    public void usaCharacterNoParameter(int characterNumber){
        if(!game.isYourTurn()||game.getCostOfCard().get(characterNumber)>game.getPersonalPlayer().getCoin())
            return;
        if(characterNumber==8 || characterNumber==6 ||characterNumber==2)
            game.playCharacter(characterNumber, new ParameterObject());
        cardEffectActivation=false;
    }
    public void useCharacter1(int studId, int islandId){
        if(!game.isYourTurn()||game.getCostOfCard().get(1)>game.getPersonalPlayer().getCoin())
            return;
        try{
            //int stud= getStudentOnCardFromColor(studentColor, 1);
            ParameterObject param= new ParameterObject(studId,islandId);
            game.playCharacter(1, param);
            cardEffectActivation=false;
        }catch (NullPointerException e){
            //todo error handling in gui
        }
    }
    public void useCharacter7(List<Integer> studentsFromCard){
        if(!game.isYourTurn()||game.getCostOfCard().get(7)>game.getPersonalPlayer().getCoin())
            return;
        if(!game.getStudentsOnCard().get(7).containsAll(studentsFromCard))
        {
            throw new NullPointerException();
        }
        //todo needs custom logic to show the entrance and save students


    }
    public void useCharacter9(int targetColor){
        if(!game.isYourTurn()||game.getCostOfCard().get(9)>game.getPersonalPlayer().getCoin())
            return;
        if(targetColor>4 || targetColor<0)
            return;
        game.playCharacter(9, new ParameterObject(targetColor));
        cardEffectActivation=false;
    }
    public void useCharacter10(){
        if(!game.isYourTurn()||game.getCostOfCard().get(10)>game.getPersonalPlayer().getCoin())
            return;

        //todo a bit tricky must write custom logic for this effect in boardController
    }
    public void useCharacter11(int studentId){
        if(!game.isYourTurn()||game.getCostOfCard().get(11)>game.getPersonalPlayer().getCoin())
            return;
        game.playCharacter(11, new ParameterObject(studentId, game.getPersonalPlayer().getId()));
        cardEffectActivation=false;
    }



    public void setCardEffectActivation() {
        cardEffectActivation=true;
    }

    @Override
    protected void caseActionChooseCloud() {

    }

    public static boolean isLobbyAvailable(){
        return isLobbyAvailable;
    }
}
