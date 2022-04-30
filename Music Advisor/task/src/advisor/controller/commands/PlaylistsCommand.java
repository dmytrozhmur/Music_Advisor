package advisor.controller.commands;

import advisor.model.DataSource;
import advisor.view.View;
import com.google.common.collect.BiMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.*;

import static advisor.controller.utils.HttpHandler.doGETRequest;
import static advisor.controller.utils.HttpHandler.isResponseValid;

public class PlaylistsCommand extends PlaylistsExtractor implements Command {
    private String category;

    public PlaylistsCommand(String category) {
        this.category = category;
    }

    @Override
    public boolean execute(final String address, DataSource source) {
        BiMap<String, String> categoriesMap = source.getCategories();

        if(categoriesMap.isEmpty()) new CategoriesCommand().execute(address, source);

        categoriesMap = categoriesMap.inverse();

        String categoryId = categoriesMap.get(category);
        String response =
                doGETRequest(address + "categories/" + categoryId + "/playlists", source);

        if(!isResponseValid(response)) {
            View.otherInform("Specified id doesn't exist");
            return false;
        }

        source.setRequestedContent(super.extract(address, response));
        return true;
    }
}
