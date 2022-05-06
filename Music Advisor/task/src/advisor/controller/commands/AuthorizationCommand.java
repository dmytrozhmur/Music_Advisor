package advisor.controller.commands;

import advisor.model.DataSource;
import advisor.view.View;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

import com.google.gson.*;

import static advisor.controller.utils.HttpHandler.*;

public class AuthorizationCommand implements Command {
    private static String code;

    @Override
    public boolean execute(final String address, final DataSource source) {
        String accessToken;
        HttpServer server;

        View.otherInform(String.format(ACCESS_LINK, address, PORT));
        try {
            server = createServer();
        } catch (IOException ioe) {
            View.otherInform("Creating listening server denied");
            return false;
        }

        server.start();
        View.otherInform("waiting for code...");
        for (int i = 0; i < 10000; i++) {
            if(gotCode()) break;
            else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return false;
                }
            }
        }

        server.stop(10);
        if(!gotCode()) {
            View.otherInform("no code received");
            return false;
        }
        code = code.replace("code=", "");
        View.otherInform("code received\nmaking http request for access_token...");

        try {
            String response = doPOSTRequest(address, code);
            accessToken = JsonParser.parseString(response).getAsJsonObject()
                    .get("access_token")
                    .getAsString();
        } catch (Exception e) {
            View.otherInform("Access token not available");
            return false;
        }

        source.setAccessToken(accessToken);
        source.setRequestedContent(List.of(new String[]{"Success!"}));

        return true;
    }

    private boolean gotCode() {
        return code != null && !code.equals("");
    }

    private HttpServer createServer() throws IOException {
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
    }

}
