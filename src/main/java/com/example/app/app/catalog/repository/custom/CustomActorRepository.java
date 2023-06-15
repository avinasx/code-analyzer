package com.example.app.app.catalog.repository.custom;

import com.example.app.app.catalog.domain.dto.ActorDetailsModel;
import com.example.app.app.catalog.domain.dto.FilmDetailsModel;
import com.example.app.app.catalog.domain.dto.FilmModel;
import com.example.app.common.constant.FilmRating;

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
