package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.enumerations.SceneEnum;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**The handler of the single board scene of the player.*/
public class SingleBoardController {//extends BoardSceneController

    public Pane entrance;
    public Pane hall;
    public Pane table;
    public Pane towers;
    public Pane moveStudentPanel;

    public Pane popupCardPanel;
    public Text studToSelect;
    public Button toBoard;
    public Button toIsland;
    public Text movedText;
    protected GUI gui;
    private int playerOwner;

    protected ArrayList<Integer> clickedEntranceStudentsColor;
    protected ArrayList<Integer> clickedDiningStudentsColor;

    private List<Circle> entranceList;
    private List<ArrayList<Circle>> diningRows;
    private List<ImageView> towerList;
    private List<Polygon> teacherList;

    public SingleBoardController() {
        this.gui= GuiInputManager.getGui();
        setPlayerOwner(BoardSceneXController.counterBoard);
    }



    public void initialize() {
        moveStudentPanel.setVisible(false);
        moveStudentPanel.setMouseTransparent(true);
        initEntrance();
        initTowers();
        initTable();
        initDiningRoom();
        setProfessors();
        setEntrance();
        setHall();
        setTowers();
        activationContext();
    }
    private void activationContext(){
        resetActivationOnExit();
        GuiInputManager manager= gui.getInputManager();
        if(manager.isActivatingCardEffect() && (manager.getCardInActivation()==7 || manager.getCardInActivation()==10) ){
            showCardPanel();
        }
        else {
            hideCardPanel();
        }

    }
    private void resetActivationOnExit(){
        if(gui.getInputManager().isActivatingCardEffect()){
            EventHandler<MouseEvent> changeSceneMidActivation= new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                gui.getInputManager().resetEffectActivation();
                }
            };
            toBoard.setOnMouseClicked(changeSceneMidActivation );
            toIsland.setOnMouseClicked(changeSceneMidActivation);
        }
    }


    private void initEntrance(){
        entranceList= new ArrayList<>();
        for (Node student:entrance.getChildren()) {
            entranceList.add((Circle)student);
        }
        entrance.setEffect(new DropShadow(6, Color.DARKGRAY));
    }
    private void initDiningRoom(){
        diningRows=new ArrayList<>();
        ArrayList<Circle> diningRow = new ArrayList<>();
        for (Node row: hall.getChildren()) {
            for (Node student:((Pane) row).getChildren()) {
                diningRow.add((Circle)student);
            }
            diningRows.add(diningRow);
            diningRow=new ArrayList<>();
        }
        hall.setEffect(new DropShadow(5, Color.BLACK));
    }
    private void initTable(){
        teacherList=new ArrayList<>();
        for (Node teacher :table.getChildren()) {
            teacherList.add((Polygon) teacher);
        }
        table.setEffect(new DropShadow(6, Color.BLACK));
    }
    private void initTowers(){
        towerList=new ArrayList<>();
        for (Node tower:towers.getChildren()) {
            towerList.add((ImageView) tower);
        }
    }


    /**displays the tokens in the entrance*/
    public void setEntrance(){
        for (Circle stud:entranceList) {
            stud.setDisable(true);
            stud.setVisible(false);
            stud.setStyle("-fx-stroke-width: 0");
        }
        List<Integer> playerEntrance=gui.getGame().getPlayers().get(playerOwner).getBoard().getEntrance();
        int id;
        for(int i=0;i<playerEntrance.size();i++){
            Image img;
            id= playerEntrance.get(i);
            if(id!=-1){
                if(gui.getGame().isYourTurn()&& gui.getGame().getState()== GameState.actionMoveStudent)
                    entranceList.get(i).setCursor(new ImageCursor(new Image("images/pointer/pickUpPointer.png")));
                setEntranceClickable(entranceList.get(i),id);
                img=getStudentImage(id);
                entranceList.get(i).setFill(new ImagePattern(img));
                entranceList.get(i).setVisible(true);
                entranceList.get(i).setDisable(false);
            }
        }
    }

    private Image getStudentImage(int idStudent){
        Image stud;
        switch(idStudent/26){
            case 0->stud=new Image("images/students/student3d/red.png");
            case 1 ->stud=new Image("images/students/student3d/yellow.png");
            case 2-> stud=new Image("images/students/student3d/green.png");
            case 3-> stud=new Image("images/students/student3d/blue.png");
            case 4->stud=new Image("images/students/student3d/pink.png");
            default -> {
                throw new NullPointerException("not an iD");
            }
        }
        return stud;
    }

    public void setHall(){
        List<ArrayList<Circle>> hall=diningRows;
        for (ArrayList<Circle> list:hall) {
            for (Circle stud:list) {
                stud.setVisible(false);
                stud.setDisable(true);
                stud.setStyle("-fx-stroke-width: 0");
            }
        }
        clickedDiningStudentsColor=new ArrayList<>();
        Integer[][] diningroom=gui.getGame().getPlayers().get(playerOwner).getBoard().getDiningRoom();
        for(int i=0;i<diningroom.length;i++){
            for(int j=0;j<diningroom[i].length;j++){
                Image img;
                int id= diningroom[i][j];
                if(id!=-1){
                    img=getStudentImage(id);
                    setDiningRoomClickable(hall.get(i).get(j),id);
                    hall.get(i).get(j).setFill(new ImagePattern(img));
                    hall.get(i).get(j).setVisible(true);
                    hall.get(i).get(j).setDisable(false);
                }
            }
        }
    }

    /**displays professors if the player has*/
    public void setProfessors(){
        Integer[] professors=gui.getGame().getPlayers().get(playerOwner).getBoard().getProfessorTable();
        for (Polygon teach:teacherList) {
            teach.setVisible(false);
            teach.setFill(null);
        }
        for(Integer i : professors){
            if(i!=-1){
                Image img;
                switch(i){
                    case 0->img=new Image("images/teachers/teacher3d/redProf.png");
                    case 1->img=new Image("images/teachers/teacher3d/yellowProf.png");
                    case 2->img=new Image("images/teachers/teacher3d/greenProf.png");
                    case 3->img=new Image("images/teachers/teacher3d/blueProf.png");
                    case 4->img=new Image("images/teachers/teacher3d/pinkProf.png");
                    default -> {throw new RuntimeException("not a professor id");}
                }
                teacherList.get(i).setFill(new ImagePattern(img));
                teacherList.get(i).setVisible(true);
            }
        }
    }
    public void setTowers(){
        int towerColor=gui.getGame().getPlayers().get(playerOwner).getTowerColor();
        int towersLeft=gui.getGame().getPlayers().get(playerOwner).getBoard().getTowersLeft();
        Image img;
        switch(towerColor){
            case 0->img=new Image("images/towers/black_tower.png");
            case 1->img=new Image("images/towers/white_tower.png");
            case 2->img=new Image("images/towers/grey_tower.png");
            default -> {throw new RuntimeException("not a towerColor");}
        }
        for(int i=0;i<towersLeft;i++){
            towerList.get(i).setImage(img);
            towerList.get(i).setVisible(true);
        }
    }
    public void setPlayerName(Text textBox){
        String name=gui.getGame().getPlayers().get(playerOwner).getUsername();
        textBox.setText("Player: "+name);
    }
    /**
     * used in initialize() to set the present student Clickable based on the context
     * @param studentId the id of the student */
    protected void setEntranceClickable(Circle student, int studentId){
        if(!gui.getGame().isYourTurn())
            return;
        int clickedColor = studentId/26;
        clickedEntranceStudentsColor=new ArrayList<>();
        student.setDisable(false);
        setStudentColoredShadow(student,clickedColor);
        GuiInputManager inputManager=gui.getInputManager();
        student.setOnMouseClicked(mouseEvent -> {
            if(!isYourBoard()) {
                return;
            }
            if(inputManager.isActivatingCardEffect() && inputManager.getCardInActivation()==10){
                effect10EntranceHandler(student, studentId);
            }
            if (gui.getGame().getState() == GameState.actionMoveStudent && !inputManager.isActivatingCardEffect()) {
                inputManager.saveSelectedStud(studentId);
                showMoveButton();
                doubleClickToDining(student);
            }
            if(inputManager.isActivatingCardEffect() && inputManager.getCardInActivation()==7){
                effect7EntranceHandler(student,studentId);
            }
        });
    }
    private void doubleClickToDining(Circle student){
        student.setOnMouseClicked(new EventHandler<>() {
             
            public void handle(MouseEvent mouseEvent) {
                moveToDining();
                student.removeEventHandler(MouseEvent.MOUSE_CLICKED,this);
            }
        });
    }

    /**It handles resolution of character 7 effect (expert mode only).
     * @param student the token to exclude
     * @param studentId the student to add
     * @see it.polimi.ingsw.model.characters.Character7*/
    private void effect7EntranceHandler(Circle student, int studentId){
        if(clickedEntranceStudentsColor.size()>gui.getInputManager().getNumberOfStudentSelectedFromCharacter())
            return;
        clickedEntranceStudentsColor.add(studentId);
        showCardPanel();
        student.setOpacity(0.5);
        student.setDisable(true);
    }

    /**It handles resolution of character 10 effect (expert mode only).
     * @param student the token to exclude
     * @param studId the student to add
     * @see it.polimi.ingsw.model.characters.Character10*/
    private void effect10EntranceHandler(Circle student, int studId){
        if(clickedEntranceStudentsColor.size()>2)
            return;
        clickedEntranceStudentsColor.add(studId);
        student.setOpacity(0.5);
        student.setDisable(true);
        showCardPanel();
    }
    /** It is used to add a shadow around a student on mouse over. Different types of shadows are assigned according to the token color.
     * @param student the element of the scene
     * @param color the color of the token*/
    private void setStudentColoredShadow(Circle student, int color){
        if(this.playerOwner!=gui.getGame().getPersonalPlayer().getId())
            return;
        Color shadowColor =switch (color){
            case 0-> Color.DARKRED;
            case 1 -> Color.YELLOW;
            case 2 ->Color.DARKOLIVEGREEN;
            case 3 -> Color.DARKBLUE;
            case 4 ->Color.PURPLE;
            default -> Color.WHITESMOKE;
        };
        DropShadow islandShadow = new DropShadow(8, shadowColor);
        student.setOnMouseEntered(event -> student.setEffect(islandShadow));
        student.setOnMouseExited(event -> student.setEffect(null));
    }

    /**It assigns the board to its owner.*/
    public void setPlayerOwner(int owner){
        playerOwner=owner;
    }

    /**It states if the board belongs to the player or not.*/
    public boolean isYourBoard(){
        return playerOwner==gui.getGame().getPersonalPlayer().getId();
    }

    /**It is used to put the selected student inside the dining room.*/
    protected void moveToDining(){
        GuiInputManager inputManager=gui.getInputManager();
        inputManager.moveToBoard();
        this.clickedEntranceStudentsColor=new ArrayList<>();
        hideMoveButtons();
    }

    /**It is used to show the map and let the user choose an island where put the selected student.*/
    protected void chooseTargetIsland(){
        this.clickedEntranceStudentsColor=new ArrayList<>();
        gui.setScene(SceneEnum.MapScene);
        hideMoveButtons();
    }
    /**
     * It sets clickble students on dining room.
     * @param studentID the id of the color
     * @param student the element on the scene*/
    protected void setDiningRoomClickable(Circle student, int studentID){
        student.setDisable(false);
        GuiInputManager inputManager = gui.getInputManager();
        student.setOnMouseClicked(mouseEvent -> {
            if(isYourBoard() && inputManager.isActivatingCardEffect() && inputManager.getCardInActivation()==10 && clickedDiningStudentsColor.size()<2) {
                clickedDiningStudentsColor.add(studentID);
                student.setDisable(true);
                student.setOpacity(0.5);
                showCardPanel();
                if(clickedDiningStudentsColor.size()==2) {
                    hall.setEffect(new DropShadow(2,Color.BLACK));
                    hall.setOpacity(0.7);
                }
            }
        });
    }



    /**It shows the move button.*/
    protected void showMoveButton() {
        moveStudentPanel.setVisible(true);
        moveStudentPanel.setMouseTransparent(false);
        movedText.setText("Student Moved : "+gui.getGame().getStudentMoved());
    }


    /**It hides the move button.*/
    protected void hideMoveButtons() {
        moveStudentPanel.setVisible(false);
        moveStudentPanel.setMouseTransparent(true);
        //refresh();
    }

    /**It hides the card panel.*/
    protected void hideCardPanel() {
        popupCardPanel.setVisible(false);
        popupCardPanel.setMouseTransparent(true);
    }

    /**It shows the card panel.*/
    protected void showCardPanel() {
        if(!isYourBoard())
            return;
        popupCardPanel.setVisible(true);
        popupCardPanel.setMouseTransparent(false);
        if (gui.getInputManager().getCardInActivation() == 7) {
            int leftToSelect = gui.getInputManager().getNumberOfStudentSelectedFromCharacter() - clickedEntranceStudentsColor.size();
            studToSelect.setText(leftToSelect + "");
        }
        if(gui.getInputManager().getCardInActivation() == 10){
            int leftToSelect = Math.max(clickedEntranceStudentsColor.size() , clickedDiningStudentsColor.size())-Math.min(clickedEntranceStudentsColor.size() , clickedDiningStudentsColor.size());
            studToSelect.setText(leftToSelect + "");
        }
    }

    /**It changes the scene into <i>board scene</i>>*/
    public void moveToBoard() {
        moveToDining();
       /// refresh();
    }

    /**It changes the scene into <i>island scene</i>>*/
    public void sendToIsland() {
        chooseTargetIsland();
    }

    /**It changes the scene into <i>character scene</i>>*/
    public void moveToCharacter() {
        gui.getInputManager().resetEffectActivation();
        gui.showCharacters();
    }

    /**
     * It triggers character card effect activation.
     */
    public void activateCharacter() {
        if (gui.getInputManager().getCardInActivation() == 7) {
            int size=clickedEntranceStudentsColor.size();
            if(size<4 && size>0 && size==gui.getInputManager().getNumberOfStudentSelectedFromCharacter()) {
                gui.getInputManager().useCharacter7(clickedEntranceStudentsColor);
                clickedEntranceStudentsColor=new ArrayList<>();
                hideCardPanel();
                initialize();
            }
        }
        if (gui.getInputManager().getCardInActivation() == 10) {
            int entrancePicked=clickedEntranceStudentsColor.size();
            int diningPicked = clickedDiningStudentsColor.size();
            if(entrancePicked == diningPicked && entrancePicked<3 && entrancePicked>0){
                gui.getInputManager().useCharacter10(clickedEntranceStudentsColor,clickedDiningStudentsColor);
                clickedEntranceStudentsColor=new ArrayList<>();
                clickedDiningStudentsColor=new ArrayList<>();
                hideCardPanel();
                initialize();
            }
        }
    }
}
