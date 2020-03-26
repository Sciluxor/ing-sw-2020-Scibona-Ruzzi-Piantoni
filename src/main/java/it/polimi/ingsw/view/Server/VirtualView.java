package it.polimi.ingsw.view.Server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.utils.Observable;
import it.polimi.ingsw.utils.Observer;


public class VirtualView extends Observable<Message> implements Observer<Game> {
    private Player player;
    private ClientHandler connection;

    public VirtualView(ClientHandler connection,String nickName) {
        this.connection = connection;
        this.player = new Player(nickName);

    }

    public void processMessageReceived(Message message){

       notify(message);

   }

   public void onUpdatedInstance(Game instance){
       switch (instance.getGameStatus()) {

       }


   }

   public Player getPlayer(){
       return player;

   }




    @Override
    public void update(Game instance) {

       onUpdatedInstance(instance);

    }
}
