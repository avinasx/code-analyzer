package com.example.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "film_text")
@Table(name = "film_text", schema = "sakila")
@Getter
@Setter
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
    private String title;

    @Basic
    @Column(name = "description", nullable = true, length = -1, columnDefinition = "TEXT")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmTextEntity that = (FilmTextEntity) o;
        return Objects.equals(filmId, that.filmId)
                && Objects.equals(title, that.title)
                && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmId, title, description);
    }
}
