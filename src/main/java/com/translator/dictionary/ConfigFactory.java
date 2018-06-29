package com.translator.dictionary;

import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created by Komyshenets on 11/17/2017.
 */
public class ConfigFactory {
    private static final Logger log = Logger.getLogger(ConfigFactory.class);
    private static final String CONFIG_PROPERTY = "configClasses.properties";


    public static <T> List<T> getConfigs(Class<T> tClass, String suffix) {
        ArrayList<T> ts = new ArrayList<>();
        try {
            Properties properties = new Properties();
            properties.load(ConfigFactory.class.getResourceAsStream(CONFIG_PROPERTY));
            String simpleName = tClass.getSimpleName();

            ts.addAll(getValues(properties, simpleName + "." + suffix));
            ts.addAll(getValues(properties, simpleName));
            return ts;

        } catch (Exception e) {
            log.warn(String.format("Can't load configs property: %s", CONFIG_PROPERTY), e);
            return new ArrayList<>();
        }
    }

    private static <T> Collection<? extends T> getValues(Properties property, String propertyKey) {
        ArrayList<T> result = new ArrayList<>();

        String value = property.getProperty(propertyKey);
        if (value != null) {
            for (String aClass : value.split(" ")) {
                try {
                    Object newInstanceConfig = Class.forName(aClass.trim()).getDeclaredConstructor().newInstance();
                    result.add((T) newInstanceConfig);
                } catch (Throwable e) {
                    log.warn(String.format("Can't createAndFill instance %s", aClass.trim()), e);
                }
            }
        }
        return result;
    }
}