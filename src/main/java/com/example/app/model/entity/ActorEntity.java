package com.example.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "actor")
@Table(name = "actor", schema = "sakila")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActorEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "actor_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actorId;

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "actorByActorId", cascade = CascadeType.ALL)
    private Collection<FilmActorEntity> filmActorsByActorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorEntity that = (ActorEntity) o;
        return Objects.equals(actorId, that.actorId)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorId, firstName, lastName, lastUpdate);
    }
}
