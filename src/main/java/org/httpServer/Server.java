package org.httpServer;

import org.controllers.PostDataHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Server {
    private int serverPort;
    private HttpServer server;
    private Routes routes;

    public Server(int serverPort) {
        this.serverPort = serverPort;
        this.routes = new Routes();
    }

    /**
     * Starts an http server and register a set of routes
     * @throws IOException
     */
    public void startServer() throws IOException {
        try {
            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(this.serverPort), 0);

            routes.registerRoutes(server);

            server.setExecutor(null);
            server.start();

            System.out.println("Server avviato su http://localhost:"+this.serverPort);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server correttamente arrestato.");
        } else {
            System.out.println("Nessun server attivo da fermare.");
        }
    }
}
