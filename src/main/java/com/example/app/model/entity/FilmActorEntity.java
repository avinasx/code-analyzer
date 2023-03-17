package com.example.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "film_actor")
@Table(name = "film_actor", schema = "sakila")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FilmActorEntityPK.class)
public class FilmActorEntity implements Serializable {
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

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "actor_id", referencedColumnName = "actor_id", nullable = false)
    private ActorEntity actorByActorId;

    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "film_id", nullable = false)
    private FilmEntity filmByFilmId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmActorEntity that = (FilmActorEntity) o;
        return Objects.equals(actorId, that.actorId)
                && Objects.equals(filmId, that.filmId)
                && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, filmId, lastUpdate);
    }
}
