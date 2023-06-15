package com.example.app.app.catalog.service;

import com.example.app.app.catalog.domain.dto.ActorModel;
import com.example.app.app.catalog.domain.dto.FilmDetailsModel;
import com.example.app.app.catalog.domain.dto.FilmModel;
import com.example.app.app.catalog.domain.dto.FilmRequestModel;
import com.example.app.app.catalog.domain.mapper.FilmMapper;
import com.example.app.app.catalog.repository.FilmRepository;
import com.example.app.common.constant.FilmRating;
import com.example.app.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FilmModel> getFilmList() {
        var list = filmRepository.findAll();
        return filmMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmModel> getFilmList(String releaseYear, String rating, Pageable pageable) {
        return (releaseYear == null || rating == null)
                ? filmRepository.findAllFilmList(pageable)
                : filmRepository.findAllFilmListWithFilter(LocalDate.parse(releaseYear), FilmRating.valueOf(rating), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilmModel> getFilm(String filmId) {
        var entity = filmRepository.findById(Integer.valueOf(filmId)).orElseThrow(() ->
                new ResourceNotFoundException("Film not found with id '" + filmId + "'"));
        return Optional.of(filmMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ActorModel> getFilmActorList(String filmId) {
        return filmRepository.findAllFilmActorListById(Integer.valueOf(filmId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ActorModel> getFilmActor(String filmId, String actorId) {
        var model = filmRepository.findFilmActorById(Integer.valueOf(filmId), Integer.valueOf(actorId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Actor not found with id '" + actorId + "'");
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmDetailsModel> getFilmDetailsList() {
        return filmRepository.findAllFilmListDetail();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilmDetailsModel> getFilmDetails(String filmId) {
        var model = filmRepository.findFilmDetailsById(Integer.valueOf(filmId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + filmId + "'");
        }
        return model;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FilmModel> getFilmStock(String filmId) {
        var model = filmRepository.findFilmStockById(Integer.valueOf(filmId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + filmId + "'");
        }
        return model;
    }

    @Override
    @Transactional
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
