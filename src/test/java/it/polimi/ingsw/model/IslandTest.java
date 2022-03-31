package it.polimi.ingsw.model;

import junit.framework.TestCase;

import javax.swing.*;
import java.util.ArrayList;

public class IslandTest extends TestCase {
    public void testMerge(){
       Island i1=new Island(1);
       Island i2=new Island(2);
       Tower t1=new Tower(TowerColor.black,1);
       Tower t2=new Tower(TowerColor.black,2);
       i1.setTower(t1);
       i2.setTower(t2);

       i1.mergeIslands(i2,null);
       for(int i=0;i<i1.getTower().size();i++)
           System.out.println("torre nera n. " + i1.getTower().get(i).getId());
    }

}