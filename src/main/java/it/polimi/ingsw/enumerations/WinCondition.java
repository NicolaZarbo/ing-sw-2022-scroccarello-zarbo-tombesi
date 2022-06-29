package it.polimi.ingsw.enumerations;

/**All the possible game over scenarios. Game over occurs when:
 * <p>•bag is empty</p>
 * <p>•team ends its tower</p>
 * <p>•10 turns have passed</p>
 * <p>•only 3 islands remain (due to merging)</p>*/
public enum WinCondition {
    BagEmpty,
    NoTowersLeft,
    TenTurnsPassed,
    ThreeIslandsLeft,
}
