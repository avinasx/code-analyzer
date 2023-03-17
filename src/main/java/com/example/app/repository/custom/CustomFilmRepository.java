package com.example.app.repository.custom;

import com.example.app.model.entity.FilmEntity;
import com.example.app.model.internal.FilmDetailModel;

import java.util.List;
import java.util.Optional;

public interface CustomFilmRepository {
    List<FilmDetailModel> findAllFilmsDetail();

    Optional<FilmDetailModel> findFilmDetailById(Integer id);

    Optional<FilmEntity> findFilmStockById(Integer id);
}
