package com.translator.handler;

import com.translator.dictionary.ConfigFactory;
import com.translator.dictionary.TranslateConfig;
import com.translator.model.Language;
import com.translator.model.Translate;
import com.translator.model.Word;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Created by Komyshenets on 12/10/2017.
 */
public class DefaultTranslateGenerator  {
    private final Logger log = Logger.getLogger(getClass());
    private Word wordOriginal;
    private Language wordLang;
    private Language targetLang;


    public DefaultTranslateGenerator(Word wordOriginal, Language wordLang, Language targetLang) {
        this.wordOriginal = wordOriginal;
        this.wordLang = wordLang;
        this.targetLang = targetLang;
    }


    public Translate getTranslate() {
        Iterable<TranslateConfig> configs = ConfigFactory.getTranslateConfigs(wordOriginal.getWord(), wordLang.getCode(), targetLang.getCode());
        for (TranslateConfig config : configs) {
            try {
                String parseResult = config.getTranslate();

                log.debug(String.format("Parse and result: %s (%s)", config.getAddress(), parseResult));
                if (parseResult != null) {
                    Translate translate = new Translate(wordOriginal, targetLang, parseResult);
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
