package com.example.app.app.catalog.domain.entity;

import com.example.app.app.catalog.domain.converter.FilmRatingConverter;
import com.example.app.app.catalog.domain.converter.SpecialFeatureConverter;
import com.example.app.app.location.domain.converter.LanguageConverter;
import com.example.app.app.location.domain.entity.LanguageEntity;
import com.example.app.common.constant.FilmRating;
import com.example.app.common.constant.Language;
import com.example.app.common.constant.SpecialFeature;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumSet;

@Entity(name = "film")
@Table(name = "film", schema = "sakila", indexes = {
        @Index(name = "idx_title", columnList = "title"),
        @Index(name = "idx_fk_language_id", columnList = "language_id"),
        @Index(name = "idx_fk_original_language_id", columnList = "original_language_id")
})
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FilmEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "film_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmId;

    @Basic
    @Column(name = "title", length = 128, nullable = false)
    @NotNull
    @Size(min = 1, max = 128)
    private String title;

    @Basic
    @Column(name = "description", columnDefinition = "TEXT", length = -1, nullable = true)
    @ColumnDefault("NULL")
    @Size(max = 65535)
    private String description;

    @Basic
    @Column(name = "release_year", columnDefinition = "YEAR", nullable = true)
    @ColumnDefault("NULL")
    private LocalDate releaseYear;

    @Basic
    @Column(name = "language_id", columnDefinition = "TINYINT UNSIGNED", nullable = false,
            insertable = false, updatable = false)
    @Convert(converter = LanguageConverter.class)
    @NotNull
    private Language languageId;

    @Basic
    @Column(name = "original_language_id", columnDefinition = "TINYINT UNSIGNED", nullable = true,
            insertable = false, updatable = false)
    @ColumnDefault("NULL")
    @Convert(converter = LanguageConverter.class)
    private Language originalLanguageId;

    @Basic
    @Column(name = "rental_duration", columnDefinition = "TINYINT UNSIGNED", nullable = false)
    @ColumnDefault("3")
    @NotNull
    private Integer rentalDuration;

    @Basic
    @Column(name = "rental_rate", columnDefinition = "DECIMAL(4,2)", precision = 2, nullable = false)
    @ColumnDefault("4.99")
    @NotNull
    private BigDecimal rentalRate;

    @Basic
    @Column(name = "length", columnDefinition = "SMALLINT UNSIGNED", nullable = true)
    @ColumnDefault("NULL")
    private Integer length;

    @Basic
    @Column(name = "replacement_cost", columnDefinition = "DECIMAL(5,2)", precision = 2, nullable = false)
    @ColumnDefault("19.99")
    @NotNull
    private BigDecimal replacementCost;

    @Basic
    @Column(name = "rating", columnDefinition = "ENUM('G','PG','PG-13','R','NC-17')", nullable = true)
    @ColumnDefault("'G'")
    @Convert(converter = FilmRatingConverter.class)
    private FilmRating rating;

    @Basic
    @Column(name = "special_features", columnDefinition = "SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes')", nullable = true)
    @ColumnDefault("NULL")
    @Convert(converter = SpecialFeatureConverter.class)
    private EnumSet<SpecialFeature> specialFeatures;

    @Basic
    @Column(name = "last_update", columnDefinition = "TIMESTAMP", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NotNull
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "language_id", referencedColumnName = "language_id", nullable = false)
    @NotNull
    @ToString.Exclude
    private LanguageEntity languageByLanguageId;

    @ManyToOne
    @JoinColumn(name = "original_language_id", referencedColumnName = "language_id")
    @ToString.Exclude
    private LanguageEntity languageByOriginalLanguageId;

    public void update(FilmEntity entity) {
        this.title = entity.title;
        this.description = entity.description;
        this.releaseYear = entity.releaseYear;
        this.languageId = entity.languageId;
        this.originalLanguageId = entity.originalLanguageId;
        this.rentalDuration = entity.rentalDuration;
        this.rentalRate = entity.rentalRate;
        this.length = entity.length;
        this.replacementCost = entity.replacementCost;
        this.rating = entity.rating;
        this.specialFeatures = entity.specialFeatures;
        this.lastUpdate = entity.lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FilmEntity that = (FilmEntity) o;
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
