package advisor.controller.commands;

import advisor.model.DataSource;

public class ExitCommand implements Command {

    @Override
    public boolean execute(String address, DataSource source) {
        System.exit(0);
        return true;
    }
}
