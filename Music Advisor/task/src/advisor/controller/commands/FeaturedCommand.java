package advisor.controller.commands;

import advisor.model.DataSource;

import static advisor.controller.utils.HttpHandler.doGETRequest;
import static advisor.controller.utils.HttpHandler.isResponseValid;

public class FeaturedCommand extends PlaylistsExtractor implements Command {
    private static final String FEATURED_PLAYLISTS_ADDRESS = "%sfeatured-playlists";

    @Override
    public boolean execute(final String address, final DataSource source) {
        String response = doGETRequest(
                String.format(FEATURED_PLAYLISTS_ADDRESS, address), source);

        if(!isResponseValid(response)) return false;

        source.setRequestedContent(super.extract(response));
        return true;
    }

}
