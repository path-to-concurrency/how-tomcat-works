package me.aikin.concurrency.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class HttpServer {
    private static final int PORT = 8080;

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
            Response response = new Response(outputStream, request);

            if (uriOptional.isPresent()) {
                if (uriOptional.get().startsWith("/servlet/")) {
                    ServletProcessor servletProcessor = new ServletProcessor();
                    servletProcessor.process(request, response);
                } else {
                    StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
                    staticResourceProcessor.process(new RequestFacade(request), new ResponseFacade(response));
                }
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
