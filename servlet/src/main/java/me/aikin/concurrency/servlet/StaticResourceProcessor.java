package me.aikin.concurrency.servlet;

import java.io.IOException;

/**
 * @author aikin
 */
public class StaticResourceProcessor {
    public void process(RequestFacade request, ResponseFacade response) {
        try {
            response.sendStaticResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
