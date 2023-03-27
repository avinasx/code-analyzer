package com.example.app.service;

import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.FilmDetailModel;
import com.example.app.model.request.FilmRequestModel;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<FilmModel> getFilms();

    List<FilmModel> getFilms(String releaseYear, String rating);

    Optional<FilmModel> getFilm(String filmId);

    List<ActorModel> getFilmActors(String filmId);

    Optional<ActorModel> getFilmActor(String filmId, String actorId);

    List<FilmDetailModel> getFilmsDetail();

    Optional<FilmDetailModel> getFilmDetail(String filmId);

    Optional<FilmModel> getFilmStock(String filmId);

    FilmModel addFilm(FilmRequestModel model);

    FilmModel updateFilm(String filmId, FilmRequestModel model);

    void deleteFilm(String filmId);
}
