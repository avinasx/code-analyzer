package com.example.app.model.entity;

import com.example.app.model.enumeration.Category;
import com.example.app.model.mapping.converter.CategoryConverter;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

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
    @Column(name = "category_id", nullable = false, columnDefinition = "TINYINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Convert(converter = CategoryConverter.class)
    private Category categoryId;

    @Basic
    @Column(name = "name", nullable = false, length = 25)
    @NonNull
    @Size(min = 1, max = 25)
    private String name;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    @NonNull
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "categoryByCategoryId", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Collection<FilmCategoryEntity> filmCategoriesByCategoryId;

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
