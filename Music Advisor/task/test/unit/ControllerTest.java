package unit;

import advisor.controller.Controller;
import advisor.controller.commands.*;
import advisor.view.CommandLineView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ControllerTest {
    private Controller controller = new Controller(new CommandLineView(Integer.MAX_VALUE));

    @Test
    public void makeAuthorizationCommand() {
        Command command = controller.makeCommand("auth");
        Assertions.assertInstanceOf(AuthorizationCommand.class, command);
    }

    @Test
    public void makeCategoriesCommand() {
        Command command = controller.makeCommand("categories");
        Assertions.assertInstanceOf(CategoriesCommand.class, command);
    }

    @Test
    public void makeExitCommand() {
        Command command = controller.makeCommand("exit");
        Assertions.assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    public void makeNoveltyCommand() {
        Command command = controller.makeCommand("new");
        Assertions.assertInstanceOf(NoveltyCommand.class, command);
    }

    @Test
    public void makePlaylistsCommand() {
        Command command = controller.makeCommand("playlists");
        Assertions.assertInstanceOf(PlaylistsCommand.class, command);
    }
}
