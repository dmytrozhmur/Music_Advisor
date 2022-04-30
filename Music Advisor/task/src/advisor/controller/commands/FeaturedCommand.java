package advisor.controller.commands;

import advisor.model.DataSource;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.*;

import static advisor.controller.utils.ForTests.*;
import static advisor.controller.utils.HttpHandler.doGETRequest;
import static advisor.controller.utils.HttpHandler.isResponseValid;

public class FeaturedCommand extends PlaylistsExtractor implements Command {

    @Override
    public boolean execute(final String address, DataSource source) {
        String response = doGETRequest(address + "featured-playlists", source);

        if(!isResponseValid(response)) return false;

        source.setRequestedContent(super.extract(address, response));
        return true;
    }

}
