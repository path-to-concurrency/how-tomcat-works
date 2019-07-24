package me.aikin.concurrency.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Locale;

/**
 * @author aikin
 */
public class Response implements ServletResponse {
    private final OutputStream outputStream;
    private final Request request;

    public Response(OutputStream outputStream, Request request) {
        this.outputStream = outputStream;
        this.request = request;
    }

    public void sendStaticResponse() throws IOException {
        String msg = "HTTP/1.1 404 File Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n";
        byte[] bytes = new byte[1024];
        URL resource = getClass().getClassLoader().getResource(Constants.STATIC_RESOURCE.concat(request.getUri().get()));
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

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(outputStream, true);
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
