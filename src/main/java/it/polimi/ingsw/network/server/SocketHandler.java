package it.polimi.ingsw.network.server;

import it.polimi.ingsw.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//socket that wait for all the connections

public class SocketHandler implements Runnable {
    private ServerSocket serverSocket;
    int port;
    private Server server;

    public SocketHandler(int port,Server server)throws IOException{
        this.port = port;
        this.server = server;
        startSocketHandler();
    }

    @Override
    public void run() {
        Logger.info("Server is listening on port: "+ port);
        ExecutorService executor = Executors.newCachedThreadPool();

            while(!Thread.currentThread().isInterrupted()) {
                try {
                    Socket connection = serverSocket.accept();
                    executor.submit(new ClientHandler(server,connection));

                } catch (SocketException s) {
                    Logger.info("socket is closed");
                } catch (IOException e) {
                    Logger.info("problem woth connection");
                }
            }

        }

    public void startSocketHandler()throws IOException {

        serverSocket = new ServerSocket(port);
        Thread clientSocketThread = new Thread(this);
        clientSocketThread.start();

}




}


