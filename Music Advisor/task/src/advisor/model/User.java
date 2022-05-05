package advisor.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User {
    private boolean accessConfirmed;
    private String accessToken;

    public boolean isAccessConfirmed() {
        return accessConfirmed;
    }

    public void setAccessConfirmed(boolean accessConfirmed) {
        this.accessConfirmed = accessConfirmed;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
