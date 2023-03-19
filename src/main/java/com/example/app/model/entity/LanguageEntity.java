package com.example.app.model.entity;

import com.example.app.model.enumeration.Language;
import com.example.app.model.mapping.LanguageConverter;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity(name = "language")
@Table(name = "language", schema = "sakila")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LanguageEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "language_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Convert(converter = LanguageConverter.class)
    private Language languageId;

    @Basic
    @Column(name = "name", nullable = false, length = 20, columnDefinition = "CHAR(20)")
    private String name;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "languageByLanguageId", cascade = CascadeType.ALL)
    private Collection<FilmEntity> filmsByLanguageId;

    @OneToMany(mappedBy = "languageByOriginalLanguageId", cascade = CascadeType.ALL)
    private Collection<FilmEntity> filmsByOriginalLanguageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageEntity that = (LanguageEntity) o;
        return languageId == that.languageId
                && Objects.equal(name, that.name)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(languageId, name, lastUpdate);
    }
}
