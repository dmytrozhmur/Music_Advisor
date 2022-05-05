package advisor.controller.commands;

import advisor.model.DataSource;
import advisor.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;

public interface Command {
    boolean execute(final String address, final DataSource source);
}
