package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.enumerations.TowerColor;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoPlaceAvailableException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.messages.servermessages.*;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.token.*;

import java.util.*;

public class Turn {
    private final Game game;
    private int movedStudent;

    public Turn (Game game){
        this.game=game;
        movedStudent=0;
    }

    /** moves a student already inside the Board of idPlayer from entrance to hall  */
    public void moveInHall(int idPlayer,int idStud){
        try {
            Player player = game.getPlayer(idPlayer);

            Board board = player.getBoard();
            Student stud = board.getStudentFromEntrance(idStud);
            board.moveToDiningRoom(stud);
            if (board.foundCoin(stud))
                player.getHand().addCoin();
            if (canHaveTeacher(stud.getColor(), idPlayer))
                getTeacher(stud.getColor(), idPlayer);
        }
        catch(NoTokenFoundException e){
            throw new NoTokenFoundException(e.getMessage());
        }
        movedStudent++;
        if(movedStudent<3)
            game.notify(new SingleBoardMessage(game, idPlayer));
        else {
            game.groupMultiMessage(new SingleBoardMessage(game, idPlayer));
            movedStudent=0;
        }

    }

    /** moves a student already inside the Board of idPlayer from entrance to a target island*/
    public void moveToIsland(int idPlayer,int idStud, int idIsland) throws NullPointerException{
        Student stud = game.getPlayer(idPlayer).getBoard().getStudentFromEntrance(idStud);
        Island island = game.getIsland(idIsland);
        island.addStudent(stud);
        game.groupMultiMessage(new  IslandsMessage(game));
        game.groupMultiMessage(new SingleBoardMessage(game,idPlayer));
        //use a multiple message to reduce the number of connection use TODO
        movedStudent++;
        if(movedStudent<3)
            game.sendMultiMessage();
        else {
            movedStudent=0;
        }


    }
    /** moves motherNature for a number 'steps', then checks on the final island for tower conquest and island merge*/
    public void moveMotherNature(int steps){
        List<Island> islands=game.getIslands();
        MotherNature mother=game.getMotherNature();
        int j=islands.size();
        mother.changePosition(Math.floorMod(mother.getPosition()+steps,j));
        int position = mother.getPosition();
        int islandId= game.getIslands().get(position).getID();
        islandConquest( islandId);
        if (isUnifiableNext( position)) {
            unifyNext( position);
        }
        if (isUnifiableBefore( position)) {
            unifyBefore( position);
        }
        game.groupMultiMessage(new MotherPositionMessage(game));
        if(game.getIslands().size()<=3)
            game.gameOver();
    }
    /** moves al the students on a target cloud to the board of playerId, it is also the last actin of the turn*/
    public void moveFromCloudToEntrance(int cloudId,int playerId){
        Board board= game.getPlayer(playerId).getBoard();
        Cloud[] clouds=game.getClouds();
        if(cloudId>=0 && cloudId<clouds.length && clouds[cloudId].getStud()[0]!=null){
            Student[] students=clouds[cloudId].getStud();
            for(int i=0;i<students.length;i++) {
                try{
                    board.putStudentOnEntrance(students[i]);
                    students[i]=null;
                }
                catch(NoPlaceAvailableException e){
                    throw new IllegalMoveException("entrance is full");
                }
            }
        }
        else {
            throw new RuntimeException("unavailable cloud");
        }

        game.resetBonus();
        game.groupMultiMessage(new CloudMessage(game));
        game.groupMultiMessage(new SingleBoardMessage(game,playerId));

    }

    public boolean isUnifiableNext(int pos){
        boolean b=false;
        int size = game.getIslands().size();
        Island central=game.getIslands().get(pos);
        Island next=game.getIslands().get(Math.floorMod(pos+1,size));

        if( !central.getTowers().isEmpty() && !next.getTowers().isEmpty())
            if(central.getTowers().get(0).getColor()==next.getTowers().get(0).getColor())
                b=true;
        return b;
    }
    public  boolean isUnifiableBefore(int pos){
        boolean b=false;
        int size = game.getIslands().size();
        Island central=game.getIslands().get(pos);
        Island before=game.getIslands().get(Math.floorMod(pos-1,size));
        if( !central.getTowers().isEmpty() && !before.getTowers().isEmpty())
            if(central.getTowers().get(0).getColor()==before.getTowers().get(0).getColor())
                b=true;
        return b;
    }

    public  void unifyNext(int pos) {
        int islandNumber = game.getIslands().size();
        Island central = game.getIslands().get(pos);
        Island next = game.getIslands().get(Math.floorMod(pos + 1, islandNumber));
        if (central.getTowers().get(0).getColor() == next.getTowers().get(0).getColor()) {
            //central.addAllStudents(next.getStudents());
            central.addSubIsland(next);
            //central.addAllTowers(next.getTowers());
            game.getIslands().remove(next);
            central.incrementIslandSize();
            //game.getMotherNature().changePosition(pos);
            }

        game.groupMultiMessage(new IslandsMessage(game));

    }
    public  void unifyBefore( int pos) {
        int islandNumber = game.getIslands().size();
        Island central = game.getIslands().get(pos);
        Island before = game.getIslands().get(Math.floorMod(pos - 1, islandNumber));
        if (central.getTowers().get(0).getColor() == before.getTowers().get(0).getColor()) {
            //central.addAllStudents(before.getStudents());
            central.addSubIsland(before);
            //central.addAllTowers(before.getTowers());
            game.getIslands().remove(before);
            central.incrementIslandSize();
           // if (game.getMotherNature().getPosition() == pos - 1)
            game.getMotherNature().changePosition(pos-1);
            }
        game.groupMultiMessage(new IslandsMessage(game));
    }
    /**
     @param player  the player who won the influence contest */
    public void putTowerFromBoardToIsland(Island island,Player player){
        if(island.getTowers().size()!=0)
            return;
        Board board;
        if(isSquadLeader(player))
             board=player.getBoard();
        else board=game.getPlayer(player.getId()-2).getBoard();
        island.setTower(board.getTower());
        game.groupMultiMessage(new IslandsMessage(game));
        game.groupMultiMessage(new SingleBoardMessage(game,player.getId()));
        if(board.towersLeft()==0)
            game.gameOver();
    }
    private void changeTower(Island island , Player newOwner){
        ArrayList<Tower> removedT=new ArrayList<>(island.getEveryTower());
        island.removeEveryTower();
        for (Player player: game.getPlayers()) {
            if(player.getColorT()==removedT.get(0).getColor()){
                player.getBoard().addTower(removedT);
                game.groupMultiMessage(new SingleBoardMessage(game,player.getId()));
            }
        }
        this.putTowerFromBoardToIsland(island,newOwner);
    }
    /** used to differentiate behavior when the player isn't the owner of the team towers*/
    private boolean isSquadLeader(Player player){
        if(game.getNPlayers()!=4)
            return true;//believe in yourself, be your own leader
        return player.getId()<2;
    }

