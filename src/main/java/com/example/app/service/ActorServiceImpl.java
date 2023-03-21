package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.ActorDetailModel;
import com.example.app.model.internal.ActorModel;
import com.example.app.model.mapping.mapper.ActorMapper;
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
    private final ActorMapper actorMapper;

    @Override
    public List<ActorModel> getAllActors() {
        var result = actorRepository.findAll();
        return actorMapper.mapToDtoList(result);
    }

    @Override
    public Optional<ActorModel> getActorById(String id) {
        var result = actorRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Actor not found with id '" + id + "'"));
        return Optional.of(actorMapper.mapToDto(entity));
    }

    @Override
    public List<ActorDetailModel> getAllActorsDetail() {
        return actorRepository.findAllActorsDetail();
    }

    @Override
    public Optional<ActorDetailModel> getActorDetailById(String id) {
        var result = actorRepository.findActorDetailById(Integer.valueOf(id));
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + id + "'");
        }
        return result;
    }

    @Override
    public List<ActorModel> findActorByName(String name) {
        var result = actorRepository.findActorByName(name);
        return actorMapper.mapToDtoList(result);
    }

    @Override
    public ActorModel addActor(ActorRequestModel model) {
        var entity = actorMapper.mapToEntity(model);
        var result = actorRepository.save(entity);
        return actorMapper.mapToDto(result);
    }

    @Override
    public ActorModel updateActor(String id, ActorRequestModel model) {
        var result = actorRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Actor not found with id '" + id + "'"));
        actorMapper.updateEntity(model, entity);
        var updated = actorRepository.save(entity);
        return actorMapper.mapToDto(updated);
    }

    @Override
    public void deleteActorById(String id) {
        actorRepository.deleteById(Integer.valueOf(id));
    }
}
