package com.example.app.app.catalog.repository.custom;

import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import com.example.app.common.constant.FilmRating;
import org.springframework.data.domain.Pageable;

import java.time.Year;
import java.util.List;
import java.util.Optional;

public interface CustomFilmRepository {
    List<FilmDto.Film> findAllFilmList(Pageable pageable);

    List<FilmDto.Film> findAllFilmListWithFilter(Year releaseYear, FilmRating rating, Pageable pageable);

    List<ActorDto.Actor> findAllFilmActorListById(Integer filmId);

    Optional<ActorDto.Actor> findFilmActorById(Integer filmId, Integer actorId);

    List<FilmDetailsDto.FilmDetails> findAllFilmListDetail();

    Optional<FilmDetailsDto.FilmDetails> findFilmDetailsById(Integer filmId);

    Optional<FilmDto.Film> findFilmStockById(Integer filmId);
}
