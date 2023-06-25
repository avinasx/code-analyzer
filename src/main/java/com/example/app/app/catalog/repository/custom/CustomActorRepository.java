package com.example.app.app.catalog.repository.custom;

import com.example.app.app.catalog.domain.dto.ActorDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomActorRepository {
    List<ActorDetailsDto.ActorDetails> findAllActorDetailsList();

    Optional<ActorDetailsDto.ActorDetails> findActorDetailsById(Integer actorId);

    List<FilmDto.Film> findAllActorFilmListByIdWithCondition(Integer actorId, FilmDto.Film condition, Pageable pageable);

    Optional<FilmDto.Film> findActorFilmById(Integer actorId, Integer filmId);

    Optional<FilmDetailsDto.FilmDetails> findActorFilmDetailsById(Integer actorId, Integer filmId);

    Optional<FilmDto.Film> addActorFilm(Integer actorId, Integer filmId);

    void removeActorFilm(Integer actorId, Integer filmId);
}
