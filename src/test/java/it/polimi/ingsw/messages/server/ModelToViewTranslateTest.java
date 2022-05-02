package it.polimi.ingsw.messages.server;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CLI.objects.SimplifiedBoard;
import it.polimi.ingsw.view.CLI.objects.SimplifiedPlayer;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class ModelToViewTranslateTest extends TestCase {

    public void testTranslateIsland() {
    }

    public void testTranslatePlayer() {
        Game g = new Game(true,2,12);
        int idPlaye=0;
        SimplifiedPlayer pp= ModelToViewTranslate.translatePlayer(g.getPlayers()).get(idPlaye);
        assertNotNull(pp);
    }

    public void testTranslateBoard() {
        Game g = new Game(true,2,12);
        int idPlaye=0;
        SimplifiedBoard bb=ModelToViewTranslate.translateBoard(g.getPlayer(idPlaye).getBoard());
        assertNotNull(bb);
        //System.out.println(bb);
    }

    public void testTranslateClouds() {
        Game g = new Game(true,2,12);
        ArrayList<Integer[]> fCloud = ModelToViewTranslate.translateClouds(g.getClouds());
        for (Integer[] arr:fCloud) {
            System.out.println(Arrays.toString(arr));
        }

    }
}