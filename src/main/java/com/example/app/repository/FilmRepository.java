package com.example.app.repository;

import com.example.app.model.entity.FilmEntity;
import com.example.app.repository.custom.CustomFilmRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<FilmEntity, Integer>, CustomFilmRepository {
}
