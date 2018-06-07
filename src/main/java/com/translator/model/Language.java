package com.translator.model;

import javax.persistence.*;

@Entity
@Table(name = "language")
public class Language {

    private String code;
    private String description;

    public Language(String lang) {
        code = lang;
    }


    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language)) return false;

        Language language = (Language) o;

        return code.equals(language.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}