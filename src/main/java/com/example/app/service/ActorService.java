package com.example.app.service;

import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.ActorDetailModel;
import com.example.app.model.internal.extra.FilmDetailModel;
import com.example.app.model.request.ActorRequestModel;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<ActorModel> getActors();

    Optional<ActorModel> getActor(String actorId);

    List<ActorDetailModel> getActorsDetail();

    Optional<ActorDetailModel> getActorDetail(String actorId);

    List<FilmModel> getActorFilms(String actorId);

    List<FilmModel> getActorFilms(String actorId, String releaseYear, String rating);

    Optional<FilmModel> getActorFilm(String actorId, String filmId);

    Optional<FilmDetailModel> getActorFilmDetail(String actorId, String filmId);

    List<ActorModel> searchActors(String name);

    ActorModel addActor(ActorRequestModel model);

    FilmModel addActorFilm(String actorId, String filmId);

    ActorModel updateActor(String actorId, ActorRequestModel model);

    void deleteActor(String actorId);

    void removeActorFilm(String actorId, String filmId);
}
