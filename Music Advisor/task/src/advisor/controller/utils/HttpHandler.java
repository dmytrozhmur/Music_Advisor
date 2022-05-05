package advisor.controller.utils;

import advisor.model.DataSource;
import advisor.view.View;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class HttpHandler {
    public static final int PORT = 45678;
    public static final String ACCESS_LINK =
            "use this link to request the access code:\n" +
                    "%s/authorize?client_id=644f4db58a604873bac7183a410fbff1&" +
                    "redirect_uri=http://localhost:%s/&" +
                    "response_type=code";

    public static String doPOSTRequest(final String address, String code) throws Exception {
        String clientID = "644f4db58a604873bac7183a410fbff1";
        String clientSecret = "ccd507db72df4dcda405b2b5a6c3c69a";

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes()))
                .uri(URI.create(String.format("%s/api/token", address)))
                .POST(HttpRequest.BodyPublishers.ofString(String.format(
                        "grant_type=authorization_code&code=%s&redirect_uri=http://localhost:%s/", code, PORT)))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static String doGETRequest(String resource, DataSource source) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + source.getUser().getAccessToken())
                .uri(URI.create(resource))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        HttpResponse<String> response;
        try {
            response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            View.otherInform("request hasn't been sent");
            return null;
        }

        return response.body();
    }

    public static boolean isResponseValid(String response) {
        return response != null && !response.contains("\"error\"");
    }
}
