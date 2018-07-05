package com.translator.model;

import javax.persistence.*;

@Entity
@Table(name = "language")
public class Language extends JPA {

    private String code;
    private String description;

    public Language() {
    }

    public Language(String lang, String description) {
        this.code = lang;
        this.description = description;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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