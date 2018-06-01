package com.lexicon.translator.dictionary;

import com.lexicon.translator.model.Language;
import com.lexicon.translator.model.Translate;
import com.lexicon.translator.model.Word;

import javax.validation.constraints.NotNull;

/**
 * Created by Komyshenets on 12/1/2017.
 */
public class WordGeneratorFactory {
    private WordGeneratorFactory() {
    }

    public static WordGenerator phonemeGenerator(@NotNull Word word) {
        String wordOriginal = word.getWord();
        String wordLang = word.getLanguage().getCode();
        switch (wordLang) {
//            case "it":
//                return new DefaultPhoneticGenerator(word, targetLang);
//            case "fi":
//                return new FinlandWordGenerator(word, targetLang);
//            case "en":
//                return new EnglishWordGenerator(word, targetLang);
            default:
                return new DefaultPhoneticGenerator(wordOriginal, wordLang);
        }
    }

    public static TranslateGenerator translateGenerator(@NotNull Translate translate) {
        String wordOriginal = translate.getOriginal().getWord();
        String wordLang = translate.getOriginal().getLanguage().getCode();
        String targetLang = translate.getLanguage().getCode();

        return new DefaultTranslateGenerator(wordOriginal, wordLang, targetLang);
    }

    public static UsagesGenerator usagesGenerator(@NotNull Word word, @NotNull Language targetLang) {
        String wordOriginal = word.getWord();
        String wordLang = word.getLanguage().getCode();

        return new DefaultUsagesGenerator(wordOriginal, wordLang, targetLang.getCode());

    }
}
