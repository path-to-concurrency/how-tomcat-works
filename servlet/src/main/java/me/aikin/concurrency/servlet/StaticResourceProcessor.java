package me.aikin.concurrency.servlet;

import java.io.IOException;

/**
 * @author aikin
 */
public class StaticResourceProcessor {
    public void process(Request request, Response response) {
        try {
            response.sendStaticResponse(Constants.STATIC_RESOURCE.concat(request.getUri().get()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
