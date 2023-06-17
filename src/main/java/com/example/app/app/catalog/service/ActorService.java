package com.example.app.app.catalog.service;

import com.example.app.app.catalog.domain.dto.ActorDetailsDto;
import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<ActorDto.Actor> getActorList();

    Optional<ActorDto.Actor> getActor(String actorId);

    List<ActorDetailsDto.ActorDetails> getActorDetailsList();

    Optional<ActorDetailsDto.ActorDetails> getActorDetails(String actorId);

    List<FilmDto.Film> getActorFilmList(String actorId);

    List<FilmDto.Film> getActorFilmList(String actorId, String releaseYear, String rating);

    Optional<FilmDto.Film> getActorFilm(String actorId, String filmId);

    Optional<FilmDetailsDto.FilmDetails> getActorFilmDetails(String actorId, String filmId);

    List<ActorDto.Actor> searchActorList(String name);

    ActorDto.Actor addActor(ActorDto.ActorRequest model);

    FilmDto.Film addActorFilm(String actorId, String filmId);

    ActorDto.Actor updateActor(String actorId, ActorDto.ActorRequest model);

    void deleteActor(String actorId);

    void removeActorFilm(String actorId, String filmId);
}
