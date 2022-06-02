package it.polimi.ingsw.client.GUI.Scenes;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.enumerations.SceneEnum;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BoardSceneController extends SceneController{

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
        }
        playerOwner=player-1;
       // clickedEntranceStudentsColor=new ArrayList<>();
        List<Integer> playerEntrance=gui.getGame().getPlayers().get(player-1).getBoard().getEntrance();
        int id;
        for(int i=0;i<playerEntrance.size();i++){
            Image img;
            id= playerEntrance.get(i);
            if(id!=-1){
                setEntranceClickable(entrance.get(i),id/26);
                switch(id/26){
                    case 0->{
                      img=new Image("images/students/student_red.png");
                      entrance.get(i).setFill(new ImagePattern(img));
                      entrance.get(i).setVisible(true);
                    }
                    case 1 ->{
                        img=new Image("images/students/student_yellow.png");
                        entrance.get(i).setFill(new ImagePattern(img));
                        entrance.get(i).setVisible(true);
                    }
                    case 2->{
                        img=new Image("images/students/student_green.png");
                        entrance.get(i).setFill(new ImagePattern(img));
                        entrance.get(i).setVisible(true);
                    }
                    case 3->{
                        img=new Image("images/students/student_blue.png");
                        entrance.get(i).setFill(new ImagePattern(img));
                        entrance.get(i).setVisible(true);
                    }
                    case 4->{
                        img=new Image("images/students/student_pink.png");
                        entrance.get(i).setFill(new ImagePattern(img));
                        entrance.get(i).setVisible(true);
                    }

                }
                entrance.get(i).setDisable(false);
            }else {
                entrance.get(i).setVisible(false);// the student must be made not visible when absent and also not clickable
                entrance.get(i).setDisable(true);
            }
        }
    }

    public void setHall(List<ArrayList<Circle>> hall, int player){
        for (ArrayList<Circle> list:hall) {
            for (Circle stud:list) {
                stud.setVisible(false);
                stud.setDisable(true);
            }
        }
        clickedDiningStudentsColor=new ArrayList<>();
        Integer[][] diningroom=gui.getGame().getPlayers().get(player-1).getBoard().getDiningRoom();
        for(int i=0;i<diningroom.length;i++){
            for(int j=0;j<diningroom[i].length;j++){
                Image img;
                int id= diningroom[i][j];
                if(id!=-1){
                    switch(id/26){
                        case 0->{
                            img=new Image("images/students/student_red.png");
                            hall.get(i).get(j).setFill(new ImagePattern(img));
                            hall.get(i).get(j).setVisible(true);
                        }
                        case 1 ->{
                            img=new Image("images/students/student_yellow.png");
                            hall.get(i).get(j).setFill(new ImagePattern(img));
                            hall.get(i).get(j).setVisible(true);
                        }
                        case 2->{
                            img=new Image("images/students/student_green.png");
                            hall.get(i).get(j).setFill(new ImagePattern(img));
                            hall.get(i).get(j).setVisible(true);
                        }
                        case 3->{
                            img=new Image("images/students/student_blue.png");
                            hall.get(i).get(j).setFill(new ImagePattern(img));
                            hall.get(i).get(j).setVisible(true);
                        }
                        case 4->{
                            img=new Image("images/students/student_pink.png");
                            hall.get(i).get(j).setFill(new ImagePattern(img));
                            hall.get(i).get(j).setVisible(true);
                        }
                    }
                    hall.get(i).get(j).setDisable(false);
                }else {
                    hall.get(i).get(j).setVisible(false);
                    hall.get(i).get(j).setDisable(true);
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
                    case 0->{
                        img=new Image("images/teachers/teacher_red.png");
                        table.get(i).setFill(new ImagePattern(img));
                        table.get(i).setVisible(true);
                    }
                    case 1->{
                        img=new Image("images/teachers/teacher_yellow.png");
                        table.get(i).setFill(new ImagePattern(img));
                        table.get(i).setVisible(true);
                    }
                    case 2->{
                        img=new Image("images/teachers/teacher_green.png");
                        table.get(i).setFill(new ImagePattern(img));
                        table.get(i).setVisible(true);
                    }
                    case 3->{
                        img=new Image("images/teachers/teacher_blue.png");
                        table.get(i).setFill(new ImagePattern(img));
                        table.get(i).setVisible(true);
                    }
                    case 4->{
                        img=new Image("images/teachers/teacher_pink.png");
                        table.get(i).setFill(new ImagePattern(img));
                        table.get(i).setVisible(true);
                    }
                }
            }
        }
    }
    public void setTowers(List<ImageView>towers,int player){
        int towercolor=gui.getGame().getPlayers().get(player-1).getTowerColor();
        int towersleft=gui.getGame().getPlayers().get(player-1).getBoard().getTowersLeft();
        switch(towercolor){
            case 0->{
                //black
                Image img=new Image("images/towers/black_tower.png");
                for(int i=0;i<towersleft;i++){
                    towers.get(i).setImage(img);
                    towers.get(i).setVisible(true);
                }
            }
            case 1->{
                //white
                Image img=new Image("images/towers/white_tower.png");
                for(int i=0;i<towersleft;i++){
                    towers.get(i).setImage(img);
                    towers.get(i).setVisible(true);
                }
            }
            case 2->{
                //grey
                Image img=new Image("images/towers/grey_tower.png");
                for(int i=0;i<towersleft;i++){
                    towers.get(i).setImage(img);
                    towers.get(i).setVisible(true);
                }
            }

        }
    }
    public void setPlayerName(Text textbox, int player){
        String name=gui.getGame().getPlayers().get(player-1).getUsername();
        textbox.setText("Player: "+name);
    }
    public void sendToHand(){
        gui.showHand();
    }
    public void sendToMap(){
        gui.showIslands();
    }
    /**
     * used in initialize to set the present student Clickable based on some need
     * @param clickedColor the color of the student (id/26) */
    protected void setEntranceClickable(Circle student, int clickedColor){
        clickedEntranceStudentsColor=new ArrayList<>();//fixme make distinctions on saved student
        student.setDisable(false);
        student.setOnMouseClicked(mouseEvent -> {
            if(isYourBoard()) {
                //clickedEntranceStudentsColor.add(clickedColor);
                gui.getInputManager().saveSelectedStud(clickedColor);
                if (gui.getGame().getState() == GameState.actionMoveStudent && !gui.getInputManager().isActivatingCardEffect()) {
                    showMoveButton();
                }
            }
        });
    }
    protected abstract void showMoveButton();
    protected abstract void hideMoveButtons();
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
    protected void setDiningRoomClickable(Circle student, int clickedColor){
        student.setDisable(false);
        student.setOnMouseClicked(mouseEvent -> {
            if(isYourBoard())
                clickedDiningStudentsColor.add(clickedColor);
        });
    }

}
