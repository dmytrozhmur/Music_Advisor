package advisor.controller.commands;

import advisor.model.DataSource;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

import static advisor.controller.utils.ForTests.*;
import static advisor.controller.utils.HttpHandler.doGETRequest;
import static advisor.controller.utils.HttpHandler.isResponseValid;

public class NoveltyCommand implements Command {

    @Override
    public boolean execute(final String address, DataSource source) {
        String responseString = doGETRequest(address + "new-releases", source);
        List<String> albums = new ArrayList<>();

        if(!isResponseValid(responseString)) return false;

        JsonArray albumsArray = JsonParser.parseString(responseString)
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

            albumString.append(album.getAsJsonObject().get("href").getAsString()).append("\n");
            albums.add(albumString.toString());
        }

        source.setRequestedContent(albums);

        return true;
    }
}
