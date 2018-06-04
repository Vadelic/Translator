package com.translator.model;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Komyshenets on 08.10.2017.
 */
@Entity
@Table(name = "words")
public class Word {
    private int id;

    private String word;
    private Language language;
    private String subject;

    private String phoneme;
    private List<Translate> translates;
    private Map<String, String> sentences;



    @ElementCollection
    @MapKeyColumn(name="sentence_original")
    @Column(name="sentence_translate")
    @CollectionTable(name="sentence", joinColumns=@JoinColumn(name="word_id"))
    public Map<String, String> getSentences() {
        return sentences;
    }

    public void setSentences(Map<String, String> sentences) {
        this.sentences = sentences;
    }

    @OneToMany(mappedBy = "original", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    public List<Translate> getTranslates() {
        return translates;
    }

    public void setTranslates(List<Translate> translates) {
        this.translates = translates;
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
    @JoinColumn(name = "lang_id")
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Column(name = "word")
    public String getWord() {
        return word;
    }

    public void setWord(String original) {
        this.word = original;
    }

    @Column(name = "subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String description) {
        this.subject = description;
    }

    @Column(name = "phoneme")
    public String getPhoneme() {
        return phoneme;
    }

    public void setPhoneme(String phoneme) {
        this.phoneme = phoneme;
    }

}
