package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.FilmDetailModel;
import com.example.app.model.internal.FilmModel;
import com.example.app.model.mapping.mapper.FilmMapper;
import com.example.app.model.request.FilmRequestModel;
import com.example.app.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    @Override
    public List<FilmModel> getAllFilms() {
        var list = filmRepository.findAll();
        return filmMapper.mapToDtoList(list);
    }

    @Override
    public Optional<FilmModel> getFilmById(String id) {
        var entity = filmRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Film not found with id '" + id + "'"));
        return Optional.of(filmMapper.mapToDto(entity));
    }

    @Override
    public List<FilmDetailModel> getAllFilmsDetail() {
        return filmRepository.findAllFilmsDetail();
    }

    @Override
    public Optional<FilmDetailModel> getFilmDetailById(String id) {
        var model = filmRepository.findFilmDetailById(Integer.valueOf(id));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + id + "'");
        }
        return model;
    }

    @Override
    public Optional<FilmModel> getFilmStockById(String id) {
        var model = filmRepository.findFilmStockById(Integer.valueOf(id));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + id + "'");
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
    public FilmModel updateFilm(String id, FilmRequestModel model) {
        var entity = filmRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Film not found with id '" + id + "'"));
        entity.update(filmMapper.mapToEntity(model));
        return filmMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deleteFilmById(String id) {
        var entity = filmRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Film not found with id '" + id + "'"));
        filmRepository.deleteById(entity.getFilmId());
    }
}
