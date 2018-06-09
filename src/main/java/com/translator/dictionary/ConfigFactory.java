package com.translator.dictionary;

import com.translator.dictionary.site.*;
import com.translator.dictionary.site.wiktionary.WiktionaryOrg;
import com.translator.exception.DictionaryConfigException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Komyshenets on 11/17/2017.
 */
public class ConfigFactory {

    public static Iterable<PhonemeConfig> getPhoneticConfigs(String word, String wordLang) {
        ArrayList<PhonemeConfig> configs = new ArrayList<>();
        List<? extends Config> phoneme = getConfigs(PhonemeConfig.class, wordLang);


        for (Config config : phoneme) {
            try {
                config.setWord(word);
                config.setLangFrom(wordLang);
                configs.add(config);
            } catch (DictionaryConfigException e) {
                e.printStackTrace();
            }
        }


        return configs;
    }

    private static <T> List<T> getConfigs(Class<T> lass, String wordLang) {
        String simpleName = lass.getSimpleName();
        return null;
    }


    public static Iterable<TranslateConfig> getTranslateConfigs(String word, String wordLang, String targetLang) {
        ArrayList<TranslateConfig> configs = new ArrayList<>();
        return configs;
    }

    public static Iterable<UsagesConfig> getUsageConfigs(String word, String wordLang, String targetLang) {
        ArrayList<UsagesConfig> configs = new ArrayList<>();

        return configs;
    }

}
