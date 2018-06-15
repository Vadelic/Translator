package com.translator.generator;

import com.translator.dictionary.ConfigFactory;
import com.translator.dictionary.PhonemeConfig;
import com.translator.exception.DictionaryConfigException;
import com.translator.model.Word;
import org.apache.log4j.Logger;

/**
 * Created by Komyshenets on 12/1/2017.
 */
public class DefaultPhoneticGenerator {
    private final Logger log = Logger.getLogger(getClass());
    private Word word;

    public DefaultPhoneticGenerator(Word wordOriginal) {
        this.word = wordOriginal;
    }


    public String getPhonetic() {
        Iterable<PhonemeConfig> configs = ConfigFactory.getConfigs(PhonemeConfig.class, word.getLanguage().getCode());
        for (PhonemeConfig config : configs) {
            try {
                config.setWord(word.getWord());
                config.setLangFrom(word.getLanguage().getCode());

                String phoneme = config.getPhoneme();
                log.debug(String.format("Parse and result: %s (%s)", config.getAddress(), phoneme));
                if (phoneme != null) {
                    return phoneme;
                }
            } catch (DictionaryConfigException e) {
                log.warn(String.format("Error while parsing phoneme: %s", config.getAddress()), e);
            }
        }
        return null;
    }
}
