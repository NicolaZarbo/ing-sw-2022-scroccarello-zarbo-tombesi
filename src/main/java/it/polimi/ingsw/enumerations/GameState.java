package it.polimi.ingsw.enumerations;

/**Enumeration used to represent the different phases the game can be on*/
public enum GameState {
    /**Setup phase of the players. Players are created after chosing a valid nickname, a tower color representing the team color and a wizard of reference.*/
    setupPlayers,
    /**Planning phase. In this phase players, according to the previous round order (or the joining lobby order for the first round), players have to play one assistant card choosing by their available cards. This will decide the playing order and the possible mother movements for each player.*/
    planPlayCard,
    /**Part one of action phase. The player has to choose three students, one per time, and move them in the dining room or on the islands. */
    actionMoveStudent,
    /**Part two of action phase. The player has to move mother nature according to the maximum number of steps indicated on the card he played on planning phase.*/
    actionMoveMother,
    /**Part three of action phase. The player has to choose one of the clouds and refill its entrance with the students on it.*/
    actionChooseCloud
}
