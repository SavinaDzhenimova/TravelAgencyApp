package org.travelagency.model.entity;

import jakarta.persistence.*;
import org.travelagency.model.enums.LanguageName;

import java.util.Objects;

@Table
@Entity(name = "languages")
public class Language extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private LanguageName languageName;

    @Column(nullable = false, unique = true)
    private String name;

    public Language() {
    }

    public LanguageName getLanguageName() {
        return languageName;
    }

    public void setLanguageName(LanguageName languageName) {
        this.languageName = languageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return name != null && name.equalsIgnoreCase(language.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.toLowerCase().hashCode() : 0;
    }
}
