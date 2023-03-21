package com.example.app.service;

import com.example.app.model.internal.ActorDetailModel;
import com.example.app.model.internal.ActorModel;
import com.example.app.model.request.ActorRequestModel;

import java.util.List;
import java.util.Optional;

public interface ActorService {
    List<ActorModel> getAllActors();

    Optional<ActorModel> getActorById(String id);

    List<ActorDetailModel> getAllActorsDetail();

    Optional<ActorDetailModel> getActorDetailById(String id);

    List<ActorModel> findActorByName(String name);

    ActorModel addActor(ActorRequestModel model);

    ActorModel updateActor(String id, ActorRequestModel model);

    void deleteActorById(String id);
}
