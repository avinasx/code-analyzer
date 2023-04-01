package com.example.app.repository.custom;

import com.example.app.model.constant.FilmRating;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.FilmDetailsModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomFilmRepository {
    List<FilmModel> findAllWithFilter(LocalDate releaseYear, FilmRating rating);

    List<ActorModel> findAllFilmActorListById(Integer filmId);

    Optional<ActorModel> findFilmActorById(Integer filmId, Integer actorId);

    List<FilmDetailsModel> findAllFilmListDetail();

    Optional<FilmDetailsModel> findFilmDetailsById(Integer filmId);

    Optional<FilmModel> findFilmStockById(Integer filmId);
}
