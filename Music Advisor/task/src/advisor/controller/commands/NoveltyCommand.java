package advisor.controller.commands;

import advisor.model.DataSource;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

import static advisor.controller.utils.HttpHandler.doGETRequest;
import static advisor.controller.utils.HttpHandler.isResponseValid;

public class NoveltyCommand implements Command {
    private static final String NOVELTY_ADDRESS = "%snew-releases";

    @Override
    public boolean execute(final String address, final DataSource source) {
        String responseString = doGETRequest(
                String.format(NOVELTY_ADDRESS, address), source);
        List<String> albums = new ArrayList<>();

        if(!isResponseValid(responseString)) return false;

        extractNovelty(responseString, albums);

        source.setRequestedContent(albums);

        return true;
    }

    private void extractNovelty(String from, List<String> to) {
        JsonArray albumsArray = JsonParser.parseString(from)
                .getAsJsonObject()
                .get("albums")
                .getAsJsonObject()
                .get("items")
                .getAsJsonArray();

        for (JsonElement album: albumsArray) {
            StringBuilder albumString = new StringBuilder();
            albumString.append(album.getAsJsonObject().get("name").getAsString()).append("\n");
            JsonArray artists = album.getAsJsonObject()
                    .get("artists")
                    .getAsJsonArray();
            for (int i = 0; i < artists.size(); i++) {
                if(i == 0) albumString.append("[");
                albumString.append(artists.get(i).getAsJsonObject().get("name").getAsString());
                if(i < artists.size()-1) albumString.append(", ");
                if(i == artists.size()-1) albumString.append("]\n");
            }
            albumString
                    .append(album
                            .getAsJsonObject()
                            .get("external_urls")
                            .getAsJsonObject()
                            .get("spotify")
                            .getAsString())
                    .append("\n");
            to.add(albumString.toString());
        }
    }
}
