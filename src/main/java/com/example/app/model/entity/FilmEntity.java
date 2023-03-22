package com.example.app.model.entity;

import com.example.app.model.enumeration.FilmRating;
import com.example.app.model.enumeration.Language;
import com.example.app.model.enumeration.SpecialFeature;
import com.example.app.model.mapping.converter.FilmRatingConverter;
import com.example.app.model.mapping.converter.LanguageConverter;
import com.example.app.model.mapping.converter.SpecialFeatureConverter;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Entity(name = "film")
@Table(name = "film", schema = "sakila", indexes = {
        @Index(name = "idx_title", columnList = "title"),
        @Index(name = "idx_fk_language_id", columnList = "language_id"),
        @Index(name = "idx_fk_original_language_id", columnList = "original_language_id")
})
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "film_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmId;

    @Basic
    @Column(name = "title", nullable = false, length = 128)
    @NonNull
    @Size(min = 1, max = 128)
    private String title;

    @Basic
    @Column(name = "description", nullable = true, length = -1, columnDefinition = "TEXT")
    @ColumnDefault("NULL")
    @Size(max = 65535)
    private String description;

    @Basic
    @Column(name = "release_year", nullable = true, columnDefinition = "YEAR")
    @ColumnDefault("NULL")
    private LocalDate releaseYear;

    @Basic
    @Column(name = "language_id", nullable = false, insertable = false, updatable = false, columnDefinition = "TINYINT UNSIGNED")
    @Convert(converter = LanguageConverter.class)
    @NonNull
    private Language languageId;

    @Basic
    @Column(name = "original_language_id", nullable = true, insertable = false, updatable = false, columnDefinition = "TINYINT UNSIGNED")
    @ColumnDefault("NULL")
    @Convert(converter = LanguageConverter.class)
    private Language originalLanguageId;

    @Basic
    @Column(name = "rental_duration", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    @ColumnDefault("3")
    @NonNull
    private Integer rentalDuration;

    @Basic
    @Column(name = "rental_rate", nullable = false, precision = 2, columnDefinition = "DECIMAL(4,2)")
    @ColumnDefault("4.99")
    @NonNull
    private BigDecimal rentalRate;

    @Basic
    @Column(name = "length", nullable = true, columnDefinition = "SMALLINT UNSIGNED")
    @ColumnDefault("NULL")
    private Integer length;

    @Basic
    @Column(name = "replacement_cost", nullable = false, precision = 2, columnDefinition = "DECIMAL(5,2)")
    @ColumnDefault("19.99")
    @NonNull
    private BigDecimal replacementCost;

    @Basic
    @Column(name = "rating", nullable = true, columnDefinition = "ENUM('G','PG','PG-13','R','NC-17')")
    @ColumnDefault("'G'")
    @Convert(converter = FilmRatingConverter.class)
    private FilmRating rating;

    @Basic
    @Column(name = "special_features", nullable = true, columnDefinition = "SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes')")
    @ColumnDefault("NULL")
    @Convert(converter = SpecialFeatureConverter.class)
    private Set<SpecialFeature> specialFeatures;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NonNull
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private LanguageEntity languageByLanguageId;

    @ManyToOne
    @JoinColumn(name = "original_language_id", referencedColumnName = "language_id")
    @ToString.Exclude
    private LanguageEntity languageByOriginalLanguageId;

    @OneToMany(mappedBy = "filmByFilmId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<FilmActorEntity> filmActorsByFilmId;

    @OneToMany(mappedBy = "filmByFilmId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<FilmCategoryEntity> filmCategoriesByFilmId;

    @OneToMany(mappedBy = "filmByFilmId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<InventoryEntity> inventoriesByFilmId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmEntity that = (FilmEntity) o;
        return Objects.equal(filmId, that.filmId)
                && Objects.equal(title, that.title)
                && Objects.equal(description, that.description)
                && Objects.equal(releaseYear, that.releaseYear)
                && languageId == that.languageId
                && originalLanguageId == that.originalLanguageId
                && Objects.equal(rentalDuration, that.rentalDuration)
                && Objects.equal(rentalRate, that.rentalRate)
                && Objects.equal(length, that.length)
                && Objects.equal(replacementCost, that.replacementCost)
                && rating == that.rating
                && Objects.equal(specialFeatures, that.specialFeatures)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(filmId, title, description, releaseYear, languageId, originalLanguageId,
                rentalDuration, rentalRate, length, replacementCost, rating, specialFeatures, lastUpdate);
    }
}
