package com.example.app.model.entity;

import com.example.app.model.enumeration.Category;
import com.example.app.model.mapping.CategoryConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@Entity(name = "category")
@Table(name = "category", schema = "sakila")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
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
    private String name;

    @Basic
    @Column(name = "last_update", nullable = false, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "categoryByCategoryId", cascade = CascadeType.ALL)
    private Collection<FilmCategoryEntity> filmCategoriesByCategoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity that = (CategoryEntity) o;
        return Objects.equals(categoryId, that.categoryId)
                && Objects.equals(name, that.name)
                && Objects.equals(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, lastUpdate);
    }
}
