package me.aikin.concurrency.socket;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

/**
 * @author aikin
 */
public class Response {
    private final OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void sendStaticResponse(String staticResourcePath) throws IOException {
        String msg = "HTTP/1.1 404 File Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n";
        byte[] bytes = new byte[1024];
        URL resource = getClass().getClassLoader().getResource(staticResourcePath);
        File file = new File(resource.getPath());
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            this.outputStream.write(msg.getBytes());
            int ch = fileInputStream.read(bytes, 0, 1024);

            // 一个中文字符是 3， 英文字符是 1
            while (ch != -1) {
                this.outputStream.write(bytes, 0, ch);
                ch = fileInputStream.read(bytes, 0, 1024);
            }
        } else {
            String errorMessage = msg + "<h1> File Not Found!</h1>";
            this.outputStream.write(errorMessage.getBytes());
        }
    }

}