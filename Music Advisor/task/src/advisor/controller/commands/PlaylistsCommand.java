package advisor.controller.commands;

import advisor.model.DataSource;
import advisor.view.View;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Map;

import static advisor.controller.utils.HttpHandler.doGETRequest;
import static advisor.controller.utils.HttpHandler.isResponseValid;

public class PlaylistsCommand extends PlaylistsExtractor implements Command {
    private static final String PLAYLISTS_ADDRESS = "%scategories/%s/playlists";
    private final String category;

    public PlaylistsCommand(String category) {
        this.category = category;
    }

    @Override
    public boolean execute(final String address, final DataSource source) {
        BiMap<String, String> categoriesMap = HashBiMap.create();
        Map<String, String> sourceCategories = source.getCategories();

        if(sourceCategories.isEmpty()) new CategoriesCommand().execute(address, source);

        categoriesMap.putAll(sourceCategories);
        categoriesMap = categoriesMap.inverse();

        String categoryId = categoriesMap.get(category);
        String response = doGETRequest(
                String.format(PLAYLISTS_ADDRESS, address, categoryId), source);

        if(!isResponseValid(response)) {
            View.otherInform("Specified id doesn't exist");
            return false;
        }

        source.setRequestedContent(super.extract(response));
        return true;
    }
}
