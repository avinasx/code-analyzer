package com.example.app.service;

import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.FilmDetailsModel;
import com.example.app.model.request.FilmRequestModel;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<FilmModel> getFilmList();

    List<FilmModel> getFilmList(String releaseYear, String rating);

    Optional<FilmModel> getFilm(String filmId);

    List<ActorModel> getFilmActorList(String filmId);

    Optional<ActorModel> getFilmActor(String filmId, String actorId);

    List<FilmDetailsModel> getFilmDetailsList();

    Optional<FilmDetailsModel> getFilmDetails(String filmId);

    Optional<FilmModel> getFilmStock(String filmId);

    FilmModel addFilm(FilmRequestModel model);

    FilmModel updateFilm(String filmId, FilmRequestModel model);

    void deleteFilm(String filmId);
}
