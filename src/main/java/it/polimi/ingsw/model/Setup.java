package it.polimi.ingsw.model;

public class Setup {
    public static Player[] createPlayer(boolean easy , int nPlayer, int stId){

        Player players[] =new Player[nPlayer];

        for (int i=0; i<nPlayer;i++){
            Hand man= Setup.createHand(i, easy, 0, 10);
            Board plan = Setup.createBoard(i, nPlayer);
            players[i]= new Player(stId+i, Mage.getMage(i), man, plan);
        }
        return players;
    }
    //genera le isole con all'interno array studenti con studenti null
    public static Cloud[] createClouds(int nPlayer, int stId){
        Cloud[] clouds = new Cloud[nPlayer];
        Student[] studs;
        int dimIsl;
        if (nPlayer==3) {
            dimIsl = 4;
        }else {
            dimIsl=3;
        }
        studs = new Student[dimIsl];
        for(int k=0; k<dimIsl;k++){
            studs[k]=null;
        }
        for(int i = 0; i<nPlayer; i++){

            clouds[i]= new Cloud(studs,stId+i);
        }
        return  clouds;
    }
    //public static ArrayList<Integer> createIslands(){}   TODO creaIsole
    //generazione mano da associare a player
    private static Hand createHand(int index, boolean easy, int stId, int nCards){
        AssistantCard[] ass ;
        int stIdCards= index*nCards + stId;
        ass = new AssistantCard[nCards];
        for(int j=0;j<nCards; j++){
            ass[j]= new AssistantCard(stIdCards+j,j,j/2, Mage.getMage(index));
        }
        Hand hand =new Hand(ass);
        if (!easy) {
            hand.addCoin();
        }
         return hand;
    }
    private static Board createBoard(int index, int nPlayer){
        Board pla ;
        TowerColor color;
        int nTower, dimEntry;
        if (nPlayer == 3){
            nTower=6;
            dimEntry = 9;
        }
        else{
            nTower =8;
            dimEntry = 7;
        }
        if (nPlayer== 4 && index>1){
            color = TowerColor.getColor(index-2);
            nTower=0;
        }
        else
            color= TowerColor.getColor(index);
        pla=new Board(nTower,dimEntry,color,index);
        return pla;
    }


}
