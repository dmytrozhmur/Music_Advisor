package advisor.model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.ArrayList;
import java.util.List;

public class DataSource {
    private final User user = new User();
    private final List<String> requestedContent = new ArrayList<>();
    private final BiMap<String, String> categories = HashBiMap.create();

    public BiMap<String, String> getCategories() {
        return categories;
    }

    public void setCategories(BiMap<String, String> categories) {
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
