package me.aikin.concurrency.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author aikin
 */
public class HttpProcessor {
    private final HttpConnector httpConnector;
    private HttpRequestLine requestLine = new HttpRequestLine();

    public HttpProcessor(HttpConnector httpConnector) {
        this.httpConnector = httpConnector;
    }

    public void process(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            SocketInputStream input = new SocketInputStream(inputStream, 2048);
            OutputStream output = socket.getOutputStream();

            HttpRequest request = new HttpRequest(input);
            HttpResponse response = new HttpResponse(output);

            response.setRequest(request);
            response.setHeader("Server", "Pyrmont Servlet Container");

            parseRequest(input, output);
            parseHeaders(input);

            if (request.getRequestURI().startsWith("/servlet/")) {
                ServletProcessor processor = new ServletProcessor();
                processor.process(request, response);
            } else {
                StaticResourceProcessor processor = new StaticResourceProcessor();
                processor.process(request, response);
            }

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseHeaders(SocketInputStream input) throws IOException {

    }

    private void parseRequest(SocketInputStream input, OutputStream output) throws IOException {
        input.readRequestLine(requestLine);
    }
}
