package advisor.controller.commands;

import advisor.model.DataSource;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.common.collect.*;

import java.util.List;

import static advisor.controller.utils.HttpHandler.doGETRequest;
import static advisor.controller.utils.HttpHandler.isResponseValid;

public class CategoriesCommand implements Command {

    @Override
    public boolean execute(final String address, DataSource source) {
        String responseString = doGETRequest(address + "categories", source);
        BiMap<String, String> categoriesMap = HashBiMap.create();

        if(!isResponseValid(responseString)) return false;

        JsonArray categories = JsonParser.parseString(responseString)
                .getAsJsonObject()
                .get("categories")
                .getAsJsonObject()
                .get("items")
                .getAsJsonArray();
        for (JsonElement category: categories) {
            categoriesMap.put(
                    category.getAsJsonObject().get("id").getAsString(),
                    category.getAsJsonObject().get("name").getAsString()
            );
        }

        source.setCategories(categoriesMap);
        source.setRequestedContent(List.copyOf(categoriesMap.values()));

        return true;
    }
}
