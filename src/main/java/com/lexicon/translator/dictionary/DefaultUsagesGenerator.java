package com.lexicon.translator.dictionary;

import com.lexicon.translator.dictionary.parser.ConfigFactory;
import com.lexicon.translator.dictionary.parser.config.UsagesConfig;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Komyshenets on 12/11/2017.
 */
public class DefaultUsagesGenerator implements UsagesGenerator {
    private final Logger log = Logger.getLogger(getClass());
    private String wordOriginal;
    private String wordLang;
    private String targetLang;


    DefaultUsagesGenerator(String wordOriginal, String wordLang, String targetLang) {
        this.wordOriginal = wordOriginal;
        this.wordLang = wordLang;
        this.targetLang = targetLang;
    }

    @Override
    public Map<String, Map<String, String>> getUsages() {
        HashMap<String, Map<String, String>> map = new HashMap<>();
        Iterable<UsagesConfig> configs = ConfigFactory.getUsageConfigs(wordOriginal, wordLang, targetLang);
        for (UsagesConfig config : configs) {
            try {
                Map<String, String> usages = config.getUsages();
                if (!usages.isEmpty()){
                    map.put(config.getClass().getSimpleName(), usages);
                    return map;
                }
            } catch (Exception e) {
                log.warn(String.format("Error while parsing : %s, %s", wordOriginal, config.getClass()), e);
            }
        }
        return map;
    }
}
