package com.translator.dao;

        import com.translator.model.Word;
        import org.springframework.data.repository.CrudRepository;


public interface WordRepository extends CrudRepository<Word, Long> {

}