package com.translator.model;

import javax.persistence.*;

/**
 * Created by Komyshenets on 12/10/2017.
 */
@Entity
@Table(name = "usages_word")
public class UsageSentence {
    private int id;

    private Word word;
    private Language langTo;
    private String sentenceFrom;
    private String sentenceTo;
    private String site_source;

    public UsageSentence() {
    }


    public UsageSentence(Word word, Language langTo, String sentenceFrom, String sentenceTo) {
        this.word = word;
        this.langTo = langTo;
        this.sentenceFrom = sentenceFrom;
        this.sentenceTo = sentenceTo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_word_id")
    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_translate_id")
    public Language getLangTo() {
        return langTo;
    }

    public void setLangTo(Language langTo) {
        this.langTo = langTo;
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
