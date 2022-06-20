package it.polimi.ingsw.client.GUI.Scenes;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.enumerations.SceneEnum;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class BoardSceneController extends SceneController{// todo eventually we could merge this class with singleBoardController

    protected GUI gui;
    private int playerOwner;//the same id of view
    protected ArrayList<Integer> clickedEntranceStudentsColor;
    protected ArrayList<Integer> clickedDiningStudentsColor;

    public BoardSceneController() {
        this.gui= GuiInputManager.getGui();
    }

    public abstract void initialize();


    /**displays the tokens in the entrance*/
    public void setEntrance(List<Circle> entrance, int player){
        for (Circle stud:entrance) {
            stud.setDisable(true);
            stud.setVisible(false);
            stud.setStyle("-fx-stroke-width: 0");
        }
        playerOwner=player-1;
        List<Integer> playerEntrance=gui.getGame().getPlayers().get(player-1).getBoard().getEntrance();
        int id;
        for(int i=0;i<playerEntrance.size();i++){
            Image img;
            id= playerEntrance.get(i);
            if(id!=-1){
                if(gui.getGame().isYourTurn()&& gui.getGame().getState()==GameState.actionMoveStudent)
                    entrance.get(i).setCursor(new ImageCursor(new Image("images/pointer/pickUpPointer.png")));
                setEntranceClickable(entrance.get(i),id/26);
                img=getStudentImage(id);
                entrance.get(i).setFill(new ImagePattern(img));
                entrance.get(i).setVisible(true);
                entrance.get(i).setDisable(false);
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

    public void setHall(List<ArrayList<Circle>> hall, int player){
        for (ArrayList<Circle> list:hall) {
            for (Circle stud:list) {
                stud.setVisible(false);
                stud.setDisable(true);
                stud.setStyle("-fx-stroke-width: 0");
            }
        }
        clickedDiningStudentsColor=new ArrayList<>();
        Integer[][] diningroom=gui.getGame().getPlayers().get(player-1).getBoard().getDiningRoom();
        for(int i=0;i<diningroom.length;i++){
            for(int j=0;j<diningroom[i].length;j++){
                Image img;
                int id= diningroom[i][j];
                if(id!=-1){
                    img=getStudentImage(id);
                    setDiningRoomClickable(hall.get(i).get(j),id/26);
                    hall.get(i).get(j).setFill(new ImagePattern(img));
                    hall.get(i).get(j).setVisible(true);
                    hall.get(i).get(j).setDisable(false);
                }
            }
        }
    }

    /**displays professors if the player has*/
    public void setProfessors(List<Polygon> table, int player){
        Integer[] professors=gui.getGame().getPlayers().get(playerOwner).getBoard().getProfessorTable();
        for (Polygon teach:table) {
            teach.setVisible(false);
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
                table.get(i).setFill(new ImagePattern(img));
                table.get(i).setVisible(true);
            }
        }
    }
    public void setTowers(List<ImageView>towers,int player){
        int towerColor=gui.getGame().getPlayers().get(player-1).getTowerColor();
        int towersLeft=gui.getGame().getPlayers().get(player-1).getBoard().getTowersLeft();
        Image img;
        switch(towerColor){
            case 0->img=new Image("images/towers/black_tower.png");
            case 1->img=new Image("images/towers/white_tower.png");
            case 2->img=new Image("images/towers/grey_tower.png");
            default -> {throw new RuntimeException("not a towerColor");}
        }
        for(int i=0;i<towersLeft;i++){
            towers.get(i).setImage(img);
            towers.get(i).setVisible(true);
        }
    }
    public void setPlayerName(Text textBox, int player){
        String name=gui.getGame().getPlayers().get(player-1).getUsername();
        textBox.setText("Player: "+name);
    }
    /**
     * used in initialize() to set the present student Clickable based on the context
     * @param studentId the id of the student */
    protected void setEntranceClickable(Circle student, int studentId){//fixme change clicked color in favor of id
        int clickedColor= studentId/26;
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
                inputManager.saveSelectedStud(clickedColor);
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
            @Override
            public void handle(MouseEvent mouseEvent) {
                moveToDining();
                student.removeEventHandler(MouseEvent.MOUSE_CLICKED,this);
            }
        });
    }
    private void effect7EntranceHandler(Circle student, int studentId){
        GuiInputManager inputManager = gui.getInputManager();
        if(clickedEntranceStudentsColor.size()>3)
            return;
        showCardPanel();

        /*
        inputManager.addSavedSelectedStud(inputManager.getSingleStudent());
        int temp=inputManager.getNumberOfStudentSelectedFromCharacter();
        inputManager.setNumberOfStudentSelectedFromCharacter(temp-1);
        if(inputManager.getNumberOfStudentSelectedFromCharacter()==1){
            //todo logic
        }

         */
    }
    private void effect10EntranceHandler(Circle student, int studId){
        if(clickedEntranceStudentsColor.size()<2)
            return;
        clickedEntranceStudentsColor.add(studId);
        student.setOpacity(0.5);
        student.setDisable(true);
        if(clickedEntranceStudentsColor.size()==2 && clickedDiningStudentsColor.size()==2)
            gui.getInputManager().useCharacter10(clickedEntranceStudentsColor,clickedDiningStudentsColor);
        //todo add a panel with a button to accept 1 to 1 exchange
        //panel.setVisible(true)
        //panel.setMouseTransparent(false)
    }
    /** Used to add a shadow to a student on mouse over*/
    private void setStudentColoredShadow(Circle student, int color){
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
    protected abstract void showMoveButton();
    protected abstract void hideMoveButtons();

    protected abstract void hideCardPanel();
    protected abstract void showCardPanel();
    public void setPlayerOwner(int owner){
        playerOwner=owner;
    }
    public boolean isYourBoard(){
        return playerOwner==gui.getGame().getPersonalPlayer().getId();
    }
    /** used to put the selected student inside the dining room */
    protected void moveToDining(){
        GuiInputManager inputManager=gui.getInputManager();
        inputManager.moveToBoard();
        this.clickedEntranceStudentsColor=new ArrayList<>();
        //fixme is the board being refreshed?
        initialize();//does this make sense? and what about the refresh method in sub?
        hideMoveButtons();
    }

    /** used to show the map and prompt the user to choose an island */
    protected void chooseTargetIsland(){
        this.clickedEntranceStudentsColor=new ArrayList<>();
        gui.setScene(SceneEnum.MapScene);
        hideMoveButtons();
    }
    /** @param clickedColor studentID/26*/
    protected void setDiningRoomClickable(Circle student, int clickedColor){//todo change color for id
        student.setDisable(false);
        GuiInputManager inputManager = gui.getInputManager();
        student.setOnMouseClicked(mouseEvent -> {
            if(isYourBoard() && inputManager.isActivatingCardEffect() && inputManager.getCardInActivation()==10 && clickedDiningStudentsColor.size()<2) {
                clickedDiningStudentsColor.add(clickedColor);
                student.setDisable(true);
                student.setOpacity(0.5);
                if(clickedDiningStudentsColor.size()==2) {
                    //todo make dining translucent
                }
            }
        });
    }

}
