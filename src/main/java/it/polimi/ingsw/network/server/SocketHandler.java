package it.polimi.ingsw.network.server;


import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that listen for new clients connections, and create a ClientHandler for each of them
 * @author Alessandro Ruzzi
 * @version 1.0
 * @since 2020/06/19
 */

public class SocketHandler extends Thread implements Closeable {
    private ServerSocket serverSocket;
    int port;
    private final Server server;
    private boolean isActive;

    /**
     * Public constructor for the SocketHandler Class that initialize the parameters of the class
     * @param port Port in which it will listen for new connections
     * @param server The Server to which refer
     * @throws IOException IOException
     */

    public SocketHandler(int port,Server server)throws IOException{
        this.port = port;
        this.server = server;
        this.isActive = true;
        startSocketHandler();
    }

    /**
     * Thread that listen for new connection requests
     */

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

    /**
     * Method that create new Server Socket and start a thread that listen for new connections request
     * @throws IOException IOException, socket get closed
     */

    public void startSocketHandler()throws IOException {

        serverSocket = new ServerSocket(port);
        Thread clientSocketThread = new Thread(this);
        clientSocketThread.start();

}

    /**
     * Method that stop the Socket Handler when the Server is stopped
     */

    @Override
    public void close(){
        try{
            isActive = false;
            serverSocket.close();
            Server.LOGGER.info("SocketHandler --> Stopped");
        }catch (IOException e){
            Server.LOGGER.severe(e.getMessage());
        }
    }
}


