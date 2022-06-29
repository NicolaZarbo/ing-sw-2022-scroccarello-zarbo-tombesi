package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.client.CLI.printers.BoardsPrinter;
import it.polimi.ingsw.client.CLI.printers.IslandsPrinter;
import it.polimi.ingsw.client.CLI.printers.Printer;
import it.polimi.ingsw.exceptions.IllegalMoveException;
import it.polimi.ingsw.util.ParameterObject;
import it.polimi.ingsw.client.CLI.printers.CharacterPrinter;
import it.polimi.ingsw.view.CentralView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

/**The handler of characters invocation (expert mode only). It contains methods to handle character's invocation, which are called by reflection.*/
public class CharacterInputManager {
    final CentralView game;

    /**It searches the first student token of a certain color in a list.
     * @param color the index related to the token color
     * @param students the list of ids of student tokens where to look for
     * @return the first id of the token with the specified color (if present).
     * @exception NullPointerException if the list doesn't contain any token of the specified color*/
    private int studentInListFromColor(int color, List<Integer> students) throws NullPointerException{
        for (Integer studId:students) {
            if(studId/26==color)
                return studId;
        }
        throw new NullPointerException("student not present");
    }

    /**It activates character 1 effect.
     * @see it.polimi.ingsw.model.characters.Character1*/
    private void character1(){
        List<Integer> studentOnTop=game.getStudentsOnCard().get(1);
        System.out.println(IslandsPrinter.print(game));
        System.out.println(CharacterPrinter.printStudentOnTop(1,studentOnTop));
        System.out.println(Printer.PINK+"select a student color in this card followed by a target Island \n+"+Printer.BLUE+"[Color IslandID]\n"+ Printer.RED+"or press enter to go back"+Printer.RST);
        try{
        String input = (new Scanner(System.in)).nextLine();
            if(input.equalsIgnoreCase("")) {
                throw new IllegalMoveException();
            }
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

    /**It activates character 2 effect for the whole turn. It lets the player have the control over one professor token even if he has the same number of tokens of that specific color as another player.
     * @see it.polimi.ingsw.model.characters.TurnEffectCharacter*/
    private void character2(){
        ParameterObject par = new ParameterObject();
        game.playCharacter(2,par);
    }

    //private void character3(){}

    //fixme don't we said that we excluded character 4???
    private  void character4(){
        ParameterObject par = new ParameterObject();
        game.playCharacter(4,par);
    }

    //private  void character5(){}

    /**It activates character 6 effect for the whole turn. It doesn't count the towers of island(s) when calculating influence.
     * @see it.polimi.ingsw.model.characters.TurnEffectCharacter*/
    private  void character6(){
        ParameterObject par = new ParameterObject();
        game.playCharacter(6,par);
    }

    /**It activates character 7 effect.
     * @see it.polimi.ingsw.model.characters.Character7*/
    private void character7(){
        List<Integer> studentOnTop=game.getStudentsOnCard().get(7);
        System.out.println(CharacterPrinter.printStudentOnTop(7,studentOnTop));
        System.out.println("select up to 3 student color in this card\n"+Printer.BLUE+"[Color Color Color] \n"+Printer.RED+"press enter to go back"+Printer.RST);
        String input = (new Scanner(System.in)).nextLine();
        if(input.equalsIgnoreCase("")) {
            throw new IllegalMoveException();
        }
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
            System.out.println("now choose from your entrance the color of "+nOfStud+" students\n"+Printer.BLUE+"[Color Color Color");
            input=(new Scanner(System.in)).nextLine();
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

    /**It activates character 8 effect for the whole turn. The player which activates it has two additional points in influence calculation.
     * @see it.polimi.ingsw.model.characters.TurnEffectCharacter*/
    private void character8(){
        ParameterObject par = new ParameterObject();
        game.playCharacter(8,par);
    }

    /**It activates character 9 effect.
     * @see it.polimi.ingsw.model.characters.Character9*/
    private  void character9(){
        System.out.println("Choose a target color\n "+Printer.BLUE+"[Color]\n"+Printer.RED+"press enter to go back"+Printer.RST);
        String input = (new Scanner(System.in)).nextLine().toLowerCase();
        if(input.equalsIgnoreCase("")) {
            throw new IllegalMoveException();
        }
        int color=InputManagerCli.convertStudentColorToInteger(input.substring(0,1));
        ParameterObject parameter = new ParameterObject(color);
        game.playCharacter(9,parameter);
    }

    /**It activates character 10 effect.
     * @see it.polimi.ingsw.model.characters.Character10*/
    private  void character10(){
        System.out.println(BoardsPrinter.print(game));
        System.out.println(Cli.IMP+"Choose up to 2 student in your Dining Room"+Cli.RST+" \n"+Printer.BLUE+"[Color Color]"+ Printer.RED+"\nor press enter to go back"+Printer.RST);
        String input = (new Scanner(System.in)).nextLine().toLowerCase();
        if(input.equalsIgnoreCase("")) {
            throw new IllegalMoveException();
        }
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
        System.out.println(" Now pick "+numberPicked+" students from your Entrance\n"+Printer.BLUE+"[Color Color]");
        input = (new Scanner(System.in)).nextLine().toLowerCase();
        multipleInput=input.split("\s");
        if(multipleInput.length>2)
        {
            throw new IllegalMoveException("you should've choose "+ numberPicked+" students color");
        }
        for (int i = 0; i < numberPicked; i++) {
            if(game.getPersonalPlayer().getBoard().hasStudentOfColorInDining(InputManagerCli.convertStudentColorToInteger(multipleInput[i])))
                studentFromEntrance[i]=(game.getPersonalPlayer().getBoard().getStudentFromColorInEntrance(InputManagerCli.convertStudentColorToInteger(multipleInput[i])));
        }
        ParameterObject parameter= new ParameterObject(game.getPersonalPlayer().getId(),studentFromEntrance,studentFromDining);
        game.playCharacter(10,parameter);
    }

    /**It activates character 11 effect.
     * @see it.polimi.ingsw.model.characters.Character11*/
    private  void character11(){
        List<Integer> studentOnTop=game.getStudentsOnCard().get(11);
        System.out.println(BoardsPrinter.printPersonal(game));
        System.out.println(CharacterPrinter.printStudentOnTop(11,studentOnTop));
        System.out.println("Choose a student on the card by its color\n"+Printer.BLUE+"[Color]"+ Printer.RED+"\nor press enter to go back"+Printer.RST);
        String input = (new Scanner(System.in)).nextLine().toLowerCase();
        if(input.equalsIgnoreCase("")) {
            throw new IllegalMoveException();
        }
        int color=InputManagerCli.convertStudentColorToInteger(input.substring(0,1));
        int student=studentInListFromColor(color,studentOnTop);
        ParameterObject parameter= new ParameterObject(student,game.getPersonalPlayer().getId());
        game.playCharacter(11,parameter);
    }

    //private static void character12(){}

    /**It builds the handler of the client's view.
     * @param view the specific view to work on*/
    public CharacterInputManager(CentralView view){
        this.game=view;
    }

    /**It activates the relative character's method based on the input of the client.
     * @param in the id of the card to activate
     * @exception IllegalMoveException if the player wants to play an unavailable character*/
    public void characterManagerCall(int in)  {
        if(admittible(in)){
        try {
            Class<?> k= this.getClass();
            Method method= k.getDeclaredMethod("character"+in);
            method.setAccessible(true);
            method.invoke(this);
        }catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            throw new IllegalMoveException("not available "+in+ " character method");
        }
    //TODO check for exception trowed in the above methods

        }

    }
    /**It checks if the input of the client corresponds to an available character or not.
     * @param param the number of the character sent by the client*/
    private boolean admittible(int param){
        return (param<13 && param>0 && param!=3 && param!=4 && param!=5 && param!=12);
    }
}
