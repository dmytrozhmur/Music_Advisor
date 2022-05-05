package advisor.controller.commands;

public enum CommandFactory {
    AUTH {
        @Override
        public Command getCommand(String param) {
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
