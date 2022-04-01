package it.polimi.ingsw.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] players;
        int[][] assistants;
        boolean win=false;
        int mother;//il suo valore indica la posizione (ha il valore dell'isola)
        List<Integer> islands;
        int bag; //il suo valore indica il numero di pedine rimaste nel sacchetto
        ArrayList<int[]> clouds=new ArrayList<int[]>();
        Scanner sc = new Scanner(System.in);
        int numPlayers= sc.nextInt();
        players=new String[numPlayers];
        assistants=new int[numPlayers][10];
        sc.nextLine();
        for(int i=0;i<numPlayers;i++) {
            System.out.println("Inserire il nick del player "+i);
            players[i]=sc.nextLine();
            for (int j = 0; j < 10; j++)
                assistants[i][j] = 1;
        }
            for(int i=0;i<numPlayers;i++)
                clouds.add(new int[3]);
        Random rand = new Random();
        int i=rand.nextInt(12);
        mother=i;
        //mettere studente su ogni isola (ricorda nel bag all'inizio sono solo 10 pedine,2 per ogni colore
        // opposta all'isola di madre natura c'è un'isola senza studente)
        int firstRoundPlayer=rand.nextInt(numPlayers);
      /*  while(!win){
            //pesca 3 students dal bag
            //ogni giocatore sceglie un'assistente da giocare, si procede in ordine orario
            //fase azione di ogni giocatore
            //si spostano 3 studenti verso la dining room o l'isola
            //si sposta madre natura sull'isola
            //si sceglie una nuvola e si prendono 3 studenti
            //possibilità giocare personaggio


        }
        */

        System.out.println("bruh");

    }
}
