package it.polimi.ingsw.client.GUI.Scenes;

import javafx.scene.layout.Pane;

public class BoardScene4Controller extends BoardSceneController{

    public Pane player1entrance;
    public Pane player1hall;
    public Pane player1table;
    public Pane player1towers;
    public Pane player2entrance;
    public Pane player2hall;
    public Pane player2table;
    public Pane player2towers; //todo still doesn't exists on the board for reasons
    public Pane player3entrance;
    public Pane player3hall;
    public Pane player3table;
    public Pane player3towers;
    public Pane player4entrance;
    public Pane player4hall;
    public Pane player4table;
    public Pane player4towers; //todo board doesn't have these towers for unknown reasons

    public BoardScene4Controller(){

    }
    @Override
    public void initialize() {
        for(int i=1;i<=4;i++){
            buildEntrance(i);
            buildHall(i);
            buildTable(i);
            buildTowers(i);
        }
    }
    private void buildEntrance(int player){

    }
    private void buildHall(int player){

    }
    private void buildTable(int player){

    }
    private void buildTowers(int player){

    }
}
