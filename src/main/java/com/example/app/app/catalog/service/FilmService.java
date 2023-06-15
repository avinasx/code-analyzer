package com.example.app.app.catalog.service;

import com.example.app.app.catalog.domain.dto.ActorModel;
import com.example.app.app.catalog.domain.dto.FilmDetailsModel;
import com.example.app.app.catalog.domain.dto.FilmModel;
import com.example.app.app.catalog.domain.dto.FilmRequestModel;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<FilmModel> getFilmList();

    List<FilmModel> getFilmList(String releaseYear, String rating, Pageable pageable);

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
