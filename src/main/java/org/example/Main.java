package org.example;

import org.httpServer.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(8080);
        try{
            server.startServer();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}