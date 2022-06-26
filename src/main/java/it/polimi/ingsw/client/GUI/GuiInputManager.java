package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.InputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.util.ParameterObject;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;

import java.util.ArrayList;
import java.util.List;
/**The handler of client inputs for the Graphical User Interface. It translates commands from client into actions and messages for server, also into error messages for client.*/
public class GuiInputManager extends InputManager {

    private final CentralView game;
    private boolean canDoAction;
    private static boolean isLobbyAvailable;
    private static boolean isConnected;
    private static GUI gui;
    private ArrayList<Integer> selectedStudents;
    private int singleStudent;
    private int cardInActivation;

    private int numberOfStudentSelectedFromCharacter;
    private boolean cardEffectActivation;

    /**It creates an instance of input manager for the GUI.
     * @param game the central view of the player
     * @param gui the reference to the GUI to manage*/
    public GuiInputManager(CentralView game,GUI gui) {
        singleStudent=-1;
        isLobbyAvailable=false;
        isConnected=false;
        this.game = game;
        GuiInputManager.gui =gui;
        selectedStudents=new ArrayList<>();
    }

    /**@return the reference to the handled GUI*/
    public static GUI getGui(){
        return gui;
    }

    @Override
    public void decodeInput() {
        canDoAction=true;    //  after every time you send a message set canDoAction to false
    }

    @Override
    public void printToScreen(String string) {
        if(string.equals("no lobby available. Creating new lobby, number of players?"))
            isConnected=true;
        if(string.equals("connected to lobby")){
            isLobbyAvailable=true;
            isConnected=true;
        }

        if(string.contains("connection closed")){
            gui.showError("connection closed");
            gui.stop();//fixme
        }
    }

    /**It is used to stop the user from spamming actions before receiving the state update from the server.*/
    private void waitForAnswer(){this.canDoAction=false;}

    /**It is used to check if the connection to the server has been established.*/
    public static boolean isIsConnected() {
        return isConnected;
    }

    /**It is used to move a student from the entrance to the dining room.*/
    public void moveToBoard(){
        if(!canDoAction)
            return;
        game.moveStudentToHall(singleStudent/26);
        singleStudent=-1;
        waitForAnswer();
    }

    /**It is used to check if the user is currently activating an effect and ensures he can interact with elements contextually.*/
    public boolean isActivatingCardEffect(){
        return cardEffectActivation;
    }

    /**It checks if a student has already been selected, for example when moving students from entrance.*/
    public boolean hasSelectedStudent(){
        return singleStudent!=-1;
    }

