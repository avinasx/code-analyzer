package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.ActorEntity;
import com.example.app.model.internal.ActorDetailModel;
import com.example.app.model.mapping.CopyUtils;
import com.example.app.model.request.ActorRequestModel;
import com.example.app.repository.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;

    @Override
    public List<ActorEntity> getAllActors() {
        return actorRepository.findAll();
    }

    @Override
    public Optional<ActorEntity> getActorById(String id) {
        return actorRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<ActorDetailModel> getAllActorsDetail() {
        return actorRepository.findAllActorsDetail();
    }

    @Override
    public Optional<ActorDetailModel> getActorDetailById(String id) {
        return actorRepository.findActorDetailById(Integer.valueOf(id));
    }

    @Override
    public List<ActorEntity> findActorByName(String name) {
        return actorRepository.findActorByName(name);
    }

    @Override
    public ActorEntity addActor(ActorRequestModel model) {
        var entity = new ActorEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return actorRepository.save(entity);
    }

    @Override
    public ActorEntity updateActor(String id, ActorRequestModel model) {
        var resource = actorRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return actorRepository.save(entity);
    }

    @Override
    public void deleteActorById(String id) {
        actorRepository.deleteById(Integer.valueOf(id));
    }
}
