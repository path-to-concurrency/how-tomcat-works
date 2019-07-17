package me.aikin.concurrency.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class HttpServer {

    private static final int PORT = 8080;
    private static final String STATIC_RESOURCE = "static-resource";

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.await();
    }

    public void await() {
        Optional<ServerSocket> serverSocketOptional = createServerSocket();

        try {
            Socket socket = serverSocketOptional.orElseThrow(() -> new RuntimeException("create socket error")).accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            Request request = new Request(inputStream);
            Optional<String> uriOptional = request.getUri();

            if (uriOptional.isPresent()) {
                Response response = new Response(outputStream);
                response.sendStaticResponse(STATIC_RESOURCE.concat(uriOptional.get()));
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Optional<ServerSocket> createServerSocket() {
        try {
            ServerSocket socket = new ServerSocket(PORT, 1, InetAddress.getByName("127.0.0.1"));
            return Optional.of(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
