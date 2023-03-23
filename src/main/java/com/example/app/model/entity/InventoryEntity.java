package com.example.app.model.entity;

import com.google.common.base.Objects;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

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
    @Column(name = "inventory_id", nullable = false, columnDefinition = "MEDIUMINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inventoryId;

    @Basic
    @Column(name = "film_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    @NonNull
    private Integer filmId;

    @Basic
    @Column(name = "store_id", nullable = false, insertable = false, updatable = false, columnDefinition = "TINYINT UNSIGNED")
    @NonNull
    private Integer storeId;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NonNull
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "film_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private FilmEntity filmByFilmId;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private StoreEntity storeByStoreId;

    @OneToMany(mappedBy = "inventoryByInventoryId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<RentalEntity> rentalsByInventoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryEntity that = (InventoryEntity) o;
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
