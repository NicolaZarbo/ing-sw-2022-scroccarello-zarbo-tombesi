package it.polimi.ingsw.model;

import it.polimi.ingsw.model.token.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private Student[][] diningRoom;
    private Professor[] table;
    private ArrayList<Student> entrance;
    private ArrayList<Tower> towers;
    private boolean[][] coinDN;
    final int playerID;
    final int entranceSize;

    public Board(int nTower, int dimEntrance, TowerColor color, int playerID){
        int nColors = TokenColor.listGetLastIndex()+1;
        this.coinDN = new boolean[nColors][3];
        this.setCoinDN(nColors);
        this.towers = new ArrayList<Tower>(nTower);
        for (int i=0;i<nTower;i++) {
            towers.add(new Tower(color,i+playerID));
        }
        this.playerID=playerID;
        this.diningRoom = new Student[nColors][10];
        this.entrance =new ArrayList<Student>(dimEntrance);
        this.table = new Professor[nColors];
        this.entranceSize=dimEntrance;
    }

    private void setCoinDN(int nColors){
        for (int i=0;i<nColors;i++){
            for (int j=0;j<3;j++) {
                    coinDN[i][j] = true;
            }
        }
    }
    public Professor getProfessor(TokenColor color){
        Professor prof=null;
        if(table[color.ordinal()]!=null)
            prof=table[color.ordinal()];
        return prof;
    }
    public boolean hasProfessor(TokenColor color){
        return getProfessor(color)!= null;
    }
    public void putProfessor(Professor prof){
       table[prof.getColor().ordinal()] = prof;
    }

    /** @param stud :sposta studente in dining room*/
    public void moveToDiningRoom(Student stud)throws NoPlaceAvailableException{
        boolean b=false;
        if(stud!= null) {
            int i = stud.getColor().ordinal();
            for (int j = 0; j < diningRoom[i].length; j++) {
                if (diningRoom[i][j] == null && !b){
                    diningRoom[i][j] = stud;
                    b=true;
                    return;
                }
            }
        throw new NoPlaceAvailableException();
        }
    }

    public Student getFromDiningRoom(int id)throws NoTokenFoundException{
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
        throw new NoTokenFoundException();
    }

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
        throw new NoTokenFoundException();
    }

    public void addTower(ArrayList<Tower> towers){
        this.towers.addAll(towers);
    }

    public Tower getTower(){
        Tower tower;
            tower = towers.get(towers.size()-1);
            towers.remove(towers.size()-1);
            if(towers.isEmpty())
            {//victory found
            }
            return tower;

    }

    public void putStudentOnEntrance(Student student)throws NoPlaceAvailableException{
        if(entrance.size()<=entranceSize)
            entrance.add(student);
        else
            throw new NoPlaceAvailableException();
        }

    public boolean foundCoin(Student stud) {
        int colorIndex=stud.getColor().ordinal();
        int pos = Arrays.asList(diningRoom[colorIndex]).indexOf(stud);
        int pCoin=(pos+1)/3;
        if (coinDN[stud.getColor().ordinal()][pCoin] && pos+1%3 == 0) {
            return true;
        }
        return false;
    }

    public ArrayList<Student> getEntrance(){
        return this.entrance;
    }
    public void setEntrance(ArrayList<Student> stud){
        this.entrance= stud;
    }
    public Student[][] getDiningRoom(){
        return this.diningRoom;
    }
    public void setDiningRoom(Student[][] dining){
         this.diningRoom=dining;
    }

    public void removeProfessor(TokenColor color){
        table[color.ordinal()]=null;
    }

}