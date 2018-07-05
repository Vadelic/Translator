package com.translator.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 14675742 on 09.06.2018.
 */
@Entity
@Table(name = "LanguagePack")
public class LanguagePack  extends JPA  {
    private Language language;
    private String translate;
    private List<UsageSentence> sentences = new ArrayList<>();

    private String resource;

    public LanguagePack(Language languageTo) {
        this.language = languageTo;
    }

    public LanguagePack() {
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "lang_id")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @JoinColumn(name = "translate")
    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name="PACK_SENTENCES_LINK",
            joinColumns=@JoinColumn(name="LANGUAGE_PACK_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="SENTENCES_ID", referencedColumnName="ID"))
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
