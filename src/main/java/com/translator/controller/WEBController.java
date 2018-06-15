package com.translator.controller;

import com.translator.repository.LangRepository;
import com.translator.repository.WordRepository;
import com.translator.model.Language;
import com.translator.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 14675742 on 04.06.2018.
 */

@RestController
public class WEBController {
    @Autowired
    private LangRepository langRepository;
    @Autowired
    private WordRepository wordRepository;


    @RequestMapping("/availableLangs")
    public Iterable<Language> getLanguages() {
        return langRepository.findAll();
    }

    @RequestMapping("/addLang")
    public Language addLanguage(@RequestParam(value = "lang") String lang, @RequestParam(value = "description") String description) {
        return langRepository.save(new Language(lang, description));
    }

    @RequestMapping("/words")
    public Iterable<String> getWordsByLang(@RequestParam(value = "lang") String lang) {
        List<Word> words = wordRepository.findAllByLanguage_Code(lang);
        return words.stream().map(Word::getWord).collect(Collectors.toList());
    }

    @RequestMapping("/addWord")
    public boolean addWord(@RequestParam(value = "word") String word, @RequestParam(value = "lang") String lang) {

        Word wordObj = new Word();
        Language language = langRepository.findFirstByCode(lang);
        wordObj.setLanguage(language);
        wordObj.setWord(word);
        wordObj = wordRepository.save(wordObj);
        return true;
    }


    @RequestMapping("/translate")
    public Word getWordsTranslate(@RequestParam(value = "word") String word, @RequestParam(value = "lang") String lang) {
        return wordRepository.findWordByWordAndLanguageCode(word, lang);
    }


}
