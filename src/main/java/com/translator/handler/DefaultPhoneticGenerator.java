package com.translator.handler;

import com.translator.dictionary.ConfigFactory;
import com.translator.dictionary.PhonemeConfig;
import com.translator.model.Language;
import com.translator.model.Word;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Komyshenets on 12/1/2017.
 */
public class DefaultPhoneticGenerator  {
    private final Logger log = Logger.getLogger(getClass());
    private Word word;
    private Language language;

    public DefaultPhoneticGenerator(Word wordOriginal, Language wordLang) {
        this.word = wordOriginal;
        this.language = wordLang;
    }


    public String getPhonetic() {
        Iterable<PhonemeConfig> configs = ConfigFactory.getPhoneticConfigs(word.getWord(), language.getCode());
        for (PhonemeConfig config : configs) {
            try {
                String phoneme = config.getPhoneme();
                log.debug(String.format("Parse and result: %s (%s)", config.getAddress(), phoneme));
                if (phoneme != null) {
                    return phoneme;
                }
            } catch (Exception e) {
                log.warn(String.format("Error while parsing phoneme: %s", config.getAddress()), e);
            }
        }
        return null;
    }
}