    //controllo se una board ha il diritto ad avere il prof del colore scelto
    public  boolean canHaveTeacher(TokenColor color, int playerId){
        Player[] players=game.getPlayers();
        boolean b=true;
        Player playercheck=game.getPlayer(playerId);
        Board playerBoard=playercheck.getBoard();

           int tokenplayer = 0; //count dei token del player
        int tempcount; //count dei token degli altri players
        for (int i = 0; i < playerBoard.getDiningRoom()[color.ordinal()].length; i++)
            if(playerBoard.getDiningRoom()[TokenColor.getIndex(color)][i]!=null)
                tokenplayer++;
        for (Player player : players) {
            tempcount = 0;
            if (!player.equals(playercheck)) {
                for (int i = 0; i < player.getBoard().getDiningRoom()[color.ordinal()].length; i++) {
                    if (player.getBoard().getDiningRoom()[TokenColor.getIndex(color)][i] != null)
                        tempcount++;
                }//card 2 effect
                if (tempcount >= tokenplayer && !(game.isBonusActive(2) && tempcount <= tokenplayer))
                    b = false;
            }
        }
        return b;
    }
    public  void getTeacher(TokenColor color,int playerId){
        Player[] players=game.getPlayers();
        Player playercheck=game.getPlayer(playerId);
        if(canHaveTeacher(color,  playerId)){
            if(game.isProfessorOnGame(color)){
                Professor temp=game.getFromGame(color);
                game.getPlayer(playerId).getBoard().putProfessor(temp);
            }else{
                    for (Player player : players)
                        if (player != playercheck && player.getBoard().getProfessor(color) != null) {
                            Professor temp = player.getBoard().getProfessor(color);
                            game.getPlayer(playerId).getBoard().putProfessor(temp);
                            int playerTemp = player.getId();
                            game.getPlayer(playerTemp).getBoard().removeProfessor(color);
                            game.groupMultiMessage(new SingleBoardMessage(game, player.getId()));
                        }
            }
        }
    }
    /** activates the character effect if the player has enough money or throws an exception*/
    public  void useCharacter(int cardId, ParameterObject parameters, int playerId){
        CharacterCard card = game.getCharacter(cardId);
        Player player = game.getPlayer(playerId);
        if(player.getHand().enoughCoin(card.getCost())){
            try {
                player.getHand().payCoin(card.getCost());
                card.cardEffect(parameters, game);
            }catch (RuntimeException e){
                throw new IllegalMoveException(e.getMessage());
            }
            game.setCardBonusActive(cardId);
            game.groupMultiMessage(new WholeGameMessage(game));
            game.sendMultiMessage();
        }
        else
            {throw new IllegalMoveException("not enough money");}
    }


    public int calculateInfluence(int playerId){
        int influence=0;
        if(game.isBonusActive(8))  //card 8 effect
            influence=influence+2;
        MotherNature theOne=game.getMotherNature();
        Island where=game.getIslands().get(theOne.getPosition());
        ArrayList<Student> students=new ArrayList<>(where.getEveryStudents());
        Player player = game.getPlayer(playerId);
        TowerColor color=player.getColorT();
        Student stud;
        for (Student student : students) {
            stud = student;
            if (player.getBoard().hasProfessor(stud.getColor()) && !(stud.getColor() == game.getTargetColor() && game.isBonusActive(9)))//card 9 effect
                influence++;

        }//card 6 effect
        if(!game.isBonusActive(6)){
            ArrayList<Tower> towers=new ArrayList<>(where.getEveryTower());
            for (Tower tower : towers) {
                if (color.equals(tower.getColor()))
                    influence++;
            }
        }
        return influence;
    }
    /** if an island can be conquered based on influence, it changes towers on the island or puts another one*/
    public void islandConquest(int islandId) throws NullPointerException{
        int  maxInf;
        Island island=game.getIsland(islandId);
        Player conqueror;
        ArrayList<Integer> influence= new ArrayList<>();
        for (Player player: game.getPlayers()) {
            influence.add(calculateInfluence(player.getId()));
        }
        maxInf=influence.stream().max(Comparator.naturalOrder()).orElse(0);
        if(Collections.frequency(influence, maxInf)==1) {
            conqueror=game.getPlayer(influence.lastIndexOf(maxInf));
            if (!island.getTowers().isEmpty()) {
                if (island.getTowers().get(0).getColor() != conqueror.getColorT())
                    changeTower(island, conqueror);
            } else
                putTowerFromBoardToIsland(island, conqueror);
        }
    }


}




