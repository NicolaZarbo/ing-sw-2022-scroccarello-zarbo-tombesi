package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;

import java.util.*;

public class Main {
    public static String[] players;
    public static boolean[][] assistantsplayed;
    public static boolean[][] towersplayed;
    public static ArrayList<int[]> clouds;
    public static int mother;        //il suo valore indica la posizione (ha il valore dell'isola)
    public static  List<Integer> islands;
    public static int bag; //il suo valore indica il numero di pedine rimaste nel sacchetto


    public static void main(String[] args) {
        boolean win=false;
        boolean[][] towersplayed;
        bag=120;
        clouds=new ArrayList<int[]>();
        Scanner sc = new Scanner(System.in);
        int numPlayers= sc.nextInt();
        players=new String[numPlayers];
        assistantsplayed =new boolean[numPlayers][10];
        for (boolean[] booleans : assistantsplayed) Arrays.fill(booleans, false);

        if(numPlayers==2)
            towersplayed=new boolean[numPlayers][8];
        else if(numPlayers==3)
            towersplayed=new boolean[numPlayers][6];
        else
            towersplayed=new boolean[2][8];

        for(int i=0;i<numPlayers;i++)
            Arrays.fill(towersplayed[i], false);


        sc.nextLine();
        for(int i=0;i<numPlayers;i++) {
            System.out.println("Inserire il nick del player "+i);
            players[i]=sc.nextLine();
        }
            for(int i=0;i<numPlayers;i++)
                clouds.add(new int[3]);
        Random rand = new Random();
        int i=rand.nextInt(12);
        mother=i;
        //mettere studente su ogni isola (ricorda nel bag all'inizio sono solo 10 pedine,2 per ogni colore
        // opposta all'isola di madre natura c'è un'isola senza studente)
        int firstRoundPlayer=rand.nextInt(numPlayers);
        int[] cardTemp=new int[numPlayers];
        int[] ordturn=new int[numPlayers];
        while(!someoneWon()){
            for(int k=0;i<numPlayers;i++){
                System.out.println("which assistant card does player "+k+1+"wants to play?");
                int j=sc.nextInt();
                if(!assistantsplayed[k][j])
                    cardTemp[k]=j;
                else
                   k--;

            }
           // ControllerRound round=new ControllerRound(new Game());
           // round.controlPianification();
           // ordturn=round.getActualOrder();

            //pesca 3 students dal bag
            //ogni giocatore sceglie un'assistente da giocare, si procede in ordine orario
            //fase azione di ogni giocatore
            //si spostano 3 studenti verso la dining room o l'isola
            //si sposta madre natura sull'isola
            //si sceglie una nuvola e si prendono 3 studenti
            //possibilità giocare personaggio


        }


        System.out.println("bruh");

    }


    public static boolean win(int player){
        boolean b=true;
        for(int i=0;i<towersplayed[player].length;i++)
            if(!towersplayed[player][i])
                b=false;

        return b;
    }
    public static boolean someoneWon(){
       boolean b=false;
       boolean found=false;
       int count=10;
        for(int i=0;i<towersplayed.length;i++)
            if(win(i))
                b=true;
            if(islands.size()==3)
                b=true;

        for (boolean[] booleans : assistantsplayed) {
            for (boolean aBoolean : booleans) {
                if (aBoolean)
                    count--;
            }
            if (count == 0 && !found) {
                b = true;
                found = true;
            }
        }

            return b;
    }
}
