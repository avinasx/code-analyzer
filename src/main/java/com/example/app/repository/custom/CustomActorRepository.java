package com.example.app.repository.custom;

import com.example.app.model.constant.FilmRating;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.ActorDetailModel;
import com.example.app.model.internal.extra.FilmDetailModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomActorRepository {
    List<ActorDetailModel> findAllActorsDetail();

    Optional<ActorDetailModel> findActorDetailById(Integer actorId);

    List<FilmModel> findAllActorFilmsById(Integer actorId);

    List<FilmModel> findAllActorFilmsByIdWithFilter(Integer actorId, LocalDate releaseYear, FilmRating rating);

    Optional<FilmModel> findActorFilmById(Integer actorId, Integer filmId);

    Optional<FilmDetailModel> findActorFilmDetailById(Integer actorId, Integer filmId);

    Optional<FilmModel> addActorFilm(Integer actorId, Integer filmId);

    void removeActorFilm(Integer actorId, Integer filmId);
}
