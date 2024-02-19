package ru.alexzmeu.java.onlineplaning;


public class ServerApplication {
    public static void main(String[] args) {

        HttpServer httpServer = new HttpServer(Integer.parseInt((String)System.getProperties().getOrDefault("port", "8189")));
        httpServer.start();

    }

}
