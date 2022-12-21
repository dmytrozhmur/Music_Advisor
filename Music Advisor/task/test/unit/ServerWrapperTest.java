package unit;

import advisor.server.HttpServerWrapper;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ServerWrapperTest {
    private HttpServerWrapper serverWrapper;

    @Test
    public void createServerAndCheckAddress() {
        serverWrapper = new HttpServerWrapper();
        HttpServer server = serverWrapper.getServer();
        Assertions.assertEquals(8089, server.getAddress().getPort());
    }
}
