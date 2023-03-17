package com.example.app.service;

import com.example.app.model.entity.FilmEntity;
import com.example.app.model.internal.FilmDetailModel;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<FilmEntity> getAllFilms();

    Optional<FilmEntity> getFilmById(Integer id);

    List<FilmDetailModel> getAllFilmsDetail();

    Optional<FilmDetailModel> getFilmDetailById(Integer id);

    Optional<FilmEntity> getFilmStockById(Integer id);

    FilmEntity addFilm(FilmEntity entity);

    void updateFilm(FilmEntity entity);

    void deleteFilmById(Integer id);
}
