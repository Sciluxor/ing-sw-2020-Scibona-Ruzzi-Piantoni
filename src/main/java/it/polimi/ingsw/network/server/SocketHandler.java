package it.polimi.ingsw.network.server;


import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
                    connection.setTcpNoDelay(true);

                    Server.LOGGER.info("ServerSocket --> accepted, sock:" + connection.getInetAddress());
                    executor.submit(new ClientHandler(server,connection));

                } catch (IOException s) {
                    Server.LOGGER.severe(s.getMessage());
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
            Server.LOGGER.info("ServerSocket --> Stopped");
        }catch (IOException e){
            Server.LOGGER.severe(e.getMessage());
        }
    }
}


