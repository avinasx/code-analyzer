package com.example.app.repository.custom;

import com.example.app.model.constant.FilmRating;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.FilmDetailModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomFilmRepository {
    List<FilmModel> findAllWithFilter(LocalDate releaseYear, FilmRating rating);

    List<ActorModel> findAllFilmActorsById(Integer filmId);

    Optional<ActorModel> findFilmActorById(Integer filmId, Integer actorId);

    List<FilmDetailModel> findAllFilmsDetail();

    Optional<FilmDetailModel> findFilmDetailById(Integer filmId);

    Optional<FilmModel> findFilmStockById(Integer filmId);
}
