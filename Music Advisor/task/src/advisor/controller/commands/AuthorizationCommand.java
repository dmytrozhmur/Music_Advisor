package advisor.controller.commands;

import advisor.controller.utils.HttpServerWrapper;
import advisor.model.DataSource;
import advisor.view.View;

import java.util.List;

import com.google.gson.*;

import static advisor.controller.utils.HttpHandler.*;

public class AuthorizationCommand implements Command {

    @Override
    public boolean execute(final String address, final DataSource source) {
        String accessToken;
        HttpServerWrapper serverWrapper = new HttpServerWrapper();
        View.otherInform(String.format(ACCESS_LINK, address, PORT));
        serverWrapper.waitForCode();

        accessToken = getToken(address, serverWrapper);
        if (accessToken == null) return false;
        source.setAccessToken(accessToken);
        source.setRequestedContent(List.of(new String[]{"Success!"}));
        return true;
    }

    private String getToken(String address, HttpServerWrapper serverWrapper) {
        String accessToken;
        if(!serverWrapper.hasCode()) {
            View.otherInform("no code received");
            return null;
        }
        String code = serverWrapper.getCode().replace("code=", "");
        View.otherInform("code received\nmaking http request for access_token...");
        try {
            String response = doPOSTRequest(address, code);
            accessToken = JsonParser.parseString(response).getAsJsonObject()
                    .get("access_token")
                    .getAsString();
        } catch (Exception e) {
            View.otherInform("Access token not available");
            return null;
        }

        return accessToken;
    }
}
