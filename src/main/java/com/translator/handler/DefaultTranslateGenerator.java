package com.translator.handler;

import com.translator.dictionary.ConfigFactory;
import com.translator.dictionary.TranslateConfig;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Komyshenets on 12/10/2017.
 */
public class DefaultTranslateGenerator implements TranslateGenerator {
    private final Logger log = Logger.getLogger(getClass());
    private String wordOriginal;
    private String wordLang;
    private String targetLang;


    DefaultTranslateGenerator(String wordOriginal, String wordLang, String targetLang) {
        this.wordOriginal = wordOriginal;
        this.wordLang = wordLang;
        this.targetLang = targetLang;
    }

    @Override
    public Map<String, String> getTranslate() {
        HashMap<String, String> map = new HashMap<>();

        Iterable<TranslateConfig> configs = ConfigFactory.getTranslateConfigs(wordOriginal, wordLang, targetLang);
        for (TranslateConfig config : configs) {
            try {
                String parseResult = config.getTranslate();
                log.debug(String.format("%s - %s (%s)", wordOriginal, parseResult, config.getClass()));
                if (parseResult != null) {
                    map.put(config.getClass().getSimpleName(), parseResult);
                    return map;
                }
            } catch (Exception e) {
                log.warn(String.format("Error while parsing : %s, %s", wordOriginal, config.getClass()), e);
            }
        }
        return map;
    }
}
