package utilities;

import loggers.Log4jLogger;

import java.io.*;
import java.util.Properties;

public class TestProperties {
    public String readProperty(String propertyName){

        try (InputStream input = getClass().getResourceAsStream("/test.properties")) {

            Properties properties = new Properties();

            properties.load(input);

            return properties.getProperty(propertyName);

        } catch (IOException ex) {
            Log4jLogger.info("Got Error When Reading Property: " + propertyName + "Error: " + ex.getMessage());
        }
        return  "./screenshots"; // default path in case of error
    }
}
