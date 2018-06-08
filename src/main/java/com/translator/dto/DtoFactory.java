package com.translator.dto;

import com.translator.model.Language;
import com.translator.model.Word;

/**
 * Created by 14675742 on 08.06.2018.
 */
public class DtoFactory {
    static WordDTO getWordDTO(Word word) {
        WordDTO wordDTO = new WordDTO();
        wordDTO.word = word.getWord();
        wordDTO.language = word.getLanguage().getCode();
        wordDTO.phoneme = word.getPhoneme();
        wordDTO.subject = word.getSubject();
        

        return null;
    }

    static WordDTO getWordDTO(Word word, Language lang) {
        return null;
    }
}
