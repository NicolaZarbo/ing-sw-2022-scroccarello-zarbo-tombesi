package it.polimi.ingsw.model;


import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.token.*;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    public static void moveInHall(int idPlayer,int idStud, Game game){
        Board board= game.getPlayer(idPlayer).getBoard();
        Student stud = board.getStudentFromEntrance(idStud);
        board.moveToDiningRoom(stud);

    }
    public static void moveToIsland(int idPlayer,int idStud, int idIsland, Game game){
        Student stud = game.getPlayer(idPlayer).getBoard().getStudentFromEntrance(idStud);
        Island island = game.getIsland(idIsland);
        island.addStudent(stud);
    }
    //moves mother nature and checks island
    public static void moveMotherNature(int steps,Game game){
        List<Island> islands=game.getIslands();
        MotherNature mother=game.getMotherNature();
        int j=islands.size();
        mother.changePosition(Math.floorMod(mother.getPosition()+steps,j));
    }
    //last action of the turn
    public static void moveFromCloudToEntrance(Game game,int cloudId,int playerId){
        Board board= game.getPlayer(playerId).getBoard();
        Cloud[] clouds=game.getClouds();
        if(cloudId>=0 && cloudId<clouds.length && clouds[cloudId].getStud()[0]!=null){
            Student[] students=clouds[cloudId].getStud();
            for(int i=0;i<students.length;i++) {
                board.putStudentOnEntrance(students[i]);
                students[i]=null;
            }
            clouds[cloudId].setStud(students);
            //game.setClouds(clouds);
        }
        game.resetBonus();
    }

    public static boolean isUnifiableNext(Game game,int pos){
        boolean b=false;
        int size = game.getIslands().size();
        Island central=game.getIslands().get(pos);
        Island next=game.getIslands().get(Math.floorMod(pos+1,size));

        if( !central.getTowers().isEmpty() && !next.getTowers().isEmpty())
            if(central.getTowers().get(0).getColor()==next.getTowers().get(0).getColor())
                b=true;
        return b;
    }
    public static boolean isUnifiableBefore(Game game,int pos){
        boolean b=false;
        int size = game.getIslands().size();
        Island central=game.getIslands().get(pos);
        Island before=game.getIslands().get(Math.floorMod(pos-1,size));
        if( !central.getTowers().isEmpty() && !before.getTowers().isEmpty())
            if(central.getTowers().get(0).getColor()==before.getTowers().get(0).getColor())
                b=true;
        return b;
    }

    public static void unifyNext(Game game, int pos) {
        int islandNumber = game.getIslands().size();
        Island central = game.getIsland(pos);
        Island next = game.getIsland(Math.floorMod(pos + 1, islandNumber));
        if (central.getTowers().get(0).getColor() == next.getTowers().get(0).getColor()) {
            central.addAllStudents(next.getStudents());
            central.addAllTowers(next.getTowers());
            game.getIslands().remove(next);
            central.incrementIslandSize();
            if (game.getMotherNature().getPosition() == pos + 1)
                game.getMotherNature().changePosition(pos);
            }
    }
    public static void unifyBefore(Game game, int pos) {
        int islandNumber = game.getIslands().size();
        Island central = game.getIsland(pos);
        Island before = game.getIsland(Math.floorMod(pos - 1, islandNumber));
        if (central.getTowers().get(0).getColor() == before.getTowers().get(0).getColor()) {
            central.addAllStudents(before.getStudents());
            central.addAllTowers(before.getTowers());
            game.getIslands().remove(before);
            central.incrementIslandSize();
            if (game.getMotherNature().getPosition() == pos - 1)
                game.getMotherNature().changePosition(pos);
            }

    }

    private static void putTowerFromBoardToIsland(Island island,Player player){
        if(player!= null){
            Board board=player.getBoard();
            island.setTower(board.getTower());
        }
    }
    private static void changeTower(Island island , Player newOwner, Game game){
        ArrayList<Tower> removedT= island.getTowers();
        island.getTowers().clear();
        Turn.putTowerFromBoardToIsland(island,newOwner);
        for (Player player: game.getPlayers()) {
            if(player.getColorT()==removedT.get(0).getColor()){
                player.getBoard().addTower(removedT);
            }
        }
    }

    //controllo se una board ha il diritto ad avere il prof del colore scelto
    public static boolean canHaveTeacher(TokenColor color, Game game, int playerId){
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
    public static void getTeacher(TokenColor color,Game game,int playerId){
        Player[] players=game.getPlayers();
        Player playercheck=game.getPlayer(playerId);
        if(canHaveTeacher(color, game, playerId)){
                if(game.isProfessorOnGame(color)){
                Professor temp=game.getFromGame(color);
                game.getPlayer(playerId).getBoard().putProfessor(temp);
            }else{
                for(int i=0;i<players.length;i++)
                    if(players[i]!=playercheck && players[i].getBoard().getProfessor(color)!=null){
                        Professor temp=players[i].getBoard().getProfessor(color);
                        game.getPlayer(playerId).getBoard().putProfessor(temp);
                        int playerTemp=players[i].getId();
                        game.getPlayer(playerTemp).getBoard().removeProfessor(color);
                    }
            }

        }
    }

    public static void useCharacter(int cardId, ArrayList<Integer> parameters, Game game){
        //  call to the right method with right parameters(int list) Arraylist used like a ParameterObject from omonimous pattern
        CharacterCard card = game.getCharacter(cardId);
        card.cardEffect( parameters,  game );
        //controls for enoughCoin, isUsed in controller, or here with an exception
    }


    public static int calculateInfluence(Game game,int playerId){
        int influence=0;
        if(game.isBonusActive(8))  //card 8 effect
            influence=influence+2;
        MotherNature theOne=game.getMotherNature();
        Island where=game.getIsland(theOne.getPosition());
        ArrayList<Student> students=where.getStudents();
        Player player = game.getPlayer(playerId);
        TowerColor color=player.getColorT();
        Student stud;
        for(int i=0;i<students.size();i++){
            stud = students.get(i);
            if(player.getBoard().hasProfessor(stud.getColor()) && !(stud.getColor()==game.getTargetColor() && game.isBonusActive(9)))//card 9 effect
                influence++;

        }//card 6 effect
        if(!game.isBonusActive(6)){
            ArrayList<Tower> towers=where.getTowers();
            for(int j=0;j<towers.size();j++){
                if(color.equals(towers.get(j).getColor()))
                    influence++;
             }
        }
        return influence;
    }
    //call checks for influence and changes or inserts a tower
    public static void islandConquest(int islandId, Game game){
        int influence, maxInf=0;
        Island island=game.getIsland(islandId);
        Player conqueror=null;
        Player[] players = game.getPlayers();
        for (Player player: players) {
            influence=Turn.calculateInfluence(game,player.getId());
            if(influence > maxInf){
                maxInf=influence;
                conqueror=player;
            }
        }

        if( !island.getTowers().isEmpty() ){
            if(island.getTowers().get(0).getColor() != conqueror.getColorT())
               Turn.changeTower(island, conqueror, game);
        } else
            Turn.putTowerFromBoardToIsland(island,conqueror);
    }


}




