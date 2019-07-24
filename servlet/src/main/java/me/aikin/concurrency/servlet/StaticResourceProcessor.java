package me.aikin.concurrency.servlet;

import java.io.IOException;

/**
 * @author aikin
 */
public class StaticResourceProcessor {
    public void process(Request request, Response response) {
        try {
            response.sendStaticResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
