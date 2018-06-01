package com.lexicon.translator.dictionary.parser;

import com.lexicon.translator.dictionary.parser.config.PhonemeConfig;
import com.lexicon.translator.dictionary.parser.config.TranslateConfig;
import com.lexicon.translator.dictionary.parser.config.UsagesConfig;
import com.lexicon.translator.dictionary.parser.config.rest.GoogleTranslate;
import com.lexicon.translator.dictionary.parser.config.site.ContextReversoNet;
import com.lexicon.translator.dictionary.parser.config.site.GlosbeCom;
import com.lexicon.translator.dictionary.parser.config.site.OxfordDictionariesCom;
import com.lexicon.translator.dictionary.parser.config.site.VocabolAudioCom;
import com.lexicon.translator.dictionary.parser.config.site.wiktionary.WiktionaryOrg;

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
