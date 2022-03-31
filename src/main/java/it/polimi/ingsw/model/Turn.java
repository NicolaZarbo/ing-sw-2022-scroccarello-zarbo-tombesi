package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.List;

public class Turn {
    public void moveInHall(Board board,int pos){//esistono già metodi di board per estrarre da entrance e per inserire in sala
        Student [] temp=board.getEntrance();//questo metodo è diverso serve per spostare uno studente specifico presente in sala d'attesa
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
    //last action of the turn
    public void moveFromCloudToEntrance(Game game,int pos,Board board){
        Cloud[] temp=game.getClouds();
        if(pos>=0 && pos<temp.length && temp[pos].getStud()[0]!=null){
            Student[] tempstud=temp[pos].getStud();
            for(int i=0;i<tempstud.length;i++) {
                board.putStudent(tempstud[i]);
                tempstud[i]=null;
            }
            temp[pos].setStud(tempstud);
            game.setClouds(temp);
        }
        game.resetBonus();
    }

    public boolean isUnifiable(Game game,int pos){
        boolean b=false;
        List<Island> tempis=game.getIslands();
        Island central=tempis.get(pos);
        Island before=tempis.get(Math.floorMod(pos-1,tempis.size()));
        Island after=tempis.get(Math.floorMod(pos+1,tempis.size()));
        if(central.getTower().get(0)!=null && before.getTower().get(0)!=null && after.getTower().get(0)!=null)
            //se non va prova a controllare l'output di getTower().get(0) su un array list vuota!
            if(central.getTower().get(0).getColor().equals(before.getTower().get(0).getColor()) && before.getTower().get(0).getColor().equals(after.getTower().get(0).getColor()))
                b=true;
        return b;
    }
    //vedi se va gestita la posizione di madre natura
    public void unify(Game game, int pos){
        if(isUnifiable(game,pos)){
            List<Island> tempis=game.getIslands();
            int bigId=tempis.get(0).getID();
            for(int i=0;i<tempis.size();i++) {
                if (bigId < tempis.get(i).getID())
                    bigId = tempis.get(0).getID();
            }
            Island central=tempis.get(pos);
            Island before=tempis.get(Math.floorMod(pos-1,tempis.size()));
            Island after=tempis.get(Math.floorMod(pos+1,tempis.size()));
            if(tempis.size()>2){
                Island bigIsland=new Island(bigId+1);
                bigIsland.addAllTowers(central.getTower());
                bigIsland.addAllTowers(before.getTower());
                bigIsland.addAllTowers(after.getTower());
                bigIsland.addAllStudents(central.getStudents());
                bigIsland.addAllStudents(before.getStudents());
                bigIsland.addAllStudents(after.getStudents());
            }else if(tempis.size()==2){
                Island bigIsland=new Island(bigId+1);
                bigIsland.addAllTowers(central.getTower());
                bigIsland.addAllTowers(before.getTower());
                bigIsland.addAllStudents(central.getStudents());
                bigIsland.addAllStudents(before.getStudents());
            }
        }

    }
    public void putTowerFromBoardToIsland(Island island,Board board){
        island.setTower(board.getTower());
    }

    //controllo se una board ha il diritto ad avere il prof del colore scelto
    public boolean isTeacher(TokenColor color,Game game,int playerId){
        Player[] players=game.getPlayers();
        boolean b=true;
        Player playercheck=game.getPlayer(playerId);
        Board playerBoard=playercheck.getBoard();

            int tokenplayer = 0; //count dei token del player
            int tempcount=0; //count dei token degli altri players
            for (int i = 0; i < playerBoard.getDiningRoom()[color.ordinal()].length; i++)
                    if(playerBoard.getDiningRoom()[TokenColor.getIndex(color)][i]!=null)
                        tokenplayer++;
            for(int j=0;j<players.length;j++){
                tempcount=0;
                if(!players[j].equals(playercheck)) {
                    for (int i = 0; i < players[j].getBoard().getDiningRoom()[color.ordinal()].length; i++) {
                        if (players[j].getBoard().getDiningRoom()[TokenColor.getIndex(color)][i] != null)
                            tempcount++;
                    }//card 2 effect
                    if (tempcount >= tokenplayer && !(game.isBonusActive(2) && tempcount<=tokenplayer))
                        b = false;
                }
            }
        return b;
    }
    public void getTeacher(TokenColor color,Game game,int playerId){
        Player[] players=game.getPlayers();
        Player playercheck=game.getPlayer(playerId);
        if(isTeacher(color, game, playerId)){
                if(game.isProfessorOnGame(color)){
                Professor temp=game.getFromGame(color);
                game.getPlayer(playerId).getBoard().putProfessore(temp);
            }else{
                for(int i=0;i<players.length;i++)
                    if(players[i]!=playercheck && players[i].getBoard().getProfessor(color)!=null){
                        Professor temp=players[i].getBoard().getProfessor(color);
                        game.getPlayer(playerId).getBoard().putProfessore(temp);
                        int playerTemp=players[i].getId();
                        game.getPlayer(playerTemp).getBoard().removeProfessor(color);
                    }
            }

        }
    }
    //manca tutta la questione delle monete
    public static void useCharacter(CharacterCard card, ArrayList<Integer> parameters, Game game){
        //  call to the right method with right parameters(int list) Arraylist used like a ParameterObject from omonimous pattern
        card.cardEffect( parameters,  game );
        //controlli enoughCoin, isUsed e payCoin in controller(?)
    }
    public int calculateInfluence(Game game,int playerId){
        int influence=0;
        if(game.isBonusActive(8))  //card 8 effect
            influence=influence+2;
        MotherNature theOne=game.getMotherNature();
        Island where=game.getIsland(theOne.getPosition());
        ArrayList<Student> students=where.getStudents();
        TowerColor color=game.getPlayer(playerId).getColorT();
        for(int i=0;i<students.size();i++){
            if(isTeacher(students.get(i).getCol(),game,playerId) && !(students.get(i).getCol()==game.getTargetColor() && game.isBonusActive(9)))//card 9 effect
                influence++;

        }//card 6 effect
        if(!game.isBonusActive(6)){
            ArrayList<Tower> towers=where.getTower();
            for(int j=0;j<towers.size();j++){
                if(color.equals(towers.get(j).getColor()))
                    influence++;
             }
        }
        return influence;
    }


}




