package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapSceneController extends SceneController {
    public Pane island_container;
    public Pane bridge_container;
    public Pane motherZones_container;
    public Pane cloudStudents_container;
    public Pane cloud_container;
    public Pane tower_container;
    public Pane studentHover_container;
    private Pane scene;
    private GUI gui;
    private CentralView view;
    private Map<Integer, Circle> islandByNumber;
    private Map<Integer, Rectangle> towerByIslandNumber;
    private Map<Integer, Circle> cloudByNumber;
    private Map<Integer, List<Circle>> cloudStudentsByNumber;
    private Map<Integer, Rectangle> motherZoneOfIsland;
    private Map<Integer, Rectangle> bridgeByIslandBefore;

    public MapSceneController() {
        gui= GuiInputManager.getGui();
        view=gui.getGame();
    }


    @Override
    public void initialize() {
        initClouds();
        initIslands();
        initTowers();
        initBridges();
        initMotherZones();

        setCloud();
        setIslands();
    }
    private void setTower(SimplifiedIsland island){
        //todo check if the number on island reflect those on view
        int color=island.getTowerColor();
        int islandId=island.getIslandId()+1;
        if(color!=-1){
            Image img;
            switch (color){
                case 0->{
                    //black
                    img=new Image("images/towers/black_tower.png");
                }

                case 1-> {
                    //white
                    img = new Image("images/towers/white_tower.png");
                }
                case 2->{
                    //grey
                    img=new Image("images/towers/grey_tower.png");
                }
                default -> {return;}
            }
            towerByIslandNumber.get(islandId).setFill(new ImagePattern(img));
            towerByIslandNumber.get(islandId).setVisible(true);
        }else towerByIslandNumber.get(islandId).setVisible(false);
    }
    private void setIslands(){
        List<SimplifiedIsland> islandsView= view.getIslands();
        List<SimplifiedIsland> fullIslandView= new ArrayList<>(islandsView);
        for (SimplifiedIsland island:islandsView) {
            setBridges(island);
            fullIslandView.addAll(island.getSubIsland());
        }
        for (SimplifiedIsland island:fullIslandView) {
            int islandID= island.getIslandId();
            int islandImage=(islandID%3)+1;
            Image img = new Image("images/simple_elements/island"+islandImage+".png");
            islandByNumber.get(islandID+1).setFill(new ImagePattern(img));
           clickChooseIsland( islandByNumber.get(islandID+1),island.getIslandId());
            setTower(island);
            setMotherZone(island);
        }
    }
    private void clickChooseIsland(Circle island, int islandID){
        island.setDisable(false);
        island.setOnMouseClicked(mouseEvent -> {
            if(view.getState()==GameState.actionMoveStudent && gui.getInputManager().getSelectedStudents().size()==1)
                gui.getInputManager().moveToIsland(islandID);
            if(view.getState()==GameState.actionMoveMother)
                gui.getInputManager().moveToIsland(islandID);
        });
    }
    private void setCloud(){
        Image cloudImage;
        for (Integer[] cloud:view.getClouds()) {
            int cloudId= view.getClouds().lastIndexOf(cloud);
            int cloudImg=(cloudId%4)+1;
            cloudImage= new Image("images/simple_elements/cloud_card_"+cloudImg+".png");
            cloudByNumber.get(cloudId+1).setFill(new ImagePattern(cloudImage));
            cloudByNumber.get(cloudId+1).setVisible(true);
            setCloudStudents(cloud,cloudId+1);
            if(view.getState()== GameState.actionChooseCloud)
                clickChooseCloud(cloudByNumber.get(cloudId+1),cloudId);
            else cloudByNumber.get(cloudId+1).setDisable(true);
        }
    }
    private void clickChooseCloud(Circle cloud, int cloudID){
        cloud.setDisable(false);
        cloud.setOnMouseClicked(mouseEvent -> {
            view.chooseCloud(cloudID-1);
        });
    }
    private void setCloudStudents(Integer[] cloud, int cloudNumber){
        List<Circle> cloudStud=cloudStudentsByNumber.get(cloudNumber);
        int i=0;
        for (Integer stud:cloud) {
            cloudStud.get(i).setFill(new ImagePattern(studentColorPath(stud)));
            cloudStud.get(i).setVisible(true);//fixme why aren't they becoming visible???
            i++;
        }
    }
    private Image studentColorPath(int studId){
        return switch (studId/26){
            case 0->new Image("images/students/student_red.png");

            case 1 ->new Image("images/students/student_yellow.png");

            case 2->new Image("images/students/student_green.png");

            case 3-> new Image("images/students/student_blue.png");

            case 4-> new Image("images/students/student_pink.png");

            default -> throw new IllegalStateException("Unexpected value: " + studId / 26);
        };
    }
    private void setMotherZone(SimplifiedIsland island){
        int motherPosition= view.getMother();
        boolean isMotherOn = island.getIslandId()==motherPosition;
        Rectangle mZone=motherZoneOfIsland.get(island.getIslandId()+1);
        mZone.setVisible(isMotherOn);
        mZone.setFill(new ImagePattern(new Image("images/simple_elements/mother_nature.png")));
    }
    private void setBridges(SimplifiedIsland island){
        int mainId=island.getIslandId()+1;
        for (SimplifiedIsland subIsland:island.getSubIsland()) {
            int subId=subIsland.getIslandId()+1;
            if(subId<mainId){
                bridgeByIslandBefore.get(subId).setVisible(true);
            }else bridgeByIslandBefore.get(mainId).setVisible(true);
            setBridges(subIsland);
        }
    }

    private void initTowers(){
        towerByIslandNumber=new HashMap<>();
        for (Node towerPos:tower_container.getChildren()) {
            int islandTargetNumber = Integer.parseInt(towerPos.getId().substring(5));
            towerByIslandNumber.put(islandTargetNumber,(Rectangle) towerPos);
        }

    }
    private void initIslands(){
        islandByNumber= new HashMap<>();
        for (Node island:island_container.getChildren()) {
            int islandNumber= Integer.parseInt(island.getId().substring(6));
            islandByNumber.put(islandNumber, (Circle) island);
        }
    }

    private void initClouds(){
        cloudByNumber= new HashMap<>();
        cloudStudentsByNumber=new HashMap<>();
        for (Node cloud:cloud_container.getChildren()) {
            int cloudNumber= Integer.parseInt(cloud.getId().substring(5));
            cloudByNumber.put(cloudNumber, (Circle) cloud);
            cloud.setVisible(false);
        }
        for (Node studContainer:cloudStudents_container.getChildren()) {
            List<Circle> studs = new ArrayList<>();
            for (Node stud:((Pane)studContainer).getChildren()) {
                studs.add((Circle) stud);
                stud.setVisible(false);
            }
            int cloudNumber= Integer.parseInt(studContainer.getId().substring(5,6));
            cloudStudentsByNumber.put(cloudNumber,studs);
        }
    }
    private void initMotherZones(){
        motherZoneOfIsland= new HashMap<>();
        for (Node zone:motherZones_container.getChildren()) {
            int islandNumber= Integer.parseInt(zone.getId().substring(12));
            motherZoneOfIsland.put(islandNumber, (Rectangle) zone);
        }
    }
    private void initBridges() {
        bridgeByIslandBefore=new HashMap<>();
        for (Node bridge:bridge_container.getChildren()) {
            int islandBeforeNumber= Integer.parseInt(bridge.getId().substring(6));
            bridge.setVisible(false);
            bridgeByIslandBefore.put(islandBeforeNumber, (Rectangle) bridge);
        }

    }
    public Pane getPane() {
        return scene;
    }
}
