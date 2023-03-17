package com.example.app.repository;

import com.example.app.model.entity.FilmTextEntity;
import com.example.app.repository.custom.CustomFilmTextRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmTextRepository extends JpaRepository<FilmTextEntity, Integer>, CustomFilmTextRepository {
}
