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
    private Map sentence;
    private String site_source;

    @ElementCollection
    @MapKeyColumn(name="sentence_original")
    @Column(name="sentence_translate")
    @CollectionTable(name="sentence", joinColumns=@JoinColumn(name="word_id"))
    public Map getSentence() {
        return sentence;
    }

    public void setSentence(Map sentence) {
        this.sentence = sentence;
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

    public void setSite_source(String helper) {
        this.site_source = helper;
    }
    @Column(name = "site_source")
    public String getSite_source() {
        return site_source;
    }
}
