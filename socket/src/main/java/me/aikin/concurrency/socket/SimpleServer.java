package me.aikin.concurrency.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class SimpleServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        Optional<ServerSocket> serverSocketOptional = createServerSocket();
        boolean loop = true;
        while (loop) {
            Socket socket;
            OutputStream outputStream;
            try {
                socket = serverSocketOptional.orElseThrow(() -> new RuntimeException("create socket error")).accept();
                outputStream = socket.getOutputStream();

                String msg = createResponse();
                outputStream.write(msg.getBytes());

                Thread.sleep(50);

                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

    }

    private static String createResponse() {
        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html>\r\n" +
                "<head>\r\n" +
                "<title>HTTP Response Example</title>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                "Welcome to Brainy Software\r\n" +
                "</body>\r\n" +
                "</html>";
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
