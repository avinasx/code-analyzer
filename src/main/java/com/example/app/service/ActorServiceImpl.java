package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.constant.FilmRating;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.ActorDetailModel;
import com.example.app.model.internal.extra.FilmDetailModel;
import com.example.app.model.mapping.mapper.ActorMapper;
import com.example.app.model.request.ActorRequestModel;
import com.example.app.repository.ActorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Override
    public List<ActorModel> getActors() {
        var list = actorRepository.findAll();
        return actorMapper.mapToDtoList(list);
    }

    @Override
    public Optional<ActorModel> getActor(String actorId) {
        var entity = actorRepository.findById(Integer.valueOf(actorId)).orElseThrow(() ->
                new ResourceNotFoundException("Actor not found with id '" + actorId + "'"));
        return Optional.of(actorMapper.mapToDto(entity));
    }

    @Override
    public List<ActorDetailModel> getActorsDetail() {
        return actorRepository.findAllActorsDetail();
    }

    @Override
    public Optional<ActorDetailModel> getActorDetail(String actorId) {
        var model = actorRepository.findActorDetailById(Integer.valueOf(actorId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + actorId + "'");
        }
        return model;
    }

    @Override
    public List<FilmModel> getActorFilms(String actorId) {
        return actorRepository.findAllActorFilmsById(Integer.valueOf(actorId));
    }

    @Override
    public List<FilmModel> getActorFilms(String actorId, String releaseYear, String rating) {
        return actorRepository.findAllActorFilmsByIdWithFilter(Integer.valueOf(actorId),
                LocalDate.parse(releaseYear), FilmRating.valueOf(rating));
    }

    @Override
    public Optional<FilmModel> getActorFilm(String actorId, String filmId) {
        var model = actorRepository.findActorFilmById(Integer.valueOf(actorId), Integer.valueOf(filmId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + actorId + "'");
        }
        return model;
    }

    @Override
    public Optional<FilmDetailModel> getActorFilmDetail(String actorId, String filmId) {
        var model = actorRepository.findActorFilmDetailById(Integer.valueOf(actorId), Integer.valueOf(filmId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + actorId + "'");
        }
        return model;
    }

    @Override
    public List<ActorModel> searchActors(String name) {
        var list = actorRepository.findActorEntitiesByFullNameFirstNameContainsOrFullNameLastNameContains(name, name);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with name '" + name + "'");
        }
        return actorMapper.mapToDtoList(list);
    }

    @Override
    public ActorModel addActor(ActorRequestModel model) {
        var entity = actorMapper.mapToEntity(model);
        var savedEntity = actorRepository.save(entity);
        return actorMapper.mapToDto(savedEntity);
    }

    @Override
    public FilmModel addActorFilm(String actorId, String filmId) {
        return actorRepository.addActorFilm(Integer.valueOf(actorId), Integer.valueOf(filmId)).orElseThrow(() ->
                new ResourceNotFoundException("Actor not found with id '" + actorId + "'"));
    }

    @Override
    @Transactional
    public ActorModel updateActor(String actorId, ActorRequestModel model) {
        var entity = actorRepository.findById(Integer.valueOf(actorId)).orElseThrow(() ->
                new ResourceNotFoundException("Actor not found with id '" + actorId + "'"));
        entity.update(actorMapper.mapToEntity(model));
        return actorMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deleteActor(String actorId) {
        actorRepository.deleteById(Integer.valueOf(actorId));
    }

    @Override
    @Transactional
    public void removeActorFilm(String actorId, String filmId) {
        actorRepository.removeActorFilm(Integer.valueOf(actorId), Integer.valueOf(filmId));
    }
}
