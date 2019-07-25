package me.aikin.concurrency.connector;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

/**
 * @author aikin
 */
public class HttpConnector implements Runnable {
    private static final int PORT = 8080;
    private boolean stopped = false;


    @Override
    public void run() {
        while (!stopped) {
            Optional<ServerSocket> serverSocketOptional = createServerSocket();
            Socket socket;
            try {
                socket = serverSocketOptional.orElseThrow(() -> new RuntimeException("create socket error")).accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            HttpProcessor httpProcessor = new HttpProcessor(this);
            httpProcessor.process(socket);
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    private static Optional<ServerSocket> createServerSocket() {
        try {
            ServerSocket socket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));
            return Optional.of(socket);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        return Optional.empty();
    }
}
