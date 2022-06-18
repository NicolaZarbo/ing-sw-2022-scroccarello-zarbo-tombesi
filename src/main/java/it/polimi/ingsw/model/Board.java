package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.enumerations.TowerColor;
import it.polimi.ingsw.exceptions.NoPlaceAvailableException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.model.tokens.*;

import java.util.ArrayList;
import java.util.Arrays;
/**It represents the single board of the player. It contains the tokens of the player and his towers. It is divided into four sections:
 * <p>•<b>Entrance</b>: it contains the placeable tokens.</p>
 * <p>•<b>Dining room</b>: it contains tokens that cannot normally be moved and the spots for the coins in expert mode.</p>
 * <p>•<b>Table</b>: it contains the professors of each color, if present.</p>
 * <p>•<b>Towers</b>: it contains the remaining towers of the player.</p>*/
public class Board {
    private Student[][] diningRoom;
    private final Professor[] table;
    private ArrayList<Student> entrance;
    private final ArrayList<Tower> towers;
    private final boolean[][] coinDN;
    final int playerID;
    final int entranceSize;

    /**It builds the board of the player according to the rules.
     * @param color tower color of the player, also team color
     * @param dimEntrance number of students of the entrance
     * @param nTower number of towers of the player
     * @param playerID id of the corresponding player*/
    public Board(int nTower, int dimEntrance, TowerColor color, int playerID){
        int nColors = TokenColor.listGetLastIndex()+1;
        this.coinDN = new boolean[nColors][3];
        this.setCoinDN(nColors);
        this.towers = new ArrayList<Tower>();
        for (int i=0;i<nTower;i++) {
            towers.add(new Tower(color,i+playerID));
        }
        this.playerID=playerID;
        this.diningRoom = new Student[nColors][10];
        this.entrance =new ArrayList<Student>();
        this.table = new Professor[nColors];
        this.entranceSize=dimEntrance;
    }

    /**It initializes the coin spots in the dining room. For expert mode only.
     * @param nColors number of colors of the tokens*/
    private void setCoinDN(int nColors){
        for (int i=0;i<nColors;i++){
            for (int j=0;j<3;j++) {
                    coinDN[i][j] = true;
            }
        }
    }

    /**It returns the professor in the board, if it is present.
     * @param color color of the professor to extract. If not found it returns null.*/
    public Professor getProfessor(TokenColor color){
        Professor prof=null;
        if(table[color.ordinal()]!=null)
            prof=table[color.ordinal()];
        return prof;
    }

    /**It states if the professor of the target color is on board.
     * @param color target color
     * @return •true: the professor is on the table
     * <p>•false: the professor is not on the table</p>*/
    public boolean hasProfessor(TokenColor color){
        return getProfessor(color)!= null;
    }

    /**It sets the target professor on the corresponding spot.
     * @param prof professor token to set*/
    public void putProfessor(Professor prof){
       table[prof.getColor().ordinal()] = prof;
    }

    /** It moves the target student on the spot of the relative color on the dining room. If there are no spots available it throws an exception.
     * @param stud target student which is on the entrance
     * @exception NoPlaceAvailableException if in the relative row of the dining room there is no place available*/
    public void moveToDiningRoom(Student stud)throws NoPlaceAvailableException {
        if(stud!= null) {
            int i = stud.getColor().ordinal();
            for (int j = 0; j < diningRoom[i].length; j++) {
                if (diningRoom[i][j] == null){
                    diningRoom[i][j] = stud;
                    return;
                }
            }
        throw new NoPlaceAvailableException("no place available in dining room");
        }
    }

    /**It returns the target student which is in the dining room. If it is not there, it throws an exception.
     * @param id target student's id
     * @exception NoTokenFoundException if there is no token with the target id*/
    public Student getFromDiningRoom(int id)throws NoTokenFoundException {
        Student stud;
        for(int i=0; i<diningRoom.length;i++){
            for (int j=0; j<diningRoom[0].length;j++){
                if(diningRoom[i][j]!= null)
                    if( diningRoom[i][j].getId()==id){
                        stud=diningRoom[i][j];
                        diningRoom[i][j]=null;
                        return stud;
                    }
            }
        }
        throw new NoTokenFoundException("no student of that color in dining room");
    }

    /**It returns the token with the target id from entrance. If not found, it throws an exception.
     * @param id target token's id
     * @exception NoTokenFoundException if there is no token with the target id in the entrance*/
    public Student getStudentFromEntrance(int id)throws NoTokenFoundException{
        Student stud;
        for (int i=0;i<entrance.size();i++) {
            stud=entrance.get(i);
            if(stud != null)
                if( stud.getId()==id){
                    entrance.remove(i);
                    return stud;
                }
        }
        throw new NoTokenFoundException("no student of color : "+TokenColor.getColor(id/26)+" in entrance");
    }

   /**It initializes the towers of the player.
    * @param towers list of towers of the player*/
    public void initTowers(ArrayList<Tower> towers){
        this.towers.addAll(towers);
    }

    /**@return number of remaining towers of the player*/
    public int towersLeft(){
        return towers.size();
    }

    /**It extracts one of the towers of the player from the list and removes it from available towers.
     * @return one of the towers of the player*/
    public Tower getTower(){
        Tower tower;
            tower = towers.get(towers.size()-1);
            towers.remove(towers.size()-1);
            if(towers.isEmpty())
            {//victory found
            }
            return tower;
    }

    /**@return table of the player*/
    public Professor[] getTable() {
        return table;
    }

    /**@return the coinDN of the player*/
    public boolean[][] getCoinDN() {
        return coinDN;
    }

    /**It inserts a student token on the entrance of the player. If there is no spot available it throws an exception.
     * @param student target token to insert
     * @exception NoPlaceAvailableException if the entrance is full of tokens*/
    public void putStudentOnEntrance(Student student)throws NoPlaceAvailableException{
        if(entrance.size()<entranceSize)
            entrance.add(student);
        else
            throw new NoPlaceAvailableException("no place left on entrance");
    }

    /**It states if in the spot of that student there is a coin or not.
     * @return •true: there is a coin
     * <p>•false: there is not a coin</p>*/
    public boolean foundCoin(Student stud) {
        int colorIndex=stud.getColor().ordinal();
        int pos = Arrays.asList(diningRoom[colorIndex]).indexOf(stud);
        int pCoin=(pos)/3;
        return coinDN[stud.getColor().ordinal()][pCoin] && pos + 1 % 3 == 0;
    }

    /**@return the entrance of the player*/
    public ArrayList<Student> getEntrance(){
        return this.entrance;
    }

    /**It initializes the entrance of the player.
     * @param stud the list of students to set on the entrance*/
    public void initEntrance(ArrayList<Student> stud){
        this.entrance= stud;
    }

    /**@return the dining room of the player*/
    public Student[][] getDiningRoom(){
        return this.diningRoom;
    }

    /**It initializes the dining room of the player.
     * @param dining the matrix of students to set*/
    public void initDiningRoom(Student[][] dining){
         this.diningRoom=dining;
    }

    /**It moves away the professor from the player school.
     * @param color color of the target professor to remove*/
    public void removeProfessor(TokenColor color){
        table[color.ordinal()]=null;
    }

}