package com.example.app.service;

import com.example.app.model.entity.FilmEntity;
import com.example.app.model.internal.FilmDetailModel;
import com.example.app.model.request.FilmRequestModel;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<FilmEntity> getAllFilms();

    Optional<FilmEntity> getFilmById(String id);

    List<FilmDetailModel> getAllFilmsDetail();

    Optional<FilmDetailModel> getFilmDetailById(String id);

    Optional<FilmEntity> getFilmStockById(String id);

    FilmEntity addFilm(FilmRequestModel model);

    FilmEntity updateFilm(String id, FilmRequestModel model);

    void deleteFilmById(String id);
}
