package com.example.app.service;

import com.example.app.model.internal.FilmDetailModel;
import com.example.app.model.internal.FilmModel;
import com.example.app.model.request.FilmRequestModel;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<FilmModel> getAllFilms();

    Optional<FilmModel> getFilmById(String id);

    List<FilmDetailModel> getAllFilmsDetail();

    Optional<FilmDetailModel> getFilmDetailById(String id);

    Optional<FilmModel> getFilmStockById(String id);

    FilmModel addFilm(FilmRequestModel model);

    FilmModel updateFilm(String id, FilmRequestModel model);

    void deleteFilmById(String id);
}
