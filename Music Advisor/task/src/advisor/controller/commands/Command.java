package advisor.controller.commands;

import advisor.model.DataSource;

public interface Command {
    boolean execute(final String address, final DataSource source);
}