    /**It moves the mother to the target island.
     * @param islandID the id of the selected island, if it is too far the mother isn't moved*/
    public void moveMotherTo(int islandID){
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
            waitForAnswer();
        }
    }

    /**It is used to move a student from entrance to a target island.*/
    public void moveToIsland(int islandId){
        if(!canDoAction || game.getState()!= GameState.actionMoveStudent || !game.isYourTurn())
            return;
        if(!game.getIslands().contains(game.getIslandById(islandId)))
            game.moveStudentToIsland(singleStudent/26, getRootIsland(islandId).getIslandId());
        else {
            game.moveStudentToIsland(singleStudent/26, islandId);
        }
        singleStudent=-1;
        waitForAnswer();
    }

    /**It is used to play a card during the planning phase.
     * @param cardID the identifier of the selected card*/
    public void useAssistantCard(int cardID){
        if(!canDoAction || game.getState()!= GameState.planPlayCard || !game.isYourTurn())
            return;
        game.useAssistantCard(cardID);
        waitForAnswer();
    }

    /**It is used to choose a target cloud from which refill the player entrance.
     * @param cloudID the id of the selected cloud*/
    public void cloudChoose(int cloudID){
        if(!canDoAction || game.getState()!=GameState.actionChooseCloud || !game.isYourTurn())
            return;
        game.chooseCloud(cloudID);
        waitForAnswer();
    }

    /** It is used to keep track of the selected students when you need to move to a different scene.
     * @param selectedStudents the list of selected students*/
    public void saveSelectedStud(ArrayList<Integer> selectedStudents){
        this.selectedStudents=new ArrayList<>(selectedStudents);
        numberOfStudentSelectedFromCharacter=selectedStudents.size();
    }

    /**It saves a selected student. It is used when the memory of the selected student has to persist after changing scene.
     * @param selectedStudent the student to save*/
    public void saveSelectedStud(int selectedStudent){this.singleStudent=selectedStudent;}

    /**It is used to get the main island of the cluster based on an island id.
     * @param islandId the id of a sub-island of the cluster
     * @return the root of the cluster
     * @exception NullPointerException if the islands is not found*/
    private SimplifiedIsland getRootIsland(int islandId){
        SimplifiedIsland sub= game.getIslandById(islandId);
        for (SimplifiedIsland main: game.getIslands()) {
            if(game.getEverySubIsland(main).contains(sub))
                return main;
        }
        throw new NullPointerException();
    }

    /**@return the selected single student*/
    public int getSingleStudent() {
        return singleStudent;
    }

    /**It activates turn effect characters, which need no additional parameters (character 2, character 6, character 8).
     * @param characterNumber id of the selected character
     * @see it.polimi.ingsw.model.characters.TurnEffectCharacter*/
    public void usaCharacterNoParameter(int characterNumber){
        if(!game.isYourTurn()||game.getCostOfCard().get(characterNumber)>game.getPersonalPlayer().getCoin())
            return;
        if(characterNumber==8 || characterNumber==6 ||characterNumber==2)
            game.playCharacter(characterNumber, new ParameterObject());
        cardEffectActivation=false;
        cardInActivation=0;
    }

    /**It activates character 6 effect for the whole turn. It doesn't count the towers of island(s) when calculating influence.
     * @param islandId the target island
     * @see it.polimi.ingsw.model.characters.TurnEffectCharacter*/
    public void useCharacter1( int islandId){
        if(!game.isYourTurn()||game.getCostOfCard().get(1)>game.getPersonalPlayer().getCoin())
            return;
        try{
            ParameterObject param= new ParameterObject(singleStudent,islandId);
            game.playCharacter(1, param);
        }catch (NullPointerException ignored){
        }
        singleStudent=-1;
        cardEffectActivation=false;
        cardInActivation=0;
    }

    /**It activates character 7 effect.
     * @param studentsFromEntrance the list of selected students
     *  @see it.polimi.ingsw.model.characters.Character7*/
    public void useCharacter7(List<Integer> studentsFromEntrance){
        if(!game.isYourTurn()||game.getCostOfCard().get(7)>game.getPersonalPlayer().getCoin())
            return;
        List<Integer> studentsFromCard= selectedStudents;
        //noinspection SlowListContainsAll
        if(!game.getStudentsOnCard().get(7).containsAll(studentsFromCard) || (studentsFromCard.size() > 3) || (studentsFromCard.size() == 0))
        {
            throw new NullPointerException();
        }
        int dimension=studentsFromEntrance.size();
        int []  entranceStudents= listToArray(studentsFromEntrance,dimension);
        int []  cardStudents=listToArray(studentsFromCard,dimension);
        ParameterObject param= new ParameterObject(game.getPersonalPlayer().getId(),entranceStudents,cardStudents);
        game.playCharacter(7, param);
        cardEffectActivation=false;
        cardInActivation=0;
    }

    /**It activates character 9 effect.
     * @param targetColor the target color
     * @see it.polimi.ingsw.model.characters.Character9*/
    public void useCharacter9(int targetColor){
        if(!game.isYourTurn()||game.getCostOfCard().get(9)>game.getPersonalPlayer().getCoin())
            return;
        if(targetColor>4 || targetColor<0)
            return;
        game.playCharacter(9, new ParameterObject(targetColor));
        cardEffectActivation=false;
        cardInActivation=0;
    }

    /**It activates character 10 effect.
     * @param diningStudents the list of target students from dining room
     * @param entranceStudents the list of target students trom entrance
     * @see it.polimi.ingsw.model.characters.Character10*/
    public void useCharacter10(List<Integer> entranceStudents, List<Integer> diningStudents){
        if(!game.isYourTurn()||game.getCostOfCard().get(10)>game.getPersonalPlayer().getCoin())
            return;
        int dimension= entranceStudents.size();
        int []  entranceTargets= listToArray(entranceStudents,dimension);
        int []  diningTargets=listToArray(diningStudents,dimension);
        game.playCharacter(10, new ParameterObject(game.getPersonalPlayer().getId(),entranceTargets,diningTargets));
        cardEffectActivation=false;
        cardInActivation=0;
    }

    /**It activates character 11 effect.
     * @param studentId the id of the target student
     * @see it.polimi.ingsw.model.characters.Character11*/
    public void useCharacter11(int studentId){
        if(!game.isYourTurn()||game.getCostOfCard().get(11)>game.getPersonalPlayer().getCoin())
            return;
        game.playCharacter(11, new ParameterObject(studentId, game.getPersonalPlayer().getId()));
        cardEffectActivation=false;
    }

    /**It converts a list to an array.
     * @param dimension the size of the list
     * @param list the list to transform*/
    private int[] listToArray(List<Integer> list, int dimension){
        int []  array=new int[dimension];
        for (int i = 0; i < dimension; i++) {
            array[i]=list.get(i);
        }
        return array;
    }

    /**It sets a flag that ensures the user can only do actions that are contextually allowed.*/
    public void setCardEffectActivation() {
        cardEffectActivation=true;
    }

    /**It resets the current activation of an effect*/
    public void resetEffectActivation(){
        cardEffectActivation=false;
        cardInActivation=0;
    }

    /**@return the id of the card activated*/
    public int getCardInActivation() {
        return cardInActivation;
    }

    /**It sets the card activated.
     * @param cardInActivation the id of the card activated*/
    public void setCardInActivation(int cardInActivation) {
        this.cardInActivation = cardInActivation;
    }

    /**It states if there is an available lobby or not.*/
    public static boolean isLobbyAvailable(){
        return isLobbyAvailable;
    }

    /**@return the number of tokens chosen from a token character card (expert mode only)*/
    public int getNumberOfStudentSelectedFromCharacter(){return this.numberOfStudentSelectedFromCharacter;}
}
