package it.polimi.ingsw.model;

public enum Response {

    //Response for game logic
    NEWMOVE,MOVED,BUILD,NOTMOVED,NOTBUILD,NEWBUILD,ASSIGNCONSTRAINT,WIN, BUILDWIN, NOTWIN,NOTBUILDWIN,TOMOVE,BUILDBEFORE,BUILDEDBEFORE,


    //Response for game config

    PLAYERADDED,NICKUSED,GAMESTARTED

}
