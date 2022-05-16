package advisor.controller.commands;

import advisor.model.DataSource;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static advisor.controller.utils.HttpHandler.doGETRequest;
import static advisor.controller.utils.HttpHandler.isResponseValid;

public class CategoriesCommand implements Command {
    private static final String CATEGORIES_ADDRESS = "%scategories";

    @Override
    public boolean execute(final String address, final DataSource source) {
        String responseString = doGETRequest(
                String.format(CATEGORIES_ADDRESS, address), source);
        Map<String, String> categoriesMap = new LinkedHashMap<>();

        if(!isResponseValid(responseString)) return false;

        extractCategories(responseString, categoriesMap);

        source.setCategories(categoriesMap);
        source.setRequestedContent(List.copyOf(categoriesMap.values()));

        return true;
    }

    private void extractCategories(String from, Map<String, String> to) {
        JsonArray categories = JsonParser.parseString(from)
                .getAsJsonObject()
                .get("categories")
                .getAsJsonObject()
                .get("items")
                .getAsJsonArray();
        for (JsonElement category: categories) {
            to.put(
                    category.getAsJsonObject().get("id").getAsString(),
                    category.getAsJsonObject().get("name").getAsString()
            );
        }
    }
}
