package com.example.app.repository.custom;

import com.example.app.model.entity.ActorEntity;
import com.example.app.model.internal.ActorDetailModel;

import java.util.List;
import java.util.Optional;

public interface CustomActorRepository {
    List<ActorDetailModel> findAllActorsDetail();

    Optional<ActorDetailModel> findActorDetailById(Integer id);

    List<ActorEntity> findActorByName(String name);
}
