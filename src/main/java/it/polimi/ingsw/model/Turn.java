package it.polimi.ingsw.model;

import java.util.List;

public class Turn {

    public void moveInHall(Board board,int pos){
           Student [] temp=board.getEntrance();
           Student[][] dining=board.getDiningRoom();
           if(temp[pos]!=null) {
               Student stud = temp[pos];
               int i=stud.getCol().ordinal();
               if(dining[i][dining[i].length-1]!=null) {
                   board.moveToDiningRoom(stud);
                    temp[pos]=null;
                    board.setEntrance(temp);
               }
           }
    }


    public void moveMotherNature(int num,Game game){
      List<Island> tempis=game.getIslands();
      MotherNature tempm=game.getMotherNature();
       int j=tempis.size();
       tempm.changePosition(Math.floorMod(tempm.getPosition()+num,j));
       game.setMotherNature(tempm);

    }
    public void moveFromCloudToEntrance(Game game,int pos,Board board){
        Cloud[] temp=game.getClouds();
        if(pos>=0 && pos<temp.length && temp[pos].stud[0]!=null){
            Student[] tempstud=temp[pos].getStud();
            for(int i=0;i<tempstud.length;i++) {
                board.putStudent(tempstud[i]);
                tempstud[i]=null;
            }
            temp[pos].setStud(tempstud);
            game.setClouds(temp);
        }

    }

    public boolean isUnificabile(Game game,int pos){
        boolean b=false;
        List<Island> tempis=game.getIslands();
        Island central=tempis.get(pos);
        Island before=tempis.get(Math.floorMod(pos-1,tempis.size()));
        Island after=tempis.get(Math.floorMod(pos+1,tempis.size()));
        if(central.getTower().getCol().equals(before.getTower().getCol()) && before.getTower().getCol().equals(after.getTower().getCol()))
            b=true;
        return b;
    }
}
