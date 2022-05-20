package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.view.CLI.printers.BoardsPrinter;
import it.polimi.ingsw.view.CLI.printers.CharacterPrinter;
import it.polimi.ingsw.view.CLI.printers.Printer;
import it.polimi.ingsw.view.CentralView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

public class CharacterInputManager {
    CentralView game;

    private static void print(String in){
        System.out.println(in);
    }

    private int studentInListFromColor(int color, List<Integer> students) throws NullPointerException{
        for (Integer studId:students) {
            if(studId/26==color)
                return studId;
        }
        throw new NullPointerException("student not present");
    }
    private void character1(){
        List<Integer> studentOnTop=game.getStudentsOnCard().get(1);
        System.out.println(CharacterPrinter.printStudentOnTop(1,studentOnTop));
        System.out.println("select a student color in this card followed by a target Island \n"+Printer.PINK+"or press enter to go back"+Printer.RST);
        try{
        String input = (new Scanner(System.in)).nextLine();
            if(input.equalsIgnoreCase(""))
                return;
        String[] multipleInput=input.toLowerCase().split("\s");
        int islandId;
        int studID = studentInListFromColor(InputManagerCli.convertStudentColorToInteger(multipleInput[0]), studentOnTop);
        islandId= Integer.parseInt(multipleInput[1])-1;
        ParameterObject par = new ParameterObject(studID,islandId);
        game.playCharacter(1, par);
        }
        catch (NumberFormatException e){
            character1();
        }catch (NullPointerException e){
            System.out.println("color not present");
            character1();
        }
    }
    private void character2(){
        ParameterObject par = new ParameterObject();
        game.playCharacter(2,par);
    }
    //private void character3(){}
    private  void character4(){
        ParameterObject par = new ParameterObject();
        game.playCharacter(4,par);
    }
    //private  void character5(){}
    private  void character6(){
        ParameterObject par = new ParameterObject();
        game.playCharacter(6,par);
    }
    private void character7(){
        List<Integer> studentOnTop=game.getStudentsOnCard().get(7);
        System.out.println(CharacterPrinter.printStudentOnTop(7,studentOnTop));
        System.out.println("select 3 student color in this card, \n"+Printer.RED+"press enter to go back"+Printer.RST);
        String input = (new Scanner(System.in)).nextLine();
        if(input.equalsIgnoreCase(""))
            return;
        String[] multipleInput=input.toLowerCase().split("\s");
        int nOfStud=multipleInput.length;
        try{
            int[] studIdsFromCard= new int[nOfStud];
            int[] studIdsFromBoard= new int[nOfStud];
            int i = 0;
            for (String studColor : multipleInput) {
                studIdsFromCard[i]=studentInListFromColor( InputManagerCli.convertStudentColorToInteger(studColor),studentOnTop);
                if (studIdsFromCard[i]==-1)
                    throw new NullPointerException("Student color not present");
                i++;
            }
            System.out.println(BoardsPrinter.print(game));
            System.out.println("now choose from your board the color of "+nOfStud+" students");
            multipleInput=input.toLowerCase().split("\s");
            i = 0;
            for (String studColor : multipleInput) {
                studIdsFromBoard[i]=studentInListFromColor( InputManagerCli.convertStudentColorToInteger(studColor),studentOnTop);
                if (studIdsFromBoard[i]==-1)
                    throw new NullPointerException("Student color not present");
                i++;
            }
            ParameterObject par = new ParameterObject(game.getPersonalPlayer().getId(),studIdsFromBoard,studIdsFromCard);
            game.playCharacter(7,par);
        }catch (NullPointerException e){
            System.out.println("color not present");
            character7();
        }catch (NumberFormatException e){
            character7();
        }

    }
    private void character8(){
        ParameterObject par = new ParameterObject();
        game.playCharacter(8,par);
    }
    private  void character9(){
        //TODO
    }
    private  void character10(){
        //TODO
    }
    private  void character11(){
        //TODO
    }
    //private static void character12(){}

    public CharacterInputManager(CentralView view){
        this.game=view;
    }
    public void characterManagerCall(int in)  {
        if(in<13 && in>0){
        try {
            Class<?> k= this.getClass();
            Method method= k.getDeclaredMethod("character"+in);
            method.setAccessible(true);
            method.invoke(this);
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            throw new IllegalMoveException("not available "+in+ " char method");
        }
        }

    }
}
