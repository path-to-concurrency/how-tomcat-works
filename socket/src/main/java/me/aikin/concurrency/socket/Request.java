package me.aikin.concurrency.socket;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author aikin
 */
public class Request {
    private final InputStream inputStream;

    private Optional<String> uri;


    public Optional<String> getUri() {
        return uri;
    }


    public Request(InputStream inputStream) throws IOException {
        this.inputStream = inputStream;
        this.uri = parse();
    }

    private Optional<String> parse() throws IOException {
        String requestString = parseRequestString();
        return parseUri(requestString);
    }

    private String parseRequestString() {
        int i;
        StringBuilder request = new StringBuilder(2048);
        byte[] buffer = new byte[2048];
        try {
            i = this.inputStream.read(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }

        return request.toString();
    }

    private Optional<String> parseUri(String requestString) {
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
}