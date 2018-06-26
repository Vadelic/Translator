package com.translator.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by 14675742 on 09.06.2018.
 */
@Entity
@Table(name = "LanguagePack")
public class LanguagePack {
    private int id;
    private Language language;
//    private String word;
    private String translate;
    private List<UsageSentence> sentences;

    private String resource;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

//    public String getWord() {
//        return word;
//    }
//
//    public void setWord(String word) {
//        this.word = word;
//    }

    @JoinColumn(name = "translate")
    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    public List<UsageSentence> getSentences() {
        return sentences;
    }

    public void setSentences(List<UsageSentence> sentences) {
        this.sentences = sentences;
    }

    @JoinColumn(name = "resource")
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
