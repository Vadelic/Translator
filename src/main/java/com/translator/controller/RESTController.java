package com.translator.controller;

import com.translator.exception.TranslatorException;
import com.translator.generator.DefaultPhoneticGenerator;
import com.translator.generator.DefaultTranslateGenerator;
import com.translator.generator.DefaultUsagesGenerator;
import com.translator.model.Language;
import com.translator.model.LanguagePack;
import com.translator.model.UsageSentence;
import com.translator.model.Word;
import com.translator.repository.LangRepository;
import com.translator.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public Word greeting(@RequestParam(value = "word") String wordQuery, @RequestParam(value = "lang") String lang) throws Exception {
        Language wordLang = langRepository.findFirstByCode(lang.split("-")[0].toLowerCase().trim());
        Language languageTo = langRepository.findFirstByCode(lang.split("-")[1].toLowerCase().trim());
        if (wordLang == null || languageTo == null) {
            Iterable<Language> all = langRepository.findAll();
            List<String> collect = StreamSupport.stream(all.spliterator(), false).map(Language::getCode).collect(Collectors.toList());
            if (collect==null) collect = new ArrayList<>();
            throw new Exception("invalid lang. Available is: " + collect.toString());
        }

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
            if (phoneticGenerator.getPhonetic()) {
                wordRepository.save(word);
            }
        }
    }

    private void translateWord(Language languageTo, Word word) throws Exception {
        LanguagePack languagePack = word.getLanguagePack(languageTo);
        if (languagePack == null) {
            languagePack = new LanguagePack(languageTo);

            DefaultTranslateGenerator translateGenerator = new DefaultTranslateGenerator(word, languagePack);
            if (!translateGenerator.getTranslate()) {
                throw new Exception(String.format("No have translate for %s (%s-%s)", word.getWord(), word.getLanguage().getCode(), languageTo.getCode()));

            } else {
                word.getTranslatePacks().add(languagePack);
                wordRepository.save(word);
            }
        }
    }


    private void fillUsages(Language languageTo, Word word) {
        LanguagePack languagePack = word.getLanguagePack(languageTo);
        if (languagePack == null)
            languagePack = new LanguagePack(languageTo);


        if (languagePack.getSentences().isEmpty()) {
            DefaultUsagesGenerator usagesGenerator = new DefaultUsagesGenerator(word, languageTo);
            List<UsageSentence> usages = usagesGenerator.getUsages();
            if (!usages.isEmpty()) {
                languagePack.getSentences().addAll(usages);
                wordRepository.save(word);
            }
        }
    }
}