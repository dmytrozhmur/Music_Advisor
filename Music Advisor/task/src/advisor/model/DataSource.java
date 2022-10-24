package advisor.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataSource {
    private final List<String> requestedContent = new ArrayList<>();
    private final Map<String, String> categories = new LinkedHashMap<>();
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Map<String, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, String> categories) {
        this.categories.clear();
        this.categories.putAll(categories);
    }

    public List<String> getRequestedContent() {
        return requestedContent;
    }

    public void setRequestedContent(List<String> requestedContent) {
        this.requestedContent.clear();
        this.requestedContent.addAll(requestedContent);
    }
}
