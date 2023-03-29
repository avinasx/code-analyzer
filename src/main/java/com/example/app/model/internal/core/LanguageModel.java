package com.example.app.model.internal.core;

import com.example.app.model.constant.Language;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageModel {
    @JsonProperty("languageId")
    private Language languageId;

    @JsonProperty("name")
    @Size(min = 1, max = 20)
    private String name;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("filmsByLanguageId")
    @ToString.Exclude
    private Collection<FilmModel> filmsByLanguageId;

    @JsonIgnore
    @JsonProperty("filmsByOriginalLanguageId")
    @ToString.Exclude
    private Collection<FilmModel> filmsByOriginalLanguageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageModel that = (LanguageModel) o;
        return languageId == that.languageId
                && Objects.equal(name, that.name)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(languageId, name, lastUpdate);
    }
}
