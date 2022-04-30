package advisor;

import advisor.controller.Controller;
import advisor.view.CommandLineView;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String access = null;
        String resource = null;
        int pageSpace = 5;

        for (int i = 0; i < args.length; i++) {
            if("-access".equals(args[i])) access = args[i+1];
            if("-resource".equals(args[i])) resource = args[i+1];
            if("-page".equals(args[i])) pageSpace = Integer.parseInt(args[i+1]);
        }

        access = access == null ? "https://accounts.spotify.com" : access;
        resource = (resource == null ? "https://api.spotify.com" : resource) + "/v1/browse/";

        Controller controller = new Controller(new CommandLineView(pageSpace));
        controller.start(access, resource);
    }
}
