package com.example.app.app.catalog.service;

import com.example.app.app.catalog.domain.dto.ActorDetailsDto;
import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import com.example.app.app.catalog.domain.mapper.ActorMapper;
import com.example.app.app.catalog.repository.ActorRepository;
import com.example.app.common.constant.FilmRating;
import com.example.app.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ActorDto.Actor> getActorList() {
        var list = actorRepository.findAll();
        return actorMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "actorResponseCache", key = "#actorId")
    public Optional<ActorDto.Actor> getActor(String actorId) {
        var entity = actorRepository.findById(Integer.valueOf(actorId)).orElseThrow(() ->
                new ResourceNotFoundException("Actor not found with id '" + actorId + "'"));
        return Optional.of(actorMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActorDetailsDto.ActorDetails> getActorDetailsList() {
        return actorRepository.findAllActorDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActorDetailsDto.ActorDetails> getActorDetails(String actorId) {
        var model = actorRepository.findActorDetailsById(Integer.valueOf(actorId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + actorId + "'");
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmDto.Film> getActorFilmList(String actorId) {
        return actorRepository.findAllActorFilmListById(Integer.valueOf(actorId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmDto.Film> getActorFilmList(String actorId, String releaseYear, String rating) {
        return actorRepository.findAllActorFilmListByIdWithFilter(Integer.valueOf(actorId),
                LocalDate.parse(releaseYear), FilmRating.valueOf(rating));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilmDto.Film> getActorFilm(String actorId, String filmId) {
        var model = actorRepository.findActorFilmById(Integer.valueOf(actorId), Integer.valueOf(filmId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + actorId + "'");
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilmDetailsDto.FilmDetails> getActorFilmDetails(String actorId, String filmId) {
        var model = actorRepository.findActorFilmDetailsById(Integer.valueOf(actorId), Integer.valueOf(filmId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + actorId + "'");
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActorDto.Actor> searchActorList(String name) {
        var list = actorRepository.findActorEntitiesByFullNameFirstNameContainsOrFullNameLastNameContains(name, name);
        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with name '" + name + "'");
        }
        return actorMapper.mapToDtoList(list);
    }

    @Override
    @Transactional
    public ActorDto.Actor addActor(ActorDto.ActorRequest model) {
        var entity = actorMapper.mapToEntity(model);
        var savedEntity = actorRepository.save(entity);
        return actorMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public FilmDto.Film addActorFilm(String actorId, String filmId) {
        return actorRepository.addActorFilm(Integer.valueOf(actorId), Integer.valueOf(filmId)).orElseThrow(() ->
                new ResourceNotFoundException("Actor not found with id '" + actorId + "'"));
    }

    @Override
    @Transactional
    @CachePut(value = "actorResponseCache", key = "#actorId")
    public ActorDto.Actor updateActor(String actorId, ActorDto.ActorRequest model) {
        var entity = actorRepository.findById(Integer.valueOf(actorId)).orElseThrow(() ->
                new ResourceNotFoundException("Actor not found with id '" + actorId + "'"));
        entity.update(actorMapper.mapToEntity(model));
        return actorMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "actorResponseCache", key = "#actorId")
    public void deleteActor(String actorId) {
        actorRepository.deleteById(Integer.valueOf(actorId));
    }

    @Override
    @Transactional
    public void removeActorFilm(String actorId, String filmId) {
        actorRepository.removeActorFilm(Integer.valueOf(actorId), Integer.valueOf(filmId));
    }
}
