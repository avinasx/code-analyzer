package com.example.app.services.store.domain.entity;

import com.example.app.services.catalog.domain.entity.FilmEntity;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "inventory")
@Table(name = "inventory", schema = "sakila", indexes = {
        @Index(name = "idx_fk_film_id", columnList = "film_id"),
        @Index(name = "idx_store_id_film_id", columnList = "store_id, film_id")
})
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class InventoryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "inventory_id", columnDefinition = "MEDIUMINT UNSIGNED", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @Basic
    @Column(name = "film_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false,
            insertable = false, updatable = false)
    @NotNull
    private Integer filmId;

    @Basic
    @Column(name = "store_id", columnDefinition = "TINYINT UNSIGNED", nullable = false,
            insertable = false, updatable = false)
    @NotNull
    private Integer storeId;

    @Basic
    @Column(name = "last_update", columnDefinition = "TIMESTAMP", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NotNull
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "film_id", nullable = false)
    @NotNull
    @ToString.Exclude
    private FilmEntity filmByFilmId;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    @NotNull
    @ToString.Exclude
    private StoreEntity storeByStoreId;

    public void update(InventoryEntity entity) {
        this.filmId = entity.getFilmId();
        this.storeId = entity.getStoreId();
        this.lastUpdate = entity.getLastUpdate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final InventoryEntity that = (InventoryEntity) o;
        return Objects.equal(inventoryId, that.inventoryId)
                && Objects.equal(filmId, that.filmId)
                && Objects.equal(storeId, that.storeId)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(inventoryId, filmId, storeId, lastUpdate);
    }
}
