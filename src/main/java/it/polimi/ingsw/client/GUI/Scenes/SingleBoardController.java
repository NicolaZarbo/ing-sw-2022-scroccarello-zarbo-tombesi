package it.polimi.ingsw.client.GUI.Scenes;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;


import java.util.ArrayList;
import java.util.List;

public class SingleBoardController extends BoardSceneController{

    public Pane entrance;
    public Pane hall;
    public Pane table;
    public Pane towers;
    public Pane popupPanel;
    private int boardNumber;

    private List<Circle> entranceList;
    private List<ArrayList<Circle>> diningRows;
    private List<ImageView> towerList;
    private List<Polygon> teacherList;
    public SingleBoardController() {
        this.boardNumber = BoardSceneXController.counterBoard;
    }
    private void initEntrance(){
        entranceList= new ArrayList<>();
        for (Node student:entrance.getChildren()) {
            entranceList.add((Circle)student);
        }
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
    }
    private void initTable(){
        teacherList=new ArrayList<>();
        for (Node teacher :table.getChildren()) {
            teacherList.add((Polygon) teacher);
        }
    }
    private void initTowers(){
        towerList=new ArrayList<>();
        for (Node tower:towers.getChildren()) {
            towerList.add((ImageView) tower);
        }
    }
    @Override
    public void initialize() {
        popupPanel.setVisible(false);
        popupPanel.setMouseTransparent(true);
        initEntrance();
        initTowers();
        initTable();
        initDiningRoom();
        super.setEntrance(entranceList,boardNumber+1);
        super.setHall(diningRows,boardNumber+1);
        super.setTowers(towerList,boardNumber+1);
        setProfessors(teacherList,boardNumber+1);
    }
    public void refresh(){
        super.setEntrance(entranceList,boardNumber+1);
        super.setHall(diningRows,boardNumber+1);
        super.setTowers(towerList,boardNumber+1);
        setProfessors(teacherList,boardNumber+1);
    }

    @Override
    protected void showMoveButton() {
        popupPanel.setVisible(true);
        popupPanel.setMouseTransparent(false);
    }

    @Override
    protected void hideMoveButtons() {
        popupPanel.setVisible(false);
        popupPanel.setMouseTransparent(true);
        refresh();
    }

    public void moveToBoard() {
        super.moveToDining();
    }

    public void sendToIsland() {
        super.chooseTargetIsland();
    }
}
