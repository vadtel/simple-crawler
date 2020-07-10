package org.vadtel.crawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesLoader {
    private static Properties prop;
    static{
        InputStream is = null;
        try {
            prop = new Properties();
            is = PropertiesLoader.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final String TERMS = prop.getProperty("terms");
    public static final String START_URL= prop.getProperty("startURL");
    public static final Integer MAX_DEPTH= Integer.parseInt(prop.getProperty("maxDepth"));
    public static final Integer MAX_URL= Integer.parseInt(prop.getProperty("maxURL"));

    public static String getPropertyValue(String key){
        return prop.getProperty(key);
    }

}
