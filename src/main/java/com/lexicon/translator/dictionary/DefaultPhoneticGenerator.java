package com.lexicon.translator.dictionary;

import com.lexicon.translator.dictionary.parser.ConfigFactory;
import com.lexicon.translator.dictionary.parser.config.PhonemeConfig;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Komyshenets on 12/1/2017.
 */
public class DefaultPhoneticGenerator implements WordGenerator {
    private final Logger log = Logger.getLogger(getClass());
    private String wordOriginal;
    private String wordLang;

    DefaultPhoneticGenerator(String wordOriginal, String wordLang) {
        this.wordOriginal = wordOriginal;
        this.wordLang = wordLang;
    }


    @Override
    public Map<String, String> getPhonetic() {
        HashMap<String, String> map = new HashMap<>();
        Iterable<PhonemeConfig> configs = ConfigFactory.getPhoneticConfigs(wordOriginal, wordLang);
        for (PhonemeConfig config : configs) {
            try {
                String parseResult = config.getPhoneme();
                log.debug(String.format("%s - %s (%s)", wordOriginal, parseResult, config.getClass()));
                if (parseResult != null) {
                    map.put(config.getClass().getSimpleName(), parseResult);
                    return map;
                }
            } catch (Exception e) {
                log.warn(String.format("Error while parsing : %s, %s", wordOriginal, config.getClass()), e);
            }
        }
        return map;
    }


    //    private SendPhoto updateCard( Word word , Person person) {
//        try {
//            File file = new File("images", randomWord.toLowerCase() + ".jpg");
//            System.out.println(file.getAbsolutePath());
//            if (!file.exists()) {
//                file = new GoogleImageSearch().searchImages(file.getParent(), file.getName());
//            }
//
//            SendPhoto card = new SendPhoto().setNewPhoto(file).setChatId(person.getId());
//            card.disableNotification();
//
//            return card;
//        } catch (IOException e) {
//            log.warn("error while getting image", e);
//
//        }
//        return null;
//    }
//
//    boolean updateAudio(Word word) {
//        return false;
//    }

}
