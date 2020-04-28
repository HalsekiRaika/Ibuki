package Ibuki.API;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class IbukiTokenReader {
    private static final String PATH = "/app.properties";
    private static String BUF;
    public String get() throws IOException {
        try(InputStream stream = getClass().getResourceAsStream(PATH)) {
            Properties properties = new Properties();
            properties.load(stream);
            BUF = properties.getProperty("token");
        } catch (Exception e) {}
        return BUF;
    }
}
