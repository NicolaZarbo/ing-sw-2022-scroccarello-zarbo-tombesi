package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapSceneController extends SceneController {
    public Pane island_container;
    public Pane islandStudent_container;
    public Pane bridge_container;
    public Pane motherZones_container;
    public Pane cloudStudents_container;
    public Pane cloud_container;
    public Pane tower_container;
    public Text info;
    private List<Pane> containers;
    public ImageView hoverBackGround;
    private List<ImageView> islandStudentPlaces;
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
        saveContainers();
        initClouds();
        initIslands();
        initTowers();
        initBridges();
        initMotherZones();
        initHoverIsland();
        setCloud();
        setIslands();
        prepareForContext();
    }
    /** used to modify some properties based on the state*/
    private void prepareForContext(){
        switch (view.getState()){
            case actionMoveMother -> {
                setOtherContainerTransparent(island_container);
                info.setText("Select a target island for Mother");
                moveMotherContext();
            }
            case actionMoveStudent -> {
                setOtherContainerTransparent(island_container);
                info.setText("Select a target island for the student");
            }
            case planPlayCard -> {
                setOtherContainerTransparent(island_container);
                info.setText("go to hand to choose a card");
            }
            case actionChooseCloud ->{
                setOtherContainerTransparent(cloud_container);
                info.setText("Choose a cloud to refill");
                chooseCloudContext();
            }
        }
    }
    private void chooseCloudContext(){
        //todo make only the not empty clouds clickable and highlighted
    }
    private void moveMotherContext(){
        //todo make only the available island highlighted and clickable
    }
    private void saveContainers(){
        containers= new ArrayList<>();
        containers.add(island_container);
        containers.add(islandStudent_container);
        containers.add(bridge_container);
        containers.add(tower_container);
        containers.add(motherZones_container);
        containers.add(cloud_container);
        containers.add(cloudStudents_container);
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
            islandByNumber.get(islandID+1).setDisable(false);
            clickChooseIsland( islandByNumber.get(islandID+1));
            hoverShowInside(islandByNumber.get(islandID+1));
            setTower(island);
            setMotherZone(island);
        }
    }
    private void initHoverIsland(){
        islandStudent_container.setVisible(false);
        islandStudentPlaces= new ArrayList<>();
        for (Node student:islandStudent_container.getChildren()) {
            islandStudentPlaces.add((ImageView) student);
        }
    }
    private void setStudentsInHover(List<Integer> students){
        int i=0;
        for (Integer stud:students) {
            islandStudentPlaces.get(i).setImage(studentColorPath(stud));
            islandStudentPlaces.get(i).setVisible(true);
            i++;
        }//fixme this works when showing less than 10 stud
    }
    private void resetStudentsHover(){
        for (ImageView place:islandStudentPlaces) {
            place.setVisible(false);
        }
    }
    /** used so that only the useful panel can receive mouseEvent, other-ways the other panels could block the event */
    private void setOtherContainerTransparent(Pane container){
        for (Pane cont:containers) {
            cont.setMouseTransparent(true);
        }
        container.setMouseTransparent(false);
    }

    private void hoverShowInside(Circle island){
        //SimplifiedIsland viewIsland= view.getIslands().get(Integer.parseInt(island.getId().substring(6)));
        //int islandImage= (islandID%3)+1;
        //List<Integer> students= viewIsland.getStudents();
        island.setDisable(false);
        island.setOnMouseEntered(mouseEvent -> {
            SimplifiedIsland isl= view.getIslands().get(Integer.parseInt(((Circle)mouseEvent.getSource()).getId().substring(6)));
            int islandImage= ((isl.getIslandId()+1)%3)+1;
            resetStudentsHover();
            setStudentsInHover(isl.getStudents());
            hoverBackGround.setImage(new Image("images/simple_elements/island"+islandImage+".png"));
            hoverBackGround.setVisible(true);
            islandStudent_container.setVisible(true);
        });
        island.setOnMouseExited(mouseEvent -> {
            hoverBackGround.setVisible(false);
            islandStudent_container.setVisible(false);
        });
    }
    private void clickChooseIsland(Circle island){
        //island.setDisable(false);
        //int id=Integer.parseInt(island.getId().substring(6));
        island.setOnMouseClicked(mouseEvent -> {
            int islandID=Integer.parseInt(((Circle)mouseEvent.getSource()).getId().substring(6));

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
            clickChooseCloud(cloudByNumber.get(cloudId+1),cloudId);

        }
    }
    private void clickChooseCloud(Circle cloud, int cloudID){
        cloud.setDisable(false);
        cloud.setOnMouseClicked(mouseEvent -> {
            gui.getInputManager().cloudChoose(cloudID-1);

        });
    }
    private void setCloudStudents(Integer[] cloud, int cloudNumber){
        List<Circle> cloudStud=cloudStudentsByNumber.get(cloudNumber);
        int i=0;
        for (Integer stud:cloud) {
            cloudStud.get(i).setFill(new ImagePattern(studentColorPath(stud)));
            cloudStud.get(i).setVisible(true);
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

    public void goToHand() {
        gui.showHand();
    }

    public void goToBoard() {
        gui.showBoards();
    }
}
