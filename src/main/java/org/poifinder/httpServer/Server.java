package org.poifinder.httpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import org.poifinder.httpServer.http.HttpRequestHandler;

/**
 * Questa classe rappresenta ed inizializza un server Http
 */
public class Server {

    private int serverPort;

    private HttpServer server;

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * Avvia il server http e registra tutte le rotte configurate nel router.
     * @throws IOException
     */
    public void startServer() throws IOException {
        try {
            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(this.serverPort), 0);

            server.createContext("/", new HttpRequestHandler<>());

            server.setExecutor(null);
            server.start();

            System.out.println("Server avviato su http://localhost:"+this.serverPort);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Arresta il server se avviato.
     */
    public void stopServer() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server correttamente arrestato.");
        } else {
            System.out.println("Nessun server attivo da fermare.");
        }
    }
}
