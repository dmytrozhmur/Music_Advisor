package advisor.model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataSource {
    private final User user = new User();
    private final List<String> requestedContent = new ArrayList<>();
    private final Map<String, String> categories = new LinkedHashMap<>();

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

    public User getUser() {
        return user;
    }
}
