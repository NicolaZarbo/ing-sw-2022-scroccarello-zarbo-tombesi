package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Board {
    private Student[][] diningRoom;
    private Professor[] table;
    private Student[] entrance;
    private Tower[] towers;//usare arraylist?
    private boolean[][] coinDN;
    final int playerID;

    public Board(int nTower, int dimEntrance, TowerColor color, int playerID){
        int nColors = TokenColor.listGetLastIndex()+1;
        this.coinDN = new boolean[nColors][3];
        this.setCoinDN(nColors);
        this.towers = new Tower[nTower];
        for (int i=0;i<nTower;i++) {
            towers[i]=new Tower(color,i);
        }
        this.playerID=playerID;
        this.diningRoom = new Student[nColors][10];
        this.entrance =new Student[dimEntrance];
        this.table = new Professor[nColors];
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
    public void putProfessore(Professor prof){
       table[prof.getColor().ordinal()] = prof;
    }

    /** @param stud :sposta studente in dining room*/
    public void moveToDiningRoom(Student stud ){
        int i = stud.getCol().ordinal();
        boolean b=false;
        if(stud!= null) {
            for (int j = 0; j < diningRoom[i].length; j++) {
                if (diningRoom[i][j] == null && !b){
                    diningRoom[i][j] = stud;
                    b=true;

                }

            }
        }
        //gestire 'eccezione' riga piena e controllo moneta (oppure controllo isCoin chiamato in controller)  TODO
    }
    public Student getFromDiningRoom(int id){
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
        return null;
    }

    //estrae studente da ingresso
    public Student getStudent(int id){
        Student stud;
        for (int i=0;i<entrance.length;i++) {
            stud=entrance[i];
            if(stud != null)
                if( stud.getId()==id){
                    entrance[i]=null;
                    return stud;
                }
        }
        return null; // <---- null se non presente
    }

    public Tower getTower(){
        Tower tower;
       // if(towers[towers.length-1]!=null)
        for (int i=0; i< towers.length;i++) {
            tower=towers[i];
            if (tower != null) {
                towers[i]=null;
                return tower;
            }
        }
        return null;
    }

    //piazza uno studente in ingresso
    public void putStudent(Student student){
        if(entrance[entrance.length-1]==null){
            for(int i = 0; i< entrance.length; i++){
                if(entrance[i]==null) {
                    entrance[i] = student;
                    break;
                }
            }
        }
    }

    private boolean isCoin(int posDining, int indexColor){
        int pCoin=(posDining+1)/3;
        if (coinDN[indexColor][pCoin]  && posDining+1%3==0 ){
           return true;
        }
        else return false;
    }

    public Student[] getEntrance(){
        return this.entrance;
    }
    public void setEntrance(Student[] stud){
        this.entrance=stud;
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