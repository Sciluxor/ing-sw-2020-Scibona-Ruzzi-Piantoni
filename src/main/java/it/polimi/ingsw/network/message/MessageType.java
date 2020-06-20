package it.polimi.ingsw.network.message;

/**
 * enum containing all the possible message Type
 */

public enum MessageType {
    //network messages
    START,NICK,CONFIG,WAITPLAYER,NUMBERPLAYER,GAMESTART,DISCONNECTION,NOTYOURTURN,
    //gamecontroller messages
    SELECTPOWERSFORMATCH, SELECTPOWER,STARTTURN, ENDTURN,POWERCHOICE,PLACEWORKERS,CHALLENGERCHOICE,CHOOSECARD,STOPPEDGAME,
    //roundcontroller messages
    WORKERCHOICE, MOVEWORKER, BUILDWORKER, WIN,LOSE,NONPERMCONSTRAINT,PERMCONSTRAINT,

    PING,CHAT

}
