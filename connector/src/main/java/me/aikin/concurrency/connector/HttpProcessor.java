package me.aikin.concurrency.connector;

import java.net.Socket; /**
 * @author aikin
 */
public class HttpProcessor {
    private final HttpConnector httpConnector;

    public HttpProcessor(HttpConnector httpConnector) {
        this.httpConnector = httpConnector;
    }

    public void process(Socket socket) {
    }
}
