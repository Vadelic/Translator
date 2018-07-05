package com.translator.dto;

import com.translator.model.Language;
import com.translator.model.Word;

/**
 * Created by 14675742 on 05.07.2018.
 */
public class TranslateRq {
private Word word;
private Language language;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
