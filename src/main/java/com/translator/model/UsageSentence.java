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

    private String sentenceFrom;
    private String sentenceTo;
    private String resource;

    public UsageSentence(Language language, String sentenceFrom, String sentenceTo) {
        this.sentenceFrom = sentenceFrom;
        this.sentenceTo = sentenceTo;
    }

    public UsageSentence() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @JoinColumn(name = "resource")
    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
