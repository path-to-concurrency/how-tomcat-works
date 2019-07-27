package me.aikin.concurrency.connector;

/**
 * @author aikin
 */
public class StaticResourceProcessor {
    public void process(HttpRequest request, HttpResponse response) {
        response.sendStaticResponse();
    }
}
