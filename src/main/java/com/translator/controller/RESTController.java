package com.translator.controller;

import com.translator.repository.LangRepository;
import com.translator.repository.WordRepository;
import com.translator.exception.TranslatorException;
import com.translator.generator.DefaultPhoneticGenerator;
import com.translator.generator.DefaultTranslateGenerator;
import com.translator.generator.DefaultUsagesGenerator;
import com.translator.model.Language;
import com.translator.model.Translate;
import com.translator.model.UsageSentence;
import com.translator.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RESTController {
    private final WordRepository wordRepository;
    private final LangRepository langRepository;

    @Autowired
    public RESTController(WordRepository wordRepository, LangRepository langRepository) {
        this.wordRepository = wordRepository;
        this.langRepository = langRepository;
    }

    @RequestMapping("/api/translete")
    public Word greeting(@RequestParam(value = "word") String wordQuery, @RequestParam(value = "lang") String lang) throws TranslatorException {
        Language wordLang = langRepository.findFirstByCode(lang.split("-")[0].toLowerCase().trim());
        Language languageTo = langRepository.findFirstByCode(lang.split("-")[1].toLowerCase().trim());

        Word word = getOrCreateWord(wordQuery, wordLang);
        translateWord(languageTo, word);
        fillPhoneme(word);
        fillUsages(languageTo, word);
        return word;
    }

    private Word getOrCreateWord(@PathVariable String wordQuery, Language wordLang) {
        Word word = wordRepository.findWordByWordAndLanguageCode(wordQuery.toLowerCase().trim(), wordLang.getCode());
        if (word == null) {
            word = new Word(wordQuery, wordLang);
        }
        return word;
    }

    private void fillPhoneme(Word word) {
        if (word.getPhoneme() == null) {
            DefaultPhoneticGenerator phoneticGenerator = new DefaultPhoneticGenerator(word);
            String phonetic = phoneticGenerator.getPhonetic();
            if (phonetic != null) {
                word.setPhoneme(phonetic);
                wordRepository.save(word);
            }
        }
    }

    private void translateWord(Language languageTo, Word word) throws TranslatorException {
        if (word.getTranslateForLang(languageTo).isEmpty()) {
            DefaultTranslateGenerator translateGenerator = new DefaultTranslateGenerator(word, languageTo);
            Translate translate = translateGenerator.getTranslate();
            if (translate == null) {
                throw new TranslatorException(String.format("No have translate for %s %s-%s" + word.getWord(), languageTo.getCode()));
            } else {
                word.getTranslates().add(translate);
                wordRepository.save(word);
            }
        }
    }


    private void fillUsages(Language languageTo, Word word) {
        if (word.getUsagesForLang(languageTo).isEmpty()) {
            DefaultUsagesGenerator usagesGenerator = new DefaultUsagesGenerator(word, languageTo);
            List<UsageSentence> usages = usagesGenerator.getUsages();
            if (!usages.isEmpty()) {
                word.getSentences().addAll(usages);
                wordRepository.save(word);
            }
        }
    }

}