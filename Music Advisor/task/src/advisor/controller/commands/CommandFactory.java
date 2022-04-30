package advisor.controller.commands;

public enum CommandFactory {
    AUTH {
        @Override
        public Command getCommand(String param) {
//            server.start();
//            System.out.println("https://accounts.spotify.com/" +
//                    "authorize?client_id=644f4db58a604873bac7183a410fbff1&" +
//                    "redirect_uri=http://localhost:8090&" +
//                    "response_type=code");
//            System.out.println("---SUCCESS---");
            return new AuthorizationCommand();
        }
    },

    FEATURED {
        @Override
        public Command getCommand(String param) {
            return new FeaturedCommand();
        }
    },

    NEW {
        @Override
        public Command getCommand(String param) {
            return new NoveltyCommand();
        }
    },

    CATEGORIES {
        @Override
        public Command getCommand(String param) {
            return new CategoriesCommand();
        }
    },

    PLAYLISTS {
        @Override
        public Command getCommand(String param) {
            return new PlaylistsCommand(param);
        }
    },

    EXIT {
        @Override
        public Command getCommand(String param) {
            return null;
        }
    };

    public abstract Command getCommand(String param);
}
