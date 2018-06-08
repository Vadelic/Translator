package com.translator.repository;

import com.translator.model.Language;
import org.springframework.data.repository.CrudRepository;


public interface LangRepository extends CrudRepository<Language, Long> {
        Language findFirstByCode(String code);

}