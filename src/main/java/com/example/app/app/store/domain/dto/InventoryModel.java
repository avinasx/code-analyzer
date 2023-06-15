package com.example.app.app.store.domain.dto;

import com.example.app.app.catalog.domain.dto.FilmModel;
import com.example.app.app.rental.domain.dto.RentalModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
@ToString
@FieldNameConstants
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryModel {
    @JsonProperty("inventoryId")
    private Integer inventoryId;

    @JsonProperty("filmId")
    private Integer filmId;

    @JsonProperty("storeId")
    private Integer storeId;

    @JsonProperty("lastUpdate")
    private LocalDateTime lastUpdate;

    @JsonIgnore
    @JsonProperty("filmByFilmId")
    @ToString.Exclude
    private FilmModel filmByFilmId;

    @JsonIgnore
    @JsonProperty("storeByStoreId")
    @ToString.Exclude
    private StoreModel storeByStoreId;

    @JsonIgnore
    @JsonProperty("rentalsByInventoryId")
    @ToString.Exclude
    private Collection<RentalModel> rentalsByInventoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryModel that = (InventoryModel) o;
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
