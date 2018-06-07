package com.translator.dao;

import com.translator.model.Language;
import com.translator.model.Word;
import org.apache.commons.codec.language.bm.Lang;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface LangRepository extends CrudRepository<Language, Long> {
        Language findFirstByCode(String code);

}