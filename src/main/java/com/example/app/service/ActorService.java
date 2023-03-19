package com.example.app.service;

import com.example.app.model.entity.ActorEntity;
import com.example.app.model.internal.ActorDetailModel;
import com.example.app.model.request.ActorRequestModel;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<ActorEntity> getAllActors();

    Optional<ActorEntity> getActorById(String id);

    List<ActorDetailModel> getAllActorsDetail();

    Optional<ActorDetailModel> getActorDetailById(String id);

    List<ActorEntity> findActorByName(String name);

    ActorEntity addActor(ActorRequestModel model);

    ActorEntity updateActor(String id, ActorRequestModel model);

    void deleteActorById(String id);
}
