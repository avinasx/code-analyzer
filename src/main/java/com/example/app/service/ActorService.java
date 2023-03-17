package com.example.app.service;

import com.example.app.model.entity.ActorEntity;
import com.example.app.model.internal.ActorDetailModel;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<ActorEntity> getAllActors();

    Optional<ActorEntity> getActorById(Integer id);

    List<ActorDetailModel> getAllActorsDetail();

    Optional<ActorDetailModel> getActorDetailById(Integer id);

    List<ActorEntity> findActorByName(String name);

    ActorEntity addActor(ActorEntity entity);

    ActorEntity updateActor(ActorEntity entity);

    void deleteActorById(Integer id);
}
