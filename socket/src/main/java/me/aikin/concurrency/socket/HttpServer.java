package me.aikin.concurrency.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
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

            Optional<String> uriOptional = parseRequest(inputStream);

            if (uriOptional.isPresent()) {
                byte[] bytes = new byte[1024];
                URL resource = getClass().getClassLoader().getResource("static-resource" + uriOptional.get());
                File file = new File(resource.getPath());
                if (file.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(file);

                    String msg = "HTTP/1.1 404 File Not Found\r\n" +
                            "Content-Type: text/html\r\n" +
                            "\r\n";

                    outputStream.write(msg.getBytes());
                    int ch = fileInputStream.read(bytes, 0, 1024);
                    while (ch != -1) {
                        outputStream.write(bytes, 0, ch);
                        ch = fileInputStream.read(bytes, 0, 1024);
                    }
                } else {
                    String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Content-Length: 23\r\n" +
                            "\r\n" +
                            "<h1> File Not Found!</h1>";
                    outputStream.write(errorMessage.getBytes());
                }
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Optional<String> parseRequest(InputStream inputStream) throws IOException {
        int i;
        StringBuilder request = new StringBuilder(2048);
        byte[] buffer = new byte[2048];
        try {
            i = inputStream.read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }

        String requestString = request.toString();
        System.out.println(requestString);

        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                return Optional.of(requestString.substring(index1 + 1, index2));
            }
        }
        return Optional.empty();
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
