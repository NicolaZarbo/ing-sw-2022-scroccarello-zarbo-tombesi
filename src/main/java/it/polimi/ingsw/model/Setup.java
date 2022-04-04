package it.polimi.ingsw.model;
import it.polimi.ingsw.model.token.Professor;
import it.polimi.ingsw.model.token.Student;
import it.polimi.ingsw.model.token.TokenColor;
import it.polimi.ingsw.model.token.TowerColor;

import java.util.ArrayList;
import java.util.List;

public class Setup {
    public static List<Island> createIslands(int nIsole, Bag bag){
        List<Island> islands = new ArrayList<>();
        Island isl;
        ArrayList<Student> students = bag.setupStudents(nIsole);
        for(int i=0;i<nIsole;i++){
            isl =  new Island(i);
            if(i!=0 && i!=(nIsole-1/2)){
                isl.addStudent(students.get(i));
                isl.addStudent(students.get(i));
            }
            islands.add(isl);
        }
        return  islands;
    }
    /*public static ArrayList<CharacterCard> createCharacterCards(Bag bag){
        ArrayList<CharacterCard> cards = new ArrayList<>();
        for (int i=1; i<=12; i++){
            cards.add(  FactoryCharacter.createCharacter(i, bag)) ;
        }
        return cards;
    } */

    public static Professor[] createProfessor(int nColors){
        Professor[] profs = new Professor[nColors];
        for (int i=0; i<nColors;i++){
            profs[i]= new Professor(i, TokenColor.getColor(i));
        }
        return profs;
    }
    public static Player[] createPlayer(boolean easy , int nPlayer){

        Player players[] =new Player[nPlayer];

        for (int id=0; id<nPlayer;id++){
            Hand man= Setup.createHand(id, easy, 10);
            TowerColor towerColor = Setup.playerColor(id, nPlayer);
            Board plan = Setup.createBoard(id, nPlayer, towerColor);
            players[id]= new Player(id, Mage.getMage(id), man, plan, towerColor);
        }
        return players;
    }
    //genera le isole con all'interno array studenti con studenti null
    public static Cloud[] createClouds(int nPlayer){
        Cloud[] clouds = new Cloud[nPlayer];
        Student[] studs;
        int dimIsl;
        if (nPlayer==3) {
            dimIsl = 4;
        }else {
            dimIsl=3;
        }
        studs = new Student[dimIsl];
        for(int k=0; k<dimIsl;k++){
            studs[k]=null;
        }
        for(int id = 0; id<nPlayer; id++){

            clouds[id]= new Cloud(studs,id);
        }
        return  clouds;
    }
    //public static ArrayList<Integer> createIslands(){}   TODO creaIsole
    private static TowerColor playerColor(int playerId, int nPlayer){
        TowerColor color;
        if (nPlayer== 4 && playerId>1){
         color = TowerColor.getColor(playerId-2);
        }
        else
            color= TowerColor.getColor(playerId);
        return color;
    }
    //generazione mano da associare a player
    private static Hand createHand(int playerId, boolean easy, int nCards){
        ArrayList<AssistantCard> ass ;
        int stIdCards= playerId * nCards ;
        ass = new ArrayList<AssistantCard>();
        for(int j=0;j<nCards; j++){
            ass.add(new AssistantCard(stIdCards+j,j,j/2, Mage.getMage(playerId)));
        }
        Hand hand =new Hand(ass);
        if (!easy) {
            hand.addCoin();
        }
         return hand;
    }
    private static Board createBoard(int playerId, int nPlayer, TowerColor towerColorolor){
        Board pla ;
        TowerColor color;
        int nTower, dimEntry;
        if (nPlayer == 3){
            nTower=6;
            dimEntry = 9;
        }
        else{
            nTower =8;
            dimEntry = 7;
        }
        if (nPlayer== 4 && playerId>1){
            nTower=0;
        }
        pla=new Board(nTower,dimEntry,towerColorolor,playerId);
        return pla;
    }


}
