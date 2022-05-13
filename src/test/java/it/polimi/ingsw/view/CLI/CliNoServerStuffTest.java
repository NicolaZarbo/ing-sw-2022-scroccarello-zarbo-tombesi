package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.view.CentralView;
import it.polimi.ingsw.view.RemoteView;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class CliNoServerStuffTest  extends TestCase {

    CliNoServer cli;
    CentralView view;
    Controller controller;
    Game model;

    public static void main(String[] args) {
        CliNoServer cli= new CliNoServer();
        ArrayList<CentralView> viewss= new ArrayList<>();
        CentralView view=cli.getView();
        viewss.add(view);
        cli.setUpGameAlone();//setter lobby
        Game model= new Game(cli.easy, cli.nPlayer);
        Controller controller= new Controller(model);

        model.getSetupPhase().setPreOrder(viewss.stream().map(CentralView::getName).toList());
        model.addObserver(view);
        view.addObserver(controller);
        controller.addObserver(cli);
        model.getSetupPhase().startPersonalisation();
    }

    public void testAyo() {

    }
}
