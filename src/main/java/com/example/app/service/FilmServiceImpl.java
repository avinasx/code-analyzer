package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.constant.FilmRating;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.FilmDetailModel;
import com.example.app.model.mapping.mapper.FilmMapper;
import com.example.app.model.request.FilmRequestModel;
import com.example.app.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    @Override
    public List<FilmModel> getFilms() {
        var list = filmRepository.findAll();
        return filmMapper.mapToDtoList(list);
    }

    @Override
    public List<FilmModel> getFilms(String releaseYear, String rating) {
        return filmRepository.findAllWithFilter(LocalDate.parse(releaseYear), FilmRating.valueOf(rating));
    }

    @Override
    public Optional<FilmModel> getFilm(String filmId) {
        var entity = filmRepository.findById(Integer.valueOf(filmId)).orElseThrow(() ->
                new ResourceNotFoundException("Film not found with id '" + filmId + "'"));
        return Optional.of(filmMapper.mapToDto(entity));
    }

    @Override
    public List<ActorModel> getFilmActors(String filmId) {
        return filmRepository.findAllFilmActorsById(Integer.valueOf(filmId));
    }

    @Override
    public Optional<ActorModel> getFilmActor(String filmId, String actorId) {
        var model = filmRepository.findFilmActorById(Integer.valueOf(filmId), Integer.valueOf(actorId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + actorId + "'");
        }
        return model;
    }

    @Override
    public List<FilmDetailModel> getFilmsDetail() {
        return filmRepository.findAllFilmsDetail();
    }

    @Override
    public Optional<FilmDetailModel> getFilmDetail(String filmId) {
        var model = filmRepository.findFilmDetailById(Integer.valueOf(filmId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + filmId + "'");
        }
        return model;
    }

    @Override
    public Optional<FilmModel> getFilmStock(String filmId) {
        var model = filmRepository.findFilmStockById(Integer.valueOf(filmId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + filmId + "'");
        }
        return model;
    }

    @Override
    public FilmModel addFilm(FilmRequestModel model) {
        var entity = filmMapper.mapToEntity(model);
        var savedEntity = filmRepository.save(entity);
        return filmMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public FilmModel updateFilm(String filmId, FilmRequestModel model) {
        var entity = filmRepository.findById(Integer.valueOf(filmId)).orElseThrow(() ->
                new ResourceNotFoundException("Film not found with id '" + filmId + "'"));
        entity.update(filmMapper.mapToEntity(model));
        return filmMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deleteFilm(String filmId) {
        filmRepository.deleteById(Integer.valueOf(filmId));
    }
}
