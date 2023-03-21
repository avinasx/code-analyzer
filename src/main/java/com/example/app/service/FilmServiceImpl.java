package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.FilmDetailModel;
import com.example.app.model.internal.FilmModel;
import com.example.app.model.mapping.mapper.FilmMapper;
import com.example.app.model.request.FilmRequestModel;
import com.example.app.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    @Override
    public List<FilmModel> getAllFilms() {
        var result = filmRepository.findAll();
        return filmMapper.mapToDtoList(result);
    }

    @Override
    public Optional<FilmModel> getFilmById(String id) {
        var result = filmRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Film not found with id '" + id + "'"));
        return Optional.of(filmMapper.mapToDto(entity));
    }

    @Override
    public List<FilmDetailModel> getAllFilmsDetail() {
        return filmRepository.findAllFilmsDetail();
    }

    @Override
    public Optional<FilmDetailModel> getFilmDetailById(String id) {
        var result = filmRepository.findFilmDetailById(Integer.valueOf(id));
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + id + "'");
        }
        return result;
    }

    @Override
    public Optional<FilmModel> getFilmStockById(String id) {
        var result = filmRepository.findFilmStockById(Integer.valueOf(id));
        if (result.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + id + "'");
        }
        return result;
    }

    @Override
    public FilmModel addFilm(FilmRequestModel model) {
        var entity = filmMapper.mapToEntity(model);
        var result = filmRepository.save(entity);
        return filmMapper.mapToDto(result);
    }

    @Override
    public FilmModel updateFilm(String id, FilmRequestModel model) {
        var result = filmRepository.findById(Integer.valueOf(id));
        var entity = result.orElseThrow(() ->
                new ResourceNotFoundException("Film not found with id '" + id + "'"));
        filmMapper.updateEntity(model, entity);
        var updated = filmRepository.save(entity);
        return filmMapper.mapToDto(updated);
    }

    @Override
    public void deleteFilmById(String id) {
        filmRepository.deleteById(Integer.valueOf(id));
    }
}
