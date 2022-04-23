package model;

public class MotherNature extends Token {
    private final int id;
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
