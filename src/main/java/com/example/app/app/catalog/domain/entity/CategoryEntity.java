package com.example.app.app.catalog.domain.entity;

import com.example.app.app.catalog.domain.converter.CategoryConverter;
import com.example.app.common.constant.Category;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "category")
@Table(name = "category", schema = "sakila")
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "category_id", columnDefinition = "TINYINT UNSIGNED", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Convert(converter = CategoryConverter.class)
    private Category categoryId;

    @Basic
    @Column(name = "name", length = 25, nullable = false)
    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    @Basic
    @Column(name = "last_update", columnDefinition = "TIMESTAMP", nullable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NotNull
    private LocalDateTime lastUpdate;

    public void update(CategoryEntity entity) {
        this.name = entity.name;
        this.lastUpdate = entity.lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return categoryId == that.categoryId
                && Objects.equal(name, that.name)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryId, name, lastUpdate);
    }
}
