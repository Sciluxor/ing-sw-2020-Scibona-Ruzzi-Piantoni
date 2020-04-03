package it.polimi.ingsw.network.message;

public enum MessageType {
    //network messages
    START,NICK,CONFIG,WAITPLAYER,NUMBERPLAYER,GAMESTART,DISCONNECTION,
    //gamecontroller messages
    SELECTPOWERSFORMATCH, SELECTPOWER, SETWORKERS, STARTTURN, ENDTURN,
    //roundcontroller messages
    SELECTWORKER, MOVEWORKER, BUILDWORKER, WIN, FIRSTACTION

}
