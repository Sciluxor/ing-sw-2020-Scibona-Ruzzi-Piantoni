package it.polimi.ingsw.network.server;

import it.polimi.ingsw.utils.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//socket that wait for all the connections

public class SocketHandler extends Thread implements Closeable {
    private ServerSocket serverSocket;
    int port;
    private Server server;
    private boolean isActive;

    public SocketHandler(int port,Server server)throws IOException{
        this.port = port;
        this.server = server;
        this.isActive = true;
        startSocketHandler();
    }

    @Override
    public void run() {
        ExecutorService executor = Executors.newCachedThreadPool();

            while(isActive) {
                try {
                    Socket connection = serverSocket.accept();
                    executor.submit(new ClientHandler(server,connection));

                } catch (SocketException s) {
                    Logger.info("socket is closed");
                } catch (IOException e) {
                    Logger.info("problem with connection");
                }
            }

        }

    public void startSocketHandler()throws IOException {

        serverSocket = new ServerSocket(port);
        Thread clientSocketThread = new Thread(this);
        clientSocketThread.start();

}


    @Override
    public void close(){
        try{
            isActive = false;
            serverSocket.close();
            Logger.info("ServerSocket --> Stopped");
        }catch (IOException e){
            Logger.info("Error in closing Server Socket");
        }
    }
}


