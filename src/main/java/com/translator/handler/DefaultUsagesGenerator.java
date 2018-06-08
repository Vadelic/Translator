package com.translator.handler;

import com.translator.dictionary.ConfigFactory;
import com.translator.dictionary.UsagesConfig;
import com.translator.model.Language;
import com.translator.model.UsageSentence;
import com.translator.model.Word;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Komyshenets on 12/11/2017.
 */
public class DefaultUsagesGenerator {
    private final Logger log = Logger.getLogger(getClass());
    private Word wordOriginal;
    private Language wordLang;
    private Language targetLang;


    public DefaultUsagesGenerator(Word wordOriginal, Language wordLang, Language targetLang) {
        this.wordOriginal = wordOriginal;
        this.wordLang = wordLang;
        this.targetLang = targetLang;
    }

    public List<UsageSentence> getUsages() {
        List<UsageSentence> usageSentences = new ArrayList<>();

        Iterable<UsagesConfig> configs = ConfigFactory.getUsageConfigs(wordOriginal.getWord(), wordLang.getCode(), targetLang.getCode());
        for (UsagesConfig config : configs) {
            try {
                Map<String, String> usages = config.getUsages();
                if (!usages.isEmpty()) {

                    usageSentences.addAll(getUsages(usages, config.getAddress()));
                }
                if (usageSentences.size() > 10) break;
            } catch (Exception e) {
                log.warn(String.format("Error while parsing usages sentence: %s", config.getAddress()), e);
            }
        }
        return usageSentences;
    }

    private List<UsageSentence> getUsages(Map<String, String> usages, String siteSource) {
        List<UsageSentence> usageSentences = new ArrayList<>();

        for (Map.Entry<String, String> entry : usages.entrySet()) {
            UsageSentence sentence = new UsageSentence(wordOriginal, wordLang, entry.getKey(), entry.getValue());
            sentence.setSite_source(siteSource);
            usageSentences.add(sentence);
        }
        return usageSentences;
    }
}
