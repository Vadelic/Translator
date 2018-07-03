package com.translator.generator;

import com.translator.exception.TranslatorException;
import com.translator.model.Language;
import com.translator.model.LanguagePack;
import com.translator.model.UsageSentence;
import com.translator.model.Word;
import com.translator.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * Created by 14675742 on 29.06.2018.
 */
@Component
public class LanguagePackCreator {

    @Autowired
    private PackRepository packRepository;



    public LanguagePack createAndFill(Word word, Language languageTo) throws TranslatorException {
        LanguagePack languagePack = new LanguagePack(languageTo);

        if (fill(word, languagePack)) {
            return languagePack;
//            return packRepository.save(languagePack);
        }

        return null;
    }

    public boolean fill(Word word, LanguagePack languagePack) throws TranslatorException {
        boolean changed = false;
        if (languagePack.getTranslate() == null) {
            changed = fillTranslate(languagePack, word);
        }

        if (languagePack.getSentences().isEmpty()) {
            changed = fillSentences(languagePack, word)|| changed;
        }

        return changed;
    }


    private boolean fillTranslate(LanguagePack languagePack, Word word) throws TranslatorException {

        DefaultTranslateGenerator translateGenerator = new DefaultTranslateGenerator(word, languagePack);
        if (!translateGenerator.getTranslate()) {
            throw new TranslatorException(String.format("No have translate for %s (%s-%s)", word.getWord(), word.getLanguage().getCode(), languagePack.getLanguage().getCode()));
        }
        return true;
    }


    private boolean fillSentences(LanguagePack languagePack, Word word) {
        DefaultUsagesGenerator usagesGenerator = new DefaultUsagesGenerator(word, languagePack.getLanguage());
        List<UsageSentence> usages = usagesGenerator.getUsages();
        if (!usages.isEmpty()) {
            languagePack.getSentences().addAll(usages);
            return true;
        } else {
            return false;
        }
    }
}
