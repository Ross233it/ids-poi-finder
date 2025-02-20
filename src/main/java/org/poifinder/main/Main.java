package org.poifinder.main;

import org.poifinder.httpServer.DbConnectionManager;

public class Main {
    public static void main(String[] args) {

//        /** Avvio del server http */
//        Server server = new Server(8080);
//        try{
//            server.startServer();
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        /**  DB check  */
        DbConnectionManager dbConnectionManager = DbConnectionManager.getInstance();
        if(dbConnectionManager.dbCheckConnection()){
            System.out.println("Connesso correttamente al DB");
        }
        else{
            System.out.println("Impossibile connettere il DB");
            System.out.println("Applicazione terminata");
            System.exit(1);
        }
    }
}