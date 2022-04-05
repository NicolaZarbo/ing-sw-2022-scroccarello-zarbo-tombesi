package it.polimi.ingsw.model;

import junit.framework.TestCase;
import it.polimi.ingsw.model.token.*;

public class BoardTest extends TestCase {

    Board boardTest=new Board(4,4,TowerColor.black,1);
    public void testProfessorInsertion(){
        if(!boardTest.hasProfessor(TokenColor.green)){
            System.out.println("there's not a green professor, i put it");
            boardTest.putProfessor(new Professor(1,TokenColor.green));
        }
        System.out.println("i just put the professor "+boardTest.getProfessor(TokenColor.green).getColor()+" and his id is "+boardTest.getProfessor(TokenColor.green).getId());
        boardTest.removeProfessor(TokenColor.green);
        if(!boardTest.hasProfessor(TokenColor.green))
            System.out.println("i just removed him");
    }
    public void testEntranceSetting(){
        Student stud[]=new Student[4];
        for(int i=0;i<4;i++){
            stud[i]=new Student(i,TokenColor.getColor(i));
        }
        boardTest.setEntrance(stud);
        System.out.print("the entrance has the following students: ");
        for(int i=0;i<4;i++){
            System.out.print(boardTest.getEntrance()[i].getId()+" "+boardTest.getEntrance()[i].getColor()+" ");
        }
    }
    public void testDiningRoomSetting(){
        Student[][] dining=new Student[TokenColor.listGetLastIndex()+1][10];
        for(int i=0;i<TokenColor.listGetLastIndex()+1;i++){
            for(int j=0;j<10;j++){
                if(i%2==0||j%3==1){
                    dining[i][j]=new Student(j,TokenColor.getColor(i));
                }
            }
        }
        boardTest.setDiningRoom(dining);
        System.out.println("these are the students in the dining room ");
        for(int i=0;i<TokenColor.listGetLastIndex()+1;i++){
            for(int j=0;j<10;j++){
                if(boardTest.getDiningRoom()[i][j]!=null){
                    System.out.print(boardTest.getDiningRoom()[i][j].getId()+"-"+boardTest.getDiningRoom()[i][j].getColor()+ " ");
                }
                else{
                    System.out.print("none ");
                }
            }
            System.out.println();
        }


    }
    public void testInsertionOnEntrance(){
        Student[] studInit=new Student[4];
        System.out.println("initial students on entrance are:");
        for(int i=0;i<4;i++){
            if(i%2==0){
                studInit[i]=new Student(i,TokenColor.getColor(i));
                System.out.print(i+"  ");
            }
            else System.out.print("none  ");
        }
        System.out.println();
        boardTest.setEntrance(studInit);
        Student studTest=new Student(4,TokenColor.getColor(4));
        try{
            boardTest.putStudentOnEntrance(studTest);
            System.out.println("inserted student "+studTest.getId()+" and now we have:");
            for(int i=0;i<4;i++){
                if(boardTest.getEntrance()[i]!=null)
                    System.out.print(boardTest.getEntrance()[i].getId()+"  ");
                else
                    System.out.print("none  ");
            }
        }
        catch(RuntimeException e){
            System.out.println("no space available");
        }

    }
    public void testInsertionOnDiningRoom(){
        Student[][] dining=new Student[TokenColor.listGetLastIndex()+1][10];
        for(int i=0;i<TokenColor.listGetLastIndex()+1;i++){
            for(int j=0;j<10;j++){
                if(i!=j){
                    dining[i][j]=new Student(j,TokenColor.getColor(i));
                }
            }
        }
        boardTest.setDiningRoom(dining);
        System.out.println("initial dining room ");
        for(int i=0;i<TokenColor.listGetLastIndex()+1;i++){
            for(int j=0;j<10;j++){
                if(boardTest.getDiningRoom()[i][j]!=null){
                    System.out.print(boardTest.getDiningRoom()[i][j].getId()+ " ");
                }
                else{
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        Student studTest=new Student(3,TokenColor.getColor(3));
        try{
            boardTest.moveToDiningRoom(studTest);
            System.out.println("new dining room");
            for(int i=0;i<TokenColor.listGetLastIndex()+1;i++){
                for(int j=0;j<10;j++){
                    if(boardTest.getDiningRoom()[i][j]!=null){
                        System.out.print(boardTest.getDiningRoom()[i][j].getId()+ " ");
                    }
                    else{
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
        }
        catch(RuntimeException e){
            System.out.println("no places available");
        }
    }
}