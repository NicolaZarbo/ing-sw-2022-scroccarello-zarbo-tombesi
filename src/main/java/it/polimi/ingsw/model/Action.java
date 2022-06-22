package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.TokenColor;
import it.polimi.ingsw.enumerations.TowerColor;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.exceptions.NoPlaceAvailableException;
import it.polimi.ingsw.exceptions.NoTokenFoundException;
import it.polimi.ingsw.util.ParameterObject;
import it.polimi.ingsw.messages.servermessages.*;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.tokens.*;

import java.util.*;

/**The action phase of the game. It allows players to do their actions such as move student tokens, move mother nature and conquer an island.*/
public class Action {
    private final Game game;
    private int movedStudent;

    /**It builds the action phase of the game.
     * @param game the game*/
    public Action(Game game){
        this.game=game;
        movedStudent=0;
    }

    /** It moves a student token from the entrance to the dining room.
     * @param idPlayer id of the player which is moving the token
     * @param idStud id of the moved student token
     * @exception NoTokenFoundException if the entrance does not contains the required token*/
    public void moveInDiningRoom(int idPlayer, int idStud){
        try {
            Player player = game.getPlayer(idPlayer);
            Board board = player.getBoard();
            Student stud = board.getStudentFromEntrance(idStud);
            board.moveToDiningRoom(stud);
            if (board.foundCoin(stud))
                player.getHand().addCoin();
            if (canHaveTeacher(stud.getColor(), idPlayer))
                setTeacher(stud.getColor(), idPlayer);
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

    /** It moves a student from the entrance to a target island.
     * @param idStud target id of the student token
     * @param idPlayer id of the player which is making the action
     * @param idIsland target id of the island*/
    public void moveToIsland(int idPlayer,int idStud, int idIsland) throws NullPointerException{
        Student stud = game.getPlayer(idPlayer).getBoard().getStudentFromEntrance(idStud);
        Island island = game.getIsland(idIsland);
        island.addStudent(stud);
        game.groupMultiMessage(new  IslandsMessage(game));
        game.groupMultiMessage(new SingleBoardMessage(game,idPlayer));
        movedStudent++;
        if(movedStudent<3)
            game.sendMultiMessage();
        else {
            movedStudent=0;
        }


    }

    /**It moves mother nature a number of steps, then checks on the final island for tower conquest and island merge.
     * @param steps amount of steps mother mature moves*/
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
        if (isUnifiableBefore( mother.getPosition())) {
            unifyBefore( mother.getPosition());
        }
        game.groupMultiMessage(new MotherPositionMessage(game));
        if(game.getIslands().size()<=3)
            game.gameOver();
    }

    /**It moves all the students from a target cloud to the board of player, it is also the last action of the player's turn.
     * @param playerId id of the player
     * @param cloudId id of the target cloud*/
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

    /**It checks if the current island can be unified with its following based on the towers on them.
     * @param pos id of the island to check
     * @return •true: the islands can be merged
     * <p>•false: the islands cannot be merged together</p>*/
    public boolean isUnifiableNext(int pos){
        int size = game.getIslands().size();
        Island central=game.getIslands().get(pos);
        Island next=game.getIslands().get(Math.floorMod(pos+1,size));
        if( !central.getTowers().isEmpty() && !next.getTowers().isEmpty())
            return central.getTowers().get(0).getColor() == next.getTowers().get(0).getColor();
        return false;
    }

    /**It checks if the current island can be unified with its previous based on the towers on them.
     * @param pos id of the island to check
     * @return •true: the islands can be merged
     * <p>•false: the islands cannot be merged together</p>*/
    public  boolean isUnifiableBefore(int pos){
        int backpos=pos-1;
        if(pos==0)
            backpos=game.getIslands().size()-1;
        return isUnifiableNext(backpos);
    }

    /**It merges the island with its following.
     * @param pos id of the island which is triggering merge*/
    public  void unifyNext(int pos) {
        int islandNumber = game.getIslands().size();
        Island central = game.getIslands().get(pos);
        Island next = game.getIslands().get(Math.floorMod(pos + 1, islandNumber));
        if (central.getTowers().get(0).getColor() == next.getTowers().get(0).getColor()) {
            central.addSubIsland(next);
            game.getIslands().remove(next);
            central.incrementIslandSize();
            }

        game.groupMultiMessage(new IslandsMessage(game));

    }

    /**It merges the island with its previous.
     * @param pos id of the island which is triggering merge*/
    public  void unifyBefore( int pos) {
        int backpos=pos-1;
        if(pos==0)
            backpos=game.getIslands().size()-1;
        unifyNext(backpos);
        MotherNature mother= game.getMotherNature();
        mother.changePosition(Math.floorMod(mother.getPosition()-1,game.getIslands().size()));
    }

    /** It conquers the island by putting on them the tower corresponding to the player.
     @param player  the player who won the influence contest
     @param island the island to conquer*/
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

    /** It checks whether the player is the leader of the game or not (for team game only).
     * @param player the player to check
     * @return •true: the player owns the towers (or the game is not team game)
     * <p>•false: the player does not own the towers</p>*/
    private boolean isSquadLeader(Player player){
        if(game.getNPlayers()!=4)
            return true;//believe in yourself, be your own leader
        return player.getId()<2;
    }

    /**It checks if the player deserves to have a professor token of a certain color on his board or not.
     * @param playerId id of the player
     * @param color target token color
     * @return  •true: the board can accept professor
     * <p>•false: the professor cannot visit the player's board</p>*/
    public  boolean canHaveTeacher(TokenColor color, int playerId){
        Player[] players=game.getPlayers();
        boolean b=true;
        Player playercheck=game.getPlayer(playerId);
        Board playerBoard=playercheck.getBoard();

           int tokenplayer = 0;
        int othercount;
        for (int i = 0; i < playerBoard.getDiningRoom()[color.ordinal()].length; i++)
            if(playerBoard.getDiningRoom()[TokenColor.getIndex(color)][i]!=null)
                tokenplayer++;
        for (Player player : players) {
            othercount = 0;
            if (!player.equals(playercheck)) {
                for (int i = 0; i < player.getBoard().getDiningRoom()[color.ordinal()].length; i++) {
                    if (player.getBoard().getDiningRoom()[TokenColor.getIndex(color)][i] != null)
                        othercount++;
                }//card 2 effect
                if (othercount >= tokenplayer && !(game.isBonusActive(2) && othercount <= tokenplayer))
                    b = false;
            }
        }
        return b;
    }

    /**It sets the professor of the specific color on the player's board, if he can have it. If it is on another board it is moved away to the player's board, otherwise it is simply added to the board.
     * @param playerId id of the player
     * @param color color of the target professor*/
    public  void setTeacher(TokenColor color, int playerId){
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

    /**It activates the character's effect if the player has enough money, otherwise it throws an exception (for expert mode only).
     * @param playerId id of the player activating the effect
     * @param cardId id of the target character card
     * @param parameters parameter object for the card
     * @see ParameterObject*/
    public  void useCharacter(int cardId, ParameterObject parameters, int playerId){
        CharacterCard card = game.getCharacter(cardId);
        Player player = game.getPlayer(playerId);
        if(game.getCardBonusActive()!=0){
            throw new IllegalMoveException("an effect has already bean activated");
        }
        if(player.getHand().enoughCoin(card.getCost()) ){
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

    /**It calculates the influence on the island for the player when mother nature visits it.
     * @param playerId id of the relative player
     * @return the value of influence the player has in the island*/
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

    /**It conquers the island.
     * @param islandId id of the conquered island*/
    public void islandConquest(int islandId) throws NullPointerException{
        int  maxInf;
        Island island=game.getIsland(islandId);
        Player conqueror;
        ArrayList<Integer> influence= new ArrayList<>();
        for (Player player : game.getPlayers()) {
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

    /**It changes the towers on a conquered island from one player to one another.
     * @param island the target island
     * @param newOwner the conqueror player*/
    private void changeTower(Island island , Player newOwner){
        ArrayList<Tower> removedT=new ArrayList<>(island.getEveryTower());
        island.removeEveryTower();
        for (Player player: game.getPlayers()) {
            if(player.getColorT()==removedT.get(0).getColor()){
                player.getBoard().initTowers(removedT);
                game.groupMultiMessage(new SingleBoardMessage(game,player.getId()));
            }
        }
        this.putTowerFromBoardToIsland(island,newOwner);
    }


}




