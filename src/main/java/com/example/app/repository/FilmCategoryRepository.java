package com.example.app.repository;

import com.example.app.model.entity.FilmCategoryEntity;
import com.example.app.repository.custom.CustomFilmCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategoryEntity, Integer>, CustomFilmCategoryRepository {
}
