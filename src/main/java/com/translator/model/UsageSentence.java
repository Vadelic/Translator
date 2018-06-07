package com.translator.model;

import javax.persistence.*;
import java.util.Map;

/**
 * Created by Komyshenets on 12/10/2017.
 */
@Entity
@Table(name = "sentence")
public class UsageSentence {
    private int id;

    private Word word;
    private Language language;
    private String sentenceFrom;
    private String sentenceTo;
    private String site_source;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Column(name = "sentence_original")
    public String getSentenceFrom() {
        return sentenceFrom;
    }

    public void setSentenceFrom(String sentenceFrom) {
        this.sentenceFrom = sentenceFrom;
    }

    @Column(name = "sentence_translate")
    public String getSentenceTo() {
        return sentenceTo;
    }

    public void setSentenceTo(String sentenceTo) {
        this.sentenceTo = sentenceTo;
    }

    public void setSite_source(String helper) {
        this.site_source = helper;
    }
    @Column(name = "site_source")
    public String getSite_source() {
        return site_source;
    }
}
