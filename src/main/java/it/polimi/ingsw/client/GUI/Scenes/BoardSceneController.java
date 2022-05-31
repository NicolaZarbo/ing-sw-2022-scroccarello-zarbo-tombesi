package it.polimi.ingsw.client.GUI.Scenes;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.enumerations.SceneEnum;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardSceneController extends SceneController{

    protected GUI gui;
    protected ArrayList<Integer> clickedEntranceStudentsColor;
    protected ArrayList<Integer> clickedDiningStudentsColor;


    public BoardSceneController() {
        this.gui= GuiInputManager.getGui();
    }

    public abstract void initialize();


    /**displays the tokens in the entrance*/
    public void setEntrance(ArrayList<Circle> entrance, int player){
        boolean canBeClicked= gui.getGame().getState()== GameState.actionMoveStudent && gui.getGame().getPersonalPlayer().getId()==player-1;//todo && not currently activating a character
        List<Integer> playerEntrance=gui.getGame().getPlayers().get(player-1).getBoard().getEntrance();
        int id;
        for(int i=0;i<playerEntrance.size();i++){
            Image img;
            id= playerEntrance.get(i);
            if(id!=-1){
                if(canBeClicked)
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

    public void setHall(ArrayList<ArrayList<Circle>> hall, int player){
        boolean canBeClicked=false;//currently activating a character? one of the effects targets studs on hall todo
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
    public void setProfessors(ArrayList<Polygon> table, int player){
        Integer[] professors=gui.getGame().getPersonalPlayer().getBoard().getProfessorTable();
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
    public void setTowers(ArrayList<ImageView>towers,int player){
        int towercolor=gui.getGame().getPlayers().get(player-1).getTowerColor();
        int towersleft=gui.getGame().getPlayers().get(player-1).getBoard().getTowersLeft();

        switch(towercolor){
            case 0->{
                //black
                Image img=new Image("images/towers/black_tower.png");
                for(int i=0;i<towersleft;i++){
                    towers.get(i).setImage(img);
                    towers.get(i).setVisible(true);
                }//todo make the others not visible
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
        student.setDisable(false);
        student.setOnMouseClicked(mouseEvent -> {clickedEntranceStudentsColor.add(clickedColor);
        });
    }
    protected void moveToDining(){
        GuiInputManager inputManager=gui.getInputManager();
        inputManager.moveToBoard(this.clickedEntranceStudentsColor.get(0));
        this.clickedEntranceStudentsColor=new ArrayList<>();
    }
    protected void chooseTargetIsland(){
        gui.getInputManager().saveSelectedStud(this.clickedEntranceStudentsColor);
        this.clickedEntranceStudentsColor=new ArrayList<>();
        gui.setScene(SceneEnum.MapScene);
    }
    /** @param clickedColor studentID/26*/
    protected void setDiningRoomClickable(Circle student, int clickedColor){
        student.setDisable(false);
        student.setOnMouseClicked(mouseEvent -> {clickedDiningStudentsColor.add(clickedColor);
        });
    }

}
