package com.translator.generator;

import com.translator.dictionary.ConfigFactory;
import com.translator.dictionary.TranslateConfig;
import com.translator.model.LanguagePack;
import com.translator.model.Word;
import org.apache.log4j.Logger;

/**
 * Created by Komyshenets on 12/10/2017.
 */
public class DefaultTranslateGenerator {
    private final Logger log = Logger.getLogger(getClass());
    private Word word;
    private LanguagePack languagePack;


    public DefaultTranslateGenerator(Word wordOriginal, LanguagePack languagePack) {
        this.word = wordOriginal;
        this.languagePack = languagePack;
    }


    public boolean getTranslate() {
        Iterable<TranslateConfig> configs = ConfigFactory.getConfigs(TranslateConfig.class, word.getLanguage().getCode());
        for (TranslateConfig config : configs) {
            try {
                config.setWord(word.getWord());
                config.setLangFrom(word.getLanguage().getCode());
                config.setLangTo(languagePack.getLanguage().getCode());
                String parseResult = config.getTranslate();

                log.debug(String.format("Parse and result: %s (%s)", config.getAddress(), parseResult));
                if (parseResult != null) {
                    languagePack.setTranslate(parseResult);
                    languagePack.setResource(config.getAddress());
                    return true;
                }
            } catch (Exception e) {
                log.warn(String.format("Error while parsing translate: %s", config.getAddress()), e);
            }
        }

        return false;
    }
}
