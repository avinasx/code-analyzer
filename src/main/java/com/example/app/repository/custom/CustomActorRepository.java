package com.example.app.repository.custom;

import com.example.app.model.constant.FilmRating;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.ActorDetailsModel;
import com.example.app.model.internal.extra.FilmDetailsModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomActorRepository {
    List<ActorDetailsModel> findAllActorDetailsList();

    Optional<ActorDetailsModel> findActorDetailsById(Integer actorId);

    List<FilmModel> findAllActorFilmListById(Integer actorId);

    List<FilmModel> findAllActorFilmListByIdWithFilter(Integer actorId, LocalDate releaseYear, FilmRating rating);

    Optional<FilmModel> findActorFilmById(Integer actorId, Integer filmId);

    Optional<FilmDetailsModel> findActorFilmDetailsById(Integer actorId, Integer filmId);

    Optional<FilmModel> addActorFilm(Integer actorId, Integer filmId);

    void removeActorFilm(Integer actorId, Integer filmId);
}
