package com.lexicon.translator.model;

import javax.persistence.*;

/**
 * Created by Komyshenets on 08.10.2017.
 */
@Entity
@Table(name = "translate_words")
public class Translate {

    private int id;

    private Word original;
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

    public Translate(Word word, Language language) {
        this.original = word;
        this.language = language;

    }

    public Translate() {
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
    @JoinColumn(name = "original_word_id")
    public Word getOriginal() {
        return original;
    }

    public void setOriginal(Word original) {
        this.original = original;
    }

    @Column(name = "translate")
    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}

