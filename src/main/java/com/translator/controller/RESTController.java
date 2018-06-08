package com.translator.controller;

import com.translator.repository.LangRepository;
import com.translator.repository.WordRepository;
import com.translator.exception.TranslatorException;
import com.translator.handler.DefaultPhoneticGenerator;
import com.translator.handler.DefaultTranslateGenerator;
import com.translator.handler.DefaultUsagesGenerator;
import com.translator.model.Language;
import com.translator.model.Translate;
import com.translator.model.UsageSentence;
import com.translator.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RESTController {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private LangRepository langRepository;

    @RequestMapping("/api/translete")
    public Word greeting(@RequestParam(value = "word") String wordQuery, @RequestParam(value = "lang") String lang) throws TranslatorException {
        Language wordLang = langRepository.findFirstByCode(lang.split("-")[0].toLowerCase().trim());
        Language targetLang = langRepository.findFirstByCode(lang.split("-")[1].toLowerCase().trim());

        Word word = wordRepository.findWordByWordAndLanguageCode(wordQuery.toLowerCase().trim(), wordLang.getCode());
        if (word == null) {
            word = new Word();
            word.setWord(wordQuery);
            word.setLanguage(wordLang);
        }

        if (word.getTranslateForLang(targetLang).isEmpty()) {
            DefaultTranslateGenerator translateGenerator = new DefaultTranslateGenerator(word, wordLang, targetLang);
            Translate translate = translateGenerator.getTranslate();
            if (translate == null) {
                throw new TranslatorException(String.format("No have translate for %s %s-%s" + word.getWord(), wordLang.getCode(), targetLang.getCode()));
            } else {
                word.getTranslates().add(translate);
                wordRepository.save(word);
            }
        }

        if (word.getPhoneme() == null) {
            DefaultPhoneticGenerator phoneticGenerator = new DefaultPhoneticGenerator(word, wordLang);
            String phonetic = phoneticGenerator.getPhonetic();
            if (phonetic != null) {
                word.setPhoneme(phonetic);
                wordRepository.save(word);
            }
        }

        if (word.getUsagesForLang(targetLang).isEmpty()) {
            DefaultUsagesGenerator usagesGenerator = new DefaultUsagesGenerator(word, wordLang, targetLang);
            List<UsageSentence> usages = usagesGenerator.getUsages();
            if (!usages.isEmpty()) {
                word.getSentences().addAll(usages);
                wordRepository.save(word);
            }
        }
        return word;
    }
}