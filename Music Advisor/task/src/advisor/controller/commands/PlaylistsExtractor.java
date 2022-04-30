package advisor.controller.commands;

import advisor.model.DataSource;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public abstract class PlaylistsExtractor {
    public List<String> extract(String address, String serverResponse) {
        List<String> playlists = new ArrayList<>();

        JsonArray playlistsArray = JsonParser.parseString(serverResponse)
                .getAsJsonObject()
                .get("playlists")
                .getAsJsonObject()
                .get("items")
                .getAsJsonArray();
        for (JsonElement playlist: playlistsArray) {
            playlists.add(
                    playlist.getAsJsonObject()
                            .get("name")
                            .getAsString() + "\n"
                            + playlist.getAsJsonObject()
                            .get("external_urls")
                            .getAsJsonObject()
                            .get("spotify")
                            .getAsString() + "\n"
            );
        }

        return playlists;
    }
}
