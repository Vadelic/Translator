package com.translator.dao;

import com.translator.model.Language;
import com.translator.model.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface WordRepository extends CrudRepository<Word, Long> {


    List<Word> findAllByLanguage_Code(String language_code);

    Word findWordByWordAndLanguageCode(String word, String language_code);
}