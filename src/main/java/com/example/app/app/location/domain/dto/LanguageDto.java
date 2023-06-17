package com.example.app.app.location.domain.dto;

import com.example.app.app.catalog.domain.dto.FilmDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.Collection;

public class LanguageDto {
    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Language {
        @JsonProperty(Fields.languageId)
        private com.example.app.common.constant.Language languageId;

        @JsonProperty(Fields.name)
        @Size(min = 1, max = 20)
        private String name;

        @JsonProperty(Fields.lastUpdate)
        private LocalDateTime lastUpdate;

        @JsonIgnore
        @JsonProperty(Fields.filmsByLanguageId)
        @ToString.Exclude
        private Collection<FilmDto.Film> filmsByLanguageId;

        @JsonIgnore
        @JsonProperty(Fields.filmsByOriginalLanguageId)
        @ToString.Exclude
        private Collection<FilmDto.Film> filmsByOriginalLanguageId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Language that = (Language) o;
            return languageId == that.languageId
                    && Objects.equal(name, that.name)
                    && Objects.equal(lastUpdate, that.lastUpdate);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(languageId, name, lastUpdate);
        }
    }
}
