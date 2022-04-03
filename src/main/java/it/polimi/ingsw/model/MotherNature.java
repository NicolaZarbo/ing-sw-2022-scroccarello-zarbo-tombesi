package it.polimi.ingsw.model;

public class MotherNature  {
    private final int id;//mn alredy unique
    private int position;

    public MotherNature(int pos){
            this.id=1;
            this.position=pos;
    }
    public int getPosition(){
        return position;
    }
    public void changePosition(int newPos){
             this.position=newPos;
    }

}
