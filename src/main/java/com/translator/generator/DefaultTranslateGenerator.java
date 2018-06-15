package com.translator.generator;

import com.translator.dictionary.ConfigFactory;
import com.translator.dictionary.TranslateConfig;
import com.translator.model.Language;
import com.translator.model.Translate;
import com.translator.model.Word;
import org.apache.log4j.Logger;

/**
 * Created by Komyshenets on 12/10/2017.
 */
public class DefaultTranslateGenerator {
    private final Logger log = Logger.getLogger(getClass());
    private Word word;
    private Language languageTo;


    public DefaultTranslateGenerator(Word wordOriginal, Language languageTo) {
        this.word = wordOriginal;
        this.languageTo = languageTo;
    }


    public Translate getTranslate() {
        Iterable<TranslateConfig> configs = ConfigFactory.getConfigs(TranslateConfig.class, word.getLanguage().getCode());
        for (TranslateConfig config : configs) {
            try {
                config.setWord(word.getWord());
                config.setLangFrom(word.getLanguage().getCode());
                config.setLangTo(languageTo.getCode());
                String parseResult = config.getTranslate();

                log.debug(String.format("Parse and result: %s (%s)", config.getAddress(), parseResult));
                if (parseResult != null) {
                    Translate translate = new Translate(word, languageTo, parseResult);
                    translate.setSite_source(config.getAddress());
                    return translate;
                }
            } catch (Exception e) {
                log.warn(String.format("Error while parsing translate: %s", config.getAddress()), e);
            }
        }

        return null;
    }
}
