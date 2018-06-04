package com.translator.model;

import javax.persistence.*;

/**
 * Created by Komyshenets on 08.10.2017.
 */
@Entity
@Table(name = "translate")
public class Translate {

    private int id;

    private Word word;
    private Language language;
    private String translate;
    private String site_source;

    @Column(name = "site_source")
    public String getSite_source() {
        return site_source;
    }

    public void setSite_source(String site_source) {
        this.site_source = site_source;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id")
    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Column(name = "translate")
    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lang_id")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}

