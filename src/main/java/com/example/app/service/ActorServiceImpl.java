package com.example.app.service;

import com.example.app.model.entity.ActorEntity;
import com.example.app.model.internal.ActorDetailModel;
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
    public Optional<ActorEntity> getActorById(Integer id) {
        return actorRepository.findById(id);
    }

    @Override
    public List<ActorDetailModel> getAllActorsDetail() {
        return actorRepository.findAllActorsDetail();
    }

    @Override
    public Optional<ActorDetailModel> getActorDetailById(Integer id) {
        return actorRepository.findActorDetailById(id);
    }

    @Override
    public List<ActorEntity> findActorByName(String name) {
        return actorRepository.findActorByName(name);
    }

    @Override
    public ActorEntity addActor(ActorEntity entity) {
        return actorRepository.save(entity);
    }

    @Override
    public void updateActor(ActorEntity entity) {
        actorRepository.save(entity);
    }

    @Override
    public void deleteActorById(Integer id) {
        actorRepository.deleteById(id);
    }
}
