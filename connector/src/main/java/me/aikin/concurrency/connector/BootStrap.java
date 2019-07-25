package me.aikin.concurrency.connector;

public class BootStrap {
    public static void main(String[] args) {
        HttpConnector httpConnector = new HttpConnector();
        httpConnector.start();
    }
}
