package it.polimi.ingsw.model;

import it.polimi.ingsw.messages.server.*;
import it.polimi.ingsw.model.character.CharacterCard;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.model.token.*;
import it.polimi.ingsw.observer.Observable;

import java.util.*;

public class Turn extends Observable<ServerMessage> {
    private Game game;

    public Turn (Game game){
        this.game=game;
    }
    public void moveInHall(int idPlayer,int idStud){
        Player player=game.getPlayer(idPlayer);
        Board board= player.getBoard();
        Student stud = board.getStudentFromEntrance(idStud);
        board.moveToDiningRoom(stud);
        if(board.foundCoin(stud))
            player.getHand().addCoin();
        if(canHaveTeacher(stud.getColor(),idPlayer))
            getTeacher(stud.getColor(),idPlayer);

        this.notify(new SingleBoardMessage(game, idPlayer));

    }

    public void moveToIsland(int idPlayer,int idStud, int idIsland) throws NullPointerException{
        Student stud = game.getPlayer(idPlayer).getBoard().getStudentFromEntrance(idStud);
        Island island = game.getIsland(idIsland);
        island.addStudent(stud);
        //use a multiple message to reduce the number of connection use
        this.notify(new IslandsMessage(game));
        this.notify(new SingleBoardMessage(game, idPlayer));

    }
    //moves mother nature and checks island
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

        this.notify(new MotherPositionMessage(game));
    }
    //last action of the turn
    public void moveFromCloudToEntrance(int cloudId,int playerId){
        Board board= game.getPlayer(playerId).getBoard();
        Cloud[] clouds=game.getClouds();
        if(cloudId>=0 && cloudId<clouds.length && clouds[cloudId].getStud()[0]!=null){
            Student[] students=clouds[cloudId].getStud();
            for(int i=0;i<students.length;i++) {
                board.putStudentOnEntrance(students[i]);
                students[i]=null;
            }
        }
        else throw new RuntimeException("unavailable cloud");

        game.resetBonus();
        this.notify(new CloudMessage(game));
        this.notify(new SingleBoardMessage(game,playerId));
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
            central.addAllStudents(next.getStudents());
            central.addAllTowers(next.getTowers());
            game.getIslands().remove(next);
            central.incrementIslandSize();
            if (game.getMotherNature().getPosition() == pos + 1)
                game.getMotherNature().changePosition(pos);
            }
        this.notify(new IslandsMessage(game));

    }
    public  void unifyBefore( int pos) {
        int islandNumber = game.getIslands().size();
        Island central = game.getIslands().get(pos);
        Island before = game.getIslands().get(Math.floorMod(pos - 1, islandNumber));
        if (central.getTowers().get(0).getColor() == before.getTowers().get(0).getColor()) {
            central.addAllStudents(before.getStudents());
            central.addAllTowers(before.getTowers());
            game.getIslands().remove(before);
            central.incrementIslandSize();
            if (game.getMotherNature().getPosition() == pos - 1)
                game.getMotherNature().changePosition(pos);
            }
        this.notify(new IslandsMessage(game));

    }
    //if it is a team game, insert the mainplayer id in idPlayer
    public void putTowerFromBoardToIsland(Island island,Player player){
        Board board=player.getBoard();
        island.setTower(board.getTower());
        this.notify(new IslandsMessage(game));
        this.notify(new SingleBoardMessage(game,player.getId()));

    }
    private void changeTower(Island island , Player newOwner){
        ArrayList<Tower> removedT=new ArrayList<>(island.getTowers());
        island.getTowers().removeAll(removedT);
        for (Player player: game.getPlayers()) {
            if(player.getColorT()==removedT.get(0).getColor()){
                player.getBoard().addTower(removedT);
                this.notify(new SingleBoardMessage(game,player.getId()));//this may cause a visual problem in the time between messages
            }
        }
        this.putTowerFromBoardToIsland(island,newOwner);
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
                        }
            }

        }
        this.notify(new SingleBoardMessage(game, playerId));
        //serverSendAll(new SingleBoardMessage(game, playerId))
    }

    public  void useCharacter(int cardId, ParameterObject parameters, int playerId){
        CharacterCard card = game.getCharacter(cardId);
        Player player = game.getPlayer(playerId);
        if(player.getHand().enoughCoin(card.getCost())){
            player.getHand().payCoin(card.getCost());
            card.cardEffect( parameters,  game );
            this.notify(new CharacterUpdateMessage(cardId,game));
        }
        else
            {throw new RuntimeException("not enough money");}
    }


    public int calculateInfluence(int playerId){
        int influence=0;
        if(game.isBonusActive(8))  //card 8 effect
            influence=influence+2;
        MotherNature theOne=game.getMotherNature();
        Island where=game.getIslands().get(theOne.getPosition());
        ArrayList<Student> students=where.getStudents();
        Player player = game.getPlayer(playerId);
        TowerColor color=player.getColorT();
        Student stud;
        for (Student student : students) {
            stud = student;
            if (player.getBoard().hasProfessor(stud.getColor()) && !(stud.getColor() == game.getTargetColor() && game.isBonusActive(9)))//card 9 effect
                influence++;

        }//card 6 effect
        if(!game.isBonusActive(6)){
            ArrayList<Tower> towers=where.getTowers();
            for (Tower tower : towers) {
                if (color.equals(tower.getColor()))
                    influence++;
            }
        }
        return influence;
    }
    //call checks for influence and changes or inserts a tower
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




