package advisor.controller.utils;

import advisor.view.View;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static advisor.controller.utils.HttpHandler.PORT;

public class HttpServerWrapper {
    private HttpServer server;
    private String code;

    public HttpServerWrapper() {
        this.server = createHttpServer();
    }

    public String getCode() {
        return code;
    }

    public void waitForCode() {
        server.start();
        View.otherInform("waiting for code...");
        for (int i = 0; i < 10000; i++) {
            if(hasCode()) break;
            else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        server.stop(10);
    }

    public boolean hasCode() {
        return code != null && !code.equals("");
    }

    private HttpServer createHttpServer() {
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(PORT), 0);
            server.createContext("/", exchange -> {
                while (code == null) code = exchange.getRequestURI().getQuery();
                String body;
                if(code.startsWith("code")) {
                    body = "Got the code. Return back to your program.";
                } else {
                    body = "Authorization code not found. Try again.";
                }
                exchange.sendResponseHeaders(200, body.length());
                exchange.getResponseBody().write(body.getBytes());
                exchange.getResponseBody().close();
            });
            return server;
        } catch (IOException e) {
            View.otherInform("Error during http server creation");
            throw new RuntimeException(e);
        }
    }
}
