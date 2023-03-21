package com.example.app.model.internal.reserved;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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
