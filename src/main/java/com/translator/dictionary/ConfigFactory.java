package com.translator.dictionary;

import com.translator.exception.DictionaryConfigException;
import org.apache.log4j.Logger;
import sun.tools.java.ClassPath;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by Komyshenets on 11/17/2017.
 */
public class ConfigFactory {
    private static final Logger log = Logger.getLogger(ConfigFactory.class);
   private static final String CONFIG_PROPERTY = "configClasses.properties";


    public static <T> List<T> getConfigs(Class<T> lass, String suffix) {
        ArrayList<T> ts = new ArrayList<>();
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = ConfigFactory.class.getResourceAsStream(CONFIG_PROPERTY);
            properties.load(resourceAsStream);
            String simpleName = lass.getSimpleName();

            ts.addAll(getValues(properties, simpleName + "." + suffix));
            ts.addAll(getValues(properties, simpleName));
        } catch (Exception e) {
            log.warn(String.format("Can't load configs property: ", CONFIG_PROPERTY), e);
        }


        return ts;
    }

    private static <T> Collection<? extends T> getValues(Properties property, String propertyKey) {
        ArrayList<T> result = new ArrayList<>();

        String value = property.getProperty(propertyKey);
        if (value != null) {
            for (String aClass : value.split(" ")) {
                try {
                    Object o = Class.forName(aClass.trim()).getDeclaredConstructor().newInstance();
                    result.add((T) o);
                } catch (Throwable e) {
                    log.warn(String.format("Can't create instance %s", aClass.trim()), e);
                }
            }
        }
        return result;
    }
}
