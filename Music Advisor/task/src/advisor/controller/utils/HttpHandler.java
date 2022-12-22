package advisor.controller.utils;

import advisor.model.DataSource;
import advisor.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Properties;

public class HttpHandler {
    public static final int PORT = 8089;
    public static final String ACCESS_LINK =
            "use this link to request the access code:\n" +
                    "%s/authorize?client_id=644f4db58a604873bac7183a410fbff1&" +
                    "redirect_uri=http://localhost:%s/&" +
                    "response_type=code";
    private static final String CREDENTIALS_ADDRESS =
            "Music Advisor/task/src/advisor/controller/utils/credentials.properties";
    private static FileReader reader;
    private static Properties creds = new Properties();

    static {
        try {
            View.otherInform(CREDENTIALS_ADDRESS);
            reader = new FileReader(CREDENTIALS_ADDRESS);
            creds.load(reader);
        } catch (IOException ioe) {
            View.otherInform("Cannot load the credentials file");
        }
    }

    public static String doPOSTRequest(final String address, String code) throws IOException, InterruptedException {
        String clientID = creds.getProperty("clientID");
        String clientSecret = creds.getProperty("clientSecret");

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
                .header("Authorization", "Bearer " + source.getAccessToken())
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
