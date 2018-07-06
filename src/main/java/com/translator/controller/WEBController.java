package com.translator.controller;

import com.translator.dto.TranslateRq;
import com.translator.exception.TranslatorException;
import com.translator.generator.DefaultPhoneticGenerator;
import com.translator.generator.LanguagePackCreator;
import com.translator.model.Language;
import com.translator.model.LanguagePack;
import com.translator.model.Word;
import com.translator.repository.LangRepository;
import com.translator.repository.PackRepository;
import com.translator.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final LangRepository langRepository;
    private final WordRepository wordRepository;
    private final PackRepository packRepository;

    @Autowired
    public WEBController(LangRepository langRepository, WordRepository wordRepository, PackRepository packRepository) {
        this.wordRepository = wordRepository;
        this.packRepository = packRepository;
        this.langRepository = langRepository;
    }


    @RequestMapping("/availableLangs")
    public Iterable<Language> getLanguages() {
        return langRepository.findAll();
    }

    @RequestMapping("/words")
    public Iterable<String> getWordsByLang(@RequestParam(value = "lang") String lang) {
        List<Word> words = wordRepository.findAllByLanguage_Code(lang);
        return words.stream().map(Word::getWord).collect(Collectors.toList());
    }

    @RequestMapping("/wordItem")
    public Word getWordsTranslate(@RequestParam(value = "word") String word, @RequestParam(value = "lang") String lang) {
        Word wordObj = wordRepository.findWordByWordAndLanguageCode(word, lang);
        if (wordObj.getPhoneme() == null) {
            DefaultPhoneticGenerator phoneticGenerator = new DefaultPhoneticGenerator(wordObj);
            if (phoneticGenerator.getPhonetic())
                wordRepository.save(wordObj);
        }
        return wordObj;
    }


    @RequestMapping("/addPack")
    public Word addPack(@RequestBody TranslateRq translateRq) throws TranslatorException {

        Word word = translateRq.getWord();
        Language language = translateRq.getLanguage();

        if (word.getLanguagePack(language) != null) {
            throw new TranslatorException("this translate pack is exist");
        }

        LanguagePack pack = new LanguagePackCreator().createAndFill(word, language);
        pack = packRepository.save(pack);
        word.addLanguagePack(pack);
        word = wordRepository.save(word);
        return word;
    }


    @RequestMapping("/addLang")
    public Language addLanguage(@RequestParam(value = "lang") String lang, @RequestParam(value = "description") String description) {
        return langRepository.save(new Language(lang, description));
    }

    @RequestMapping("/addWord")
    public Word addWord(@RequestParam(value = "word") String word, @RequestParam(value = "lang") String lang) throws TranslatorException {
        if (wordRepository.findWordByWordAndLanguageCode(word, lang) != null)
            throw new TranslatorException("this word is exist");
        Word wordObj = new Word(word.toLowerCase(), langRepository.findFirstByCode(lang));
        return wordRepository.save(wordObj);
    }

    @RequestMapping("/saveTranslatePack")
    public LanguagePack saveTranslatePack(@RequestBody LanguagePack pack) {
        return packRepository.save(pack);
    }

    @RequestMapping("/saveWord")
    public Word saveWord(@RequestBody Word word) {
        return wordRepository.save(word);
    }
}
