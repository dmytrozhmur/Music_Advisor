package advisor.controller.commands;

import advisor.model.DataSource;
import advisor.model.User;
import advisor.view.View;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.google.gson.*;

import static advisor.controller.utils.HttpHandler.doPOSTRequest;

public class AuthorizationCommand implements Command {
    private static String code;

    @Override
    public boolean execute(final String address, DataSource source) {
        String accessToken;
        HttpServer server;
        User currUser = source.getUser();

        View.otherInform(String.format("use this link to request the access code:\n" +
                "%s/authorize?client_id=644f4db58a604873bac7183a410fbff1&" +
                "redirect_uri=http://localhost:8089/&" +
                "response_type=code", address));
        try {
            server = createServer();
        } catch (IOException ioe) {
            View.otherInform("Creating listening server denied");
            return false;
        }

        server.start();
        View.otherInform("waiting for code...");
        for (int i = 0; i < 100; i++) {
            if(gotCode()) break;
            else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return false;
                }
            }
        }

        if(!gotCode()) return false;
        code = code.replace("code=", "");
        View.otherInform("code received\nmaking http request for access_token...");

        server.stop(10);
        try {
            String response = doPOSTRequest(address, code);
            accessToken = JsonParser.parseString(response).getAsJsonObject()
                    .get("access_token")
                    .getAsString();
        } catch (Exception e) {
            View.otherInform("Access token not available");
            return false;
        }

        currUser.setAccessConfirmed(true);
        currUser.setAccessToken(accessToken);
        source.setRequestedContent(List.of(new String[]{"Success!"}));

        return true;
    }

    private boolean gotCode() {
        return code != null && !code.equals("");
    }

    private HttpServer createServer() throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8089), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                code = exchange.getRequestURI().getQuery();

                String body = "";
                if(code.startsWith("code")) {
                    body = "Got the code. Return back to your program.";
                } else {
                    body = "Authorization code not found. Try again.";
                }

                exchange.sendResponseHeaders(200, body.length());
                exchange.getResponseBody().write(body.getBytes());
                exchange.getResponseBody().close();
            }
        });

        return server;
    }

}
