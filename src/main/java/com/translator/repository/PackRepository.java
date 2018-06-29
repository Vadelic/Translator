package com.translator.repository;

import com.translator.model.LanguagePack;
import org.springframework.data.repository.CrudRepository;


public interface PackRepository extends CrudRepository<LanguagePack, Long> {

}