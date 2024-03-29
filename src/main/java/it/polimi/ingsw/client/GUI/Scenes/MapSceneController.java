package it.polimi.ingsw.client.GUI.Scenes;

import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.GUI.GuiInputManager;
import it.polimi.ingsw.enumerations.GameState;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.simplifiedobjects.SimplifiedIsland;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;

/**The handler of map scene.*/
public class MapSceneController extends SceneController {
    public Pane island_container;
    public Pane islandStudent_container;
    public Pane bridge_container;
    public Pane motherZones_container;
    public Pane cloudStudents_container;
    public Pane cloud_container;
    public Pane tower_container;
    public Text info;
    public ImageView hoverBackGround;
    public AnchorPane root;
    public AnchorPane map_container;
    public Pane overflown_students;
    public Button toHand;
    public Button toBoards;
    private List<ImageView> islandStudentPlaces;
    private final GUI gui;
    private CentralView view;
    private Map<Integer, Circle> islandByNumber;
    private Map<Integer, Rectangle> towerByIslandNumber;
    private Map<Integer, Circle> cloudByNumber;
    private Map<Integer, List<Circle>> cloudStudentsByNumber;
    private Map<Integer, Rectangle> motherZoneOfIsland;
    private Map<Integer, Rectangle> bridgeByIslandBefore;

    /**It creates the instance of map scene controller.*/
    public MapSceneController() {
        gui= GuiInputManager.getGui();
    }

    @Override
    public void initialize() {
        view=gui.getGame();
        root.setStyle("-fx-background-image:url(images/wallpapers/sky_no_title.png); -fx-background-position: center; -fx-background-size: 1236 863");
        root.setCursor(new ImageCursor(new Image("images/pointer/baseArrow.png")));
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

    /**It is used to modify the scene context (and actions possible) based on the state.*/
    private void prepareForContext(){
        if(!view.isYourTurn()){
            info.setText(view.getTurnPlayer()+" is now playing");
            return;
        }

        switch (view.getState()){
            case actionMoveMother -> {
                moveMotherContext();
            }
            case actionMoveStudent -> {
                setOtherContainerTransparent(island_container);
                moveStudentContext();
            }
            case planPlayCard -> {
                setOtherContainerTransparent(island_container);
                info.setText("go to hand to choose a card");
            }
            case actionChooseCloud ->{
                chooseCloudContext();
            }
        }
    }

    /**It resets the effect activation when changing scene.*/
    private void breakActivationChangeScene(){
        GuiInputManager manager= gui.getInputManager();
        toBoards.setOnMouseClicked(event -> { manager.resetEffectActivation(); goToBoard();});
        toHand.setOnMouseClicked(event -> {manager.resetEffectActivation(); goToHand();});
    }

    /**It sets the move student context. It allows the player to drop a student on island.*/
    private void moveStudentContext(){
        if(gui.getInputManager().isActivatingCardEffect()) {
            breakActivationChangeScene();
            info.setText("Select where to drop the student");
            return;
        }
            GuiInputManager inputManager= gui.getInputManager();
        if(inputManager.hasSelectedStudent())
            info.setText("Select a target island for the student");
        else info.setText("Go to bord and choose a student to move out of entrance");
    }
    /**It sets the choice cloud context. It allows the player to refill his entrance with the students from a target cloud.*/
    private void chooseCloudContext(){
        setOtherContainerTransparent(cloud_container);
        info.setText("Choose a cloud to refill");
        for (Integer[] cloud:view.getClouds()) {
            int i = view.getClouds().lastIndexOf(cloud)+1;
            if(cloud[0]==-1){
                cloudByNumber.get(i).setDisable(true);
                cloudByNumber.get(i).setOpacity(0.4);
                cloudByNumber.get(i).setCursor(new ImageCursor(new Image("images/pointer/basePointer.png")));
            }else {
                cloudByNumber.get(i).setCursor(new ImageCursor(new Image("images/pointer/pickUpPointer.png")));
                cloudByNumber.get(i).setDisable(false);
            }

        }
    }

    /**It sets the move mother context. It allows the player to move mother nature between islands of a maximum amount of steps (decided in planning phase).*/
    private void moveMotherContext(){
        if(gui.getInputManager().isActivatingCardEffect()){
            breakActivationChangeScene();
            info.setText("Select where to drop the student");
            return;
        }
        setOtherContainerTransparent(island_container);
        info.setText("Select a target island for Mother");
        int maxSteps= (view.getCardYouPlayed()+2)/2;
        int motherPos=view.getMother();
        for (int i = 1; i < 13; i++) {
            islandByNumber.get(i).setDisable(true);
            islandByNumber.get(i).setOpacity(0.4);
        }
        int mod;
        int howManyAvailableIslands=view.getIslands().size();
        for (int i = motherPos+1; i <= motherPos+maxSteps; i++) {
            if(i<howManyAvailableIslands)
                mod=i;
            else mod=i-howManyAvailableIslands;
            SimplifiedIsland presentIsland= view.getIslands().get(mod);
            islandByNumber.get(presentIsland.getIslandId()+1).setOpacity(1);
            islandByNumber.get(presentIsland.getIslandId()+1).setDisable(false);
        }

    }

    /**It fills the containers' list with all the containers of the scene.*/
    private void saveContainers(){
        List<Pane> containers = new ArrayList<>();
        containers.add(island_container);
        containers.add(islandStudent_container);
        containers.add(bridge_container);
        containers.add(tower_container);
        containers.add(motherZones_container);
        containers.add(cloud_container);
        containers.add(cloudStudents_container);
        setContainerList(containers);
    }

    /**It sets a tower on an island.
     * @param island the island on which to set the tower*/
    private void setTower(SimplifiedIsland island){
        int color=island.getTowerColor();
        int islandId=island.getIslandId()+1;
        if(color!=-1){
            Image img;
            switch (color){
                case 0->img=new Image("images/towers/black_tower.png");
                case 1-> img = new Image("images/towers/white_tower.png");
                case 2->img=new Image("images/towers/grey_tower.png");
                default -> {return;}
            }
            towerByIslandNumber.get(islandId).setFill(new ImagePattern(img));
            towerByIslandNumber.get(islandId).setEffect(new DropShadow(5, Color.BLACK));
            towerByIslandNumber.get(islandId).setVisible(true);
        }else towerByIslandNumber.get(islandId).setVisible(false);
    }

    /**It sets the island elements of the scene, with their students and their towers (also with merge bridge).*/
    private void setIslands(){
        List<SimplifiedIsland> islandsView= view.getIslands();
        List<SimplifiedIsland> fullIslandView= new ArrayList<>(islandsView);
        for (SimplifiedIsland island:islandsView) {
            setBridges(island);
            fullIslandView.addAll(view.getEverySubIsland(island));
        }
        for (SimplifiedIsland island:fullIslandView) {
            int islandID= island.getIslandId();
            int islandImage=(islandID%3)+1;
            Image img = new Image("images/simple_elements/island"+islandImage+".png");
            islandByNumber.get(islandID+1).setFill(new ImagePattern(img));
            ImageCursor imgCursor;
            if(view.getState()==GameState.actionMoveMother && !gui.getInputManager().isActivatingCardEffect())
                imgCursor=new ImageCursor(new Image("images/pointer/basePointer.png"));
            else if ((gui.getInputManager().isActivatingCardEffect() || view.getState()==GameState.actionMoveStudent) && gui.getInputManager().hasSelectedStudent()  ) {
                imgCursor= new ImageCursor(studentColorPath(gui.getInputManager().getSingleStudent(),false));
            } else imgCursor=new ImageCursor(new Image("images/pointer/lookUp.png"));
            islandByNumber.get(islandID+1).setCursor(imgCursor);
            islandByNumber.get(islandID+1).setDisable(false);
            islandByNumber.get(islandID+1).setOpacity(1);
            setClickChooseIsland( islandByNumber.get(islandID+1));
            hoverShowInside(islandByNumber.get(islandID+1));
            setTower(island);
            setMotherZone(island);
        }
    }

    /**It initializes the mouse over event on island.*/
    private void initHoverIsland(){
        islandStudent_container.setVisible(false);
        hoverBackGround.setMouseTransparent(true);
        islandStudentPlaces= new ArrayList<>();
        for (Node student:islandStudent_container.getChildren()) {
            if(!(student instanceof Pane) )
                islandStudentPlaces.add((ImageView) student);
        }
    }

    /**It sets students on mouse over on island.
     * @param students the list of students over the island*/
    private void setStudentsInHover(List<Integer> students){
        int i=0;
        ArrayList<Integer>overflow= new ArrayList<>();
        for (Integer stud:students) {
            if(i>8){
                overflow.add(stud);
            }else {
                islandStudentPlaces.get(i).setImage(studentColorPath(stud, true));
                islandStudentPlaces.get(i).setVisible(true);
            }
            i++;
        }
            setOverflow(overflow);
    }

    /**It sets the overflown students.
     * @param overflown the list of students to set*/
    private void setOverflow(List<Integer> overflown){
        int[]colorNumber=new int[5];
        Arrays.fill(colorNumber,0);
        for (Integer ovr:overflown) {
            colorNumber[ovr/26]++;
        }
        int imageCounter=0, numberCounter=0;
        for (Node child:overflown_students.getChildren()) {
            if(child instanceof ImageView) {
                child.setVisible(colorNumber[imageCounter] != 0);
                ((ImageView) child).setImage(studentColorPath(imageCounter * 26, false));
                imageCounter++;
            }
            if(child instanceof Text) {
                child.setVisible(colorNumber[numberCounter] != 0);
                ((Text) child).setText(colorNumber[numberCounter] + "");
                numberCounter++;
            }
        }
    }

    /**It resets the mouse over pop-up*/
    private void resetStudentsHover(){
        for (ImageView place:islandStudentPlaces) {
            place.setVisible(false);
        }
    }
    /**It is used so that only the useful panel can receive mouse event, other-ways the other panels could block the event.*/
    private void setOtherContainerTransparent(Pane container){
        super.setOthersContainerMouseTransparent(container);
    }

    /**It shows after a mouse over event the content of the island.
     * @param island the island to show*/
    private void hoverShowInside(Circle island){
        island.setDisable(false);
        island.setOnMouseEntered(mouseEvent -> {
            SimplifiedIsland isl= view.getIslandById(Integer.parseInt(((Circle)mouseEvent.getSource()).getId().substring(6))-1);
            int islandImage= ((isl.getIslandId())%3)+1;
            resetStudentsHover();
            setStudentsInHover(isl.getStudents());
            hoverBackGround.setImage(new Image("images/simple_elements/island"+islandImage+".png"));
            hoverBackGround.setVisible(true);
            islandStudent_container.setVisible(true);
            map_container.setOpacity(0.7);
            DropShadow islandShadow = new DropShadow(15, Color.DARKRED);
            island.setEffect(islandShadow);
            hoverBackGround.setEffect(islandShadow);
            DropShadow blackShadow= new DropShadow(10,Color.BLACK);
            islandStudent_container.setEffect(blackShadow);
            if(view.isYourTurn()&& view.getState()==GameState.actionMoveMother && !gui.getInputManager().isActivatingCardEffect()){
                motherZoneOfIsland.get(isl.getIslandId()+1).setVisible(true);
                motherZoneOfIsland.get(isl.getIslandId()+1).setOpacity(0.6);
            }
        });
        island.setOnMouseExited(mouseEvent -> {
            island.setEffect(null);
            map_container.setOpacity(1);
            hoverBackGround.setVisible(false);
            islandStudent_container.setVisible(false);
            SimplifiedIsland isl= view.getIslandById(Integer.parseInt(((Circle)mouseEvent.getSource()).getId().substring(6))-1);
            setMotherZone(isl);
        });
    }

    /**It selects the island after the click event.
     * @param island the selected island*/
    private void setClickChooseIsland(Circle island){
        island.setOnMouseClicked(mouseEvent -> {
            if(!view.isYourTurn())
                return;
            int islandID=Integer.parseInt(((Circle)mouseEvent.getSource()).getId().substring(6))-1;
            if(view.getState()==GameState.actionMoveMother) {
                gui.getInputManager().moveMotherTo(islandID);
                info.setText("wait for your turn");
                cloud_container.setDisable(true);
            }
            if(view.getState()==GameState.actionMoveStudent && gui.getInputManager().hasSelectedStudent() && !gui.getInputManager().isActivatingCardEffect()) {
                gui.getInputManager().moveToIsland(islandID);
                for (int i = 1; i < 13; i++)
                    islandByNumber.get(i).setDisable(false);
            }
            if(gui.getInputManager().isActivatingCardEffect())
                gui.getInputManager().useCharacter1(islandID);
        });
    }

    /**It shows the clouds on the scene.*/
    private void setCloud(){
        Image cloudImage;
        for (Integer[] cloud:view.getClouds()) {
            int cloudId= view.getClouds().lastIndexOf(cloud);
            int cloudImg=(cloudId%4)+1;
            cloudImage= new Image("images/simple_elements/cloud_card_"+cloudImg+".png");
            cloudByNumber.get(cloudId+1).setFill(new ImagePattern(cloudImage));
            cloudByNumber.get(cloudId+1).setVisible(true);
            cloudByNumber.get(cloudId+1).setOpacity(1);
            setCloudStudents(cloud,cloudId+1);
            clickChooseCloud(cloudByNumber.get(cloudId+1),cloudId);

        }
    }

    /**It selects the cloud after the click event.
     * @param cloud the selected cloud
     * @param cloudID the id of the cloud*/
    private void clickChooseCloud(Circle cloud, int cloudID){
        if(!view.isYourTurn())
            return;
        DropShadow cloudShadow = new DropShadow(8, Color.DARKRED);
        cloud.setOnMouseEntered(event -> {
            cloudStudentsByNumber.get(cloudID+1).forEach(circle -> {
                circle.setScaleY(1.3);
                circle.setScaleX(1.3);
                circle.setEffect(new DropShadow(4,Color.BLACK));
            });
            cloud.setEffect(cloudShadow);
            cloud.setScaleX(1.5);
            cloud.setScaleY(1.5);});

        cloud.setOnMouseExited(event -> {
            cloudStudentsByNumber.get(cloudID+1).forEach(circle -> {
                circle.setScaleY(1);
                circle.setScaleX(1);
                circle.setEffect(null);
            });
            cloud.setEffect(null);
            cloud.setScaleX(1);
            cloud.setScaleY(1);});
        cloud.setOnMouseClicked(mouseEvent -> {
            gui.getInputManager().cloudChoose(cloudID);
            info.setText("wait for your turn");
        });
    }

    /**It sets the students on the cloud.
     * @param cloud the ids of the students
     * @param cloudNumber the id of the cloud*/
    private void setCloudStudents(Integer[] cloud, int cloudNumber){
        List<Circle> cloudStud=cloudStudentsByNumber.get(cloudNumber);
        int i=0;
        for (Integer stud:cloud) {
            if(stud!=-1) {
                cloudStud.get(i).setFill(new ImagePattern(studentColorPath(stud, false)));
                cloudStud.get(i).setVisible(true);
                cloudByNumber.get(cloudNumber).setOpacity(1);
                i++;
            }
            else cloudByNumber.get(cloudNumber).setOpacity(0.5);
        }
    }

    /**@param studId the id of the student
     * @param highContrast it says if the resource is needed in high contrarst or not (green only)
     * @return the path for the graphical resource about token of color*/
    private Image studentColorPath(int studId, boolean highContrast){
        if(highContrast){
            return switch (studId / 26) {
                case 0 -> new Image("images/students/student_red.png");
                case 1 -> new Image("images/students/student_yellow.png");
                case 2 -> new Image("images/students/high_contrast_green.png");
                case 3 -> new Image("images/students/student_blue.png");
                case 4 -> new Image("images/students/student_pink.png");
                default -> throw new IllegalStateException("Unexpected value: " + studId / 26);
            };
        }else {
            return switch (studId / 26) {
                case 0 -> new Image("images/students/student_red.png");
                case 1 -> new Image("images/students/student_yellow.png");
                case 2 -> new Image("images/students/student_green.png");
                case 3 -> new Image("images/students/student_blue.png");
                case 4 -> new Image("images/students/student_pink.png");
                default -> throw new IllegalStateException("Unexpected value: " + studId / 26);
            };
        }
    }

    /**It sets mother nature on the island she currently is.
     * @param island the island currently visited by mother nature*/
    private void setMotherZone(SimplifiedIsland island){
        int motherPosition= view.getMother();
        boolean isMotherOn=false;
        if(view.getIslands().contains(island)){
            int islandPlace=view.getIslands().lastIndexOf(island);
            isMotherOn = islandPlace==motherPosition;
        }
        Rectangle mZone=motherZoneOfIsland.get(island.getIslandId()+1);
        mZone.setVisible(isMotherOn);
        mZone.setFill(new ImagePattern(new Image("images/simple_elements/mother_nature.png")));
    }

    /**It is used to add bridges to an island and its sub-islands.
     * @param island the island where to add bridges*/
    private void setBridges(SimplifiedIsland island){

        for (SimplifiedIsland subIsland:island.getSubIslands()) {
            int subId=subIsland.getIslandId()+1;
            int beforeIsland=subId-1;
            if(beforeIsland==0)
                beforeIsland=12;
            bridgeByIslandBefore.get(beforeIsland).setVisible(true);
            setBridges(subIsland);
        }
    }

    /**It sets the towers on the islands.*/
    private void initTowers(){
        towerByIslandNumber=new HashMap<>();
        for (Node towerPos:tower_container.getChildren()) {
            int islandTargetNumber = Integer.parseInt(towerPos.getId().substring(5));
            towerByIslandNumber.put(islandTargetNumber,(Rectangle) towerPos);
        }

    }

    /**It initializes islands tiles.*/
    private void initIslands(){
        islandByNumber= new HashMap<>();
        for (Node island:island_container.getChildren()) {
            int islandNumber= Integer.parseInt(island.getId().substring(6));
            islandByNumber.put(islandNumber, (Circle) island);
            island.setScaleX(1.5);
            island.setScaleY(1.5);
        }
    }

    /**It initializes clouds tiles.*/
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

    /**It initializes mother tiles.*/
    private void initMotherZones(){
        motherZoneOfIsland= new HashMap<>();
        for (Node zone:motherZones_container.getChildren()) {
            int islandNumber= Integer.parseInt(zone.getId().substring(12));
            motherZoneOfIsland.put(islandNumber, (Rectangle) zone);
        }
    }

    /**It initializes bridges tiles.*/
    private void initBridges() {
        bridgeByIslandBefore=new HashMap<>();
        for (Node bridge:bridge_container.getChildren()) {
            int islandBeforeNumber= Integer.parseInt(bridge.getId().substring(6));
            bridge.setVisible(false);
            ((Rectangle)bridge).setFill(new ImagePattern(new Image("images/simple_elements/bridgePlank.png")));
            bridgeByIslandBefore.put(islandBeforeNumber, (Rectangle) bridge);
        }

    }

    /**It changes the scene into <i>hand scene</i>>.*/
    public void goToHand() {
        gui.showHand();
    }

    /**It changes the scene into <i>board scene</i>>.*/
    public void goToBoard() {
        gui.showBoards();
    }

    /**It changes the scene into <i>character scene</i>>.*/
    public void goToCharacters() {
        gui.showCharacters();
    }

}
