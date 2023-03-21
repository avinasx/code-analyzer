package com.example.app.model.internal.reserved;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmTextModel {
    @JsonProperty("filmId")
    private Integer filmId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmTextModel that = (FilmTextModel) o;
        return Objects.equal(filmId, that.filmId)
                && Objects.equal(title, that.title)
                && Objects.equal(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(filmId, title, description);
    }
}
