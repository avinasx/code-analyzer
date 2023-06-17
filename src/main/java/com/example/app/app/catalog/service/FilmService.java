package com.example.app.app.catalog.service;

import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<FilmDto.Film> getFilmList();

    List<FilmDto.Film> getFilmList(String releaseYear, String rating, Pageable pageable);

    Optional<FilmDto.Film> getFilm(String filmId);

    List<ActorDto.Actor> getFilmActorList(String filmId);

    Optional<ActorDto.Actor> getFilmActor(String filmId, String actorId);

    List<FilmDetailsDto.FilmDetails> getFilmDetailsList();

    Optional<FilmDetailsDto.FilmDetails> getFilmDetails(String filmId);

    Optional<FilmDto.Film> getFilmStock(String filmId);

    FilmDto.Film addFilm(FilmDto.FilmRequest model);

    FilmDto.Film updateFilm(String filmId, FilmDto.FilmRequest model);

    void deleteFilm(String filmId);
}
