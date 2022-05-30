package it.polimi.ingsw.client.GUI.Scenes;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class BoardSceneController extends SceneController{

    protected GUI gui;

    public BoardSceneController() {
        this.gui= GuiInputManager.getGui();
    }

    public abstract void initialize();

    /**displays the tokens in the entrance*/
    public void setEntrance(ArrayList<Circle> entrance, int player){
        List<Integer> playerentrance=gui.getGame().getPlayers().get(player-1).getBoard().getEntrance();
        int id;
        for(int i=0;i<playerentrance.size();i++){
            Image img;
            id=playerentrance.get(i).intValue();
            if(id!=-1){
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
            }
        }
    }

    public void setHall(ArrayList<ArrayList<Circle>> hall, int player){
        Integer[][] diningroom=gui.getGame().getPlayers().get(player-1).getBoard().getDiningRoom();
        for(int i=0;i<diningroom.length;i++){
            for(int j=0;j<diningroom[i].length;j++){
                Image img;
                int id=diningroom[i][j].intValue();
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
                }
            }
        }
    }

    /**displays professors if the player has*/
    public void setProfessors(ArrayList<Polygon> table, int player){
        Integer[] professors=gui.getGame().getPersonalPlayer().getBoard().getProfessorTable();
        for(Integer i : professors){
            if(i.intValue()!=0){
                Image img;
                switch(i.intValue()){
                    case 0->{
                        img=new Image("images/teachers/teacher_red.png");
                        table.get(i.intValue()).setFill(new ImagePattern(img));
                        table.get(i.intValue()).setVisible(true);
                    }
                    case 1->{
                        img=new Image("images/teachers/teacher_yellow.png");
                        table.get(i.intValue()).setFill(new ImagePattern(img));
                        table.get(i.intValue()).setVisible(true);
                    }
                    case 2->{
                        img=new Image("images/teachers/teacher_green.png");
                        table.get(i.intValue()).setFill(new ImagePattern(img));
                        table.get(i.intValue()).setVisible(true);
                    }
                    case 3->{
                        img=new Image("images/teachers/teacher_blue.png");
                        table.get(i.intValue()).setFill(new ImagePattern(img));
                        table.get(i.intValue()).setVisible(true);
                    }
                    case 4->{
                        img=new Image("images/teachers/teacher_pink.png");
                        table.get(i.intValue()).setFill(new ImagePattern(img));
                        table.get(i.intValue()).setVisible(true);
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
}
