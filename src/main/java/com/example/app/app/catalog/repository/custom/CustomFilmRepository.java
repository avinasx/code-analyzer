package com.example.app.app.catalog.repository.custom;

import com.example.app.app.catalog.domain.dto.ActorModel;
import com.example.app.app.catalog.domain.dto.FilmDetailsModel;
import com.example.app.app.catalog.domain.dto.FilmModel;
import com.example.app.common.constant.FilmRating;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomFilmRepository {
    List<FilmModel> findAllFilmList(Pageable pageable);

    List<FilmModel> findAllFilmListWithFilter(LocalDate releaseYear, FilmRating rating, Pageable pageable);

    List<ActorModel> findAllFilmActorListById(Integer filmId);

    Optional<ActorModel> findFilmActorById(Integer filmId, Integer actorId);

    List<FilmDetailsModel> findAllFilmListDetail();

    Optional<FilmDetailsModel> findFilmDetailsById(Integer filmId);

    Optional<FilmModel> findFilmStockById(Integer filmId);
}
