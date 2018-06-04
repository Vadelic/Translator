package com.translator.dictionary;

import com.translator.dictionary.rest.GoogleTranslate;
import com.translator.dictionary.site.ContextReversoNet;
import com.translator.dictionary.site.GlosbeCom;
import com.translator.dictionary.site.OxfordDictionariesCom;
import com.translator.dictionary.site.VocabolAudioCom;
import com.translator.dictionary.site.wiktionary.WiktionaryOrg;

import java.util.ArrayList;

/**
 * Created by Komyshenets on 11/17/2017.
 */
public class ConfigFactory {

    public static Iterable<PhonemeConfig> getPhoneticConfigs(String word, String wordLang) {

        ArrayList<PhonemeConfig> configs = new ArrayList<>();
        switch (wordLang) {
            case "it":
                configs.add(new VocabolAudioCom(word));
                break;

            case "en":
                configs.add(new OxfordDictionariesCom(word));
                break;
        }

        configs.add(new WiktionaryOrg(word, wordLang));
//        configs.add(new WordReferenceCom(wordOriginal, wordLang));
        return configs;
    }

    public static Iterable<TranslateConfig> getTranslateConfigs(String word, String wordLang, String targetLang) {
        ArrayList<TranslateConfig> configs = new ArrayList<>();
        switch (wordLang) {
            case "it":
                configs.add(new VocabolAudioCom(word, targetLang));
                break;
        }
        configs.add(new GlosbeCom(word, wordLang, targetLang));
        configs.add(new GoogleTranslate(word, wordLang, targetLang));
        return configs;
    }

    public static Iterable<UsagesConfig> getUsageConfigs(String word, String wordLang, String targetLang) {
        ArrayList<UsagesConfig> configs = new ArrayList<>();

        configs.add(new ContextReversoNet(word, wordLang, targetLang));
        configs.add(new GlosbeCom(word, wordLang, targetLang));
        return configs;
    }

}
