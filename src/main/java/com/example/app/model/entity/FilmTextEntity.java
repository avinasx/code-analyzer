package com.example.app.model.entity;

import com.google.common.base.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "film_text")
@Table(name = "film_text", schema = "sakila", indexes = {
        @Index(name = "idx_title_description", columnList = "title, description")
})
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmTextEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmId;

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    @NonNull
    @Size(min = 1, max = 255)
    private String title;

    @Basic
    @Column(name = "description", nullable = true, length = -1, columnDefinition = "TEXT")
    @Size(max = 65535)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmTextEntity that = (FilmTextEntity) o;
        return Objects.equal(filmId, that.filmId)
                && Objects.equal(title, that.title)
                && Objects.equal(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(filmId, title, description);
    }
}
