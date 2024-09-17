package JiraXray.util;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by NairaMasgo on 20/09/2023.
 * Role: QE Engineer
 */
public class PropertiesReader {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("automation.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        String value = System.getProperty(key);
        if (value == null || value.isEmpty())
            value = properties.getProperty(key);
        return value;
    }

    public static boolean getBooleanProperty(String property) {
        return Boolean.parseBoolean(getProperty(property));
    }
}

