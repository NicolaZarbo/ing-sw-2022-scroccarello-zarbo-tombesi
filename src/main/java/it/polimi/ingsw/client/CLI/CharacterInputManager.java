package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.printers.BoardsPrinter;
import it.polimi.ingsw.client.CLI.printers.IslandsPrinter;
import it.polimi.ingsw.client.CLI.printers.Printer;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.model.character.ParameterObject;
import it.polimi.ingsw.client.CLI.printers.CharacterPrinter;
import it.polimi.ingsw.view.CentralView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.IntFunction;

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
        System.out.println(IslandsPrinter.print(game));
        System.out.println(CharacterPrinter.printStudentOnTop(1,studentOnTop));
        System.out.println(Printer.PINK+"select a student color in this card followed by a target Island \n"+ Printer.RED+"or press enter to go back"+Printer.RST);
        try{
        String input = (new Scanner(System.in)).nextLine();
            if(input.equalsIgnoreCase(""))
                return;
        String[] multipleInput=input.toLowerCase().split("\s");
        int islandId;
        int studID = studentInListFromColor(InputManagerCli.convertStudentColorToInteger(multipleInput[0]), studentOnTop);
        islandId= Integer.parseInt(multipleInput[1]);
        ParameterObject par = new ParameterObject(studID,islandId);
        game.playCharacter(1, par);
        }
        catch (NumberFormatException e){
            System.out.println("pick the island by its number");
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
        System.out.println("Choose a target color");
        String input = (new Scanner(System.in)).nextLine().toLowerCase().substring(0,1);
        int color=InputManagerCli.convertStudentColorToInteger(input);
        ParameterObject parameter = new ParameterObject(color);
        game.playCharacter(9,parameter);
    }
    private  void character10(){
        System.out.println(BoardsPrinter.print(game));
        System.out.println(Cli.IMP+"Choose up to 2 student in your Dining Room"+Cli.RST+" \n[Color Color]"+ Printer.RED+"\nor press enter to go back"+Printer.RST);
        String input = (new Scanner(System.in)).nextLine().toLowerCase();
        if(input.equalsIgnoreCase(""))
            return;
        String[] multipleInput=input.split("\s");
        if(multipleInput.length>2)
        {
            throw new IllegalMoveException("you can choose 2 students color at max");
        }
        int[] studentFromDining = new int[multipleInput.length];
        int numberPicked=0;
        for (int i = 0; i<multipleInput.length; i++) {
            if(game.getPersonalPlayer().getBoard().hasStudentOfColorInDining(InputManagerCli.convertStudentColorToInteger(multipleInput[i]))){
                studentFromDining[i]=(game.getPersonalPlayer().getBoard().getStudentFromColorInDining(InputManagerCli.convertStudentColorToInteger(multipleInput[i])));
                numberPicked++;
            }
        }
        int[] studentFromEntrance = new int[numberPicked];
        System.out.println(" Now pick "+numberPicked+" students from your Entrance");
        input = (new Scanner(System.in)).nextLine().toLowerCase();
        multipleInput=input.split("\s");
        if(multipleInput.length>2)
        {
            throw new IllegalMoveException("you should've choose "+ numberPicked+" students color at max");
        }
        for (int i = 0; i < numberPicked; i++) {
            if(game.getPersonalPlayer().getBoard().hasStudentOfColorInDining(InputManagerCli.convertStudentColorToInteger(multipleInput[i])))
                studentFromEntrance[i]=(game.getPersonalPlayer().getBoard().getStudentFromColorInEntrance(InputManagerCli.convertStudentColorToInteger(multipleInput[i])));
        }
        ParameterObject parameter= new ParameterObject(game.getPersonalPlayer().getId(),studentFromEntrance,studentFromDining);
        game.playCharacter(10,parameter);
    }
    private  void character11(){
        List<Integer> studentOnTop=game.getStudentsOnCard().get(11);
        System.out.println(BoardsPrinter.printPersonal(game));
        System.out.println(CharacterPrinter.printStudentOnTop(11,studentOnTop));
        System.out.println("Choose a student on the card by its color");
        String input = (new Scanner(System.in)).nextLine().toLowerCase().substring(0,1);
        int color=InputManagerCli.convertStudentColorToInteger(input);
        ParameterObject parameter= new ParameterObject(color,game.getPersonalPlayer().getId());
        game.playCharacter(11,parameter);
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
        }catch (IllegalMoveException e ){
    //TODO check for exception trowed in the above methods
        }
        }

    }
}
