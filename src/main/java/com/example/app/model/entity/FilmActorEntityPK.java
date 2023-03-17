package com.example.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
public class FilmActorEntityPK implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "actor_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actorId;
    @Id
    @Column(name = "film_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorEntityPK that = (FilmActorEntityPK) o;
        return Objects.equals(actorId, that.actorId)
                && Objects.equals(filmId, that.filmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, filmId);
    }
}
