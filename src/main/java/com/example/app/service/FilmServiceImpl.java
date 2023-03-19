package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.FilmEntity;
import com.example.app.model.internal.FilmDetailModel;
import com.example.app.model.mapping.CopyUtils;
import com.example.app.model.request.FilmRequestModel;
import com.example.app.repository.FilmRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FilmServiceImpl implements FilmService {
    private FilmRepository filmRepository;

    @Override
    public List<FilmEntity> getAllFilms() {
        return filmRepository.findAll();
    }

    @Override
    public Optional<FilmEntity> getFilmById(String id) {
        return filmRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<FilmDetailModel> getAllFilmsDetail() {
        return filmRepository.findAllFilmsDetail();
    }

    @Override
    public Optional<FilmDetailModel> getFilmDetailById(String id) {
        return filmRepository.findFilmDetailById(Integer.valueOf(id));
    }

    @Override
    public Optional<FilmEntity> getFilmStockById(String id) {
        return filmRepository.findFilmStockById(Integer.valueOf(id));
    }

    @Override
    public FilmEntity addFilm(FilmRequestModel model) {
        var entity = new FilmEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return filmRepository.save(entity);
    }

    @Override
    public FilmEntity updateFilm(String id, FilmRequestModel model) {
        var resource = filmRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("Film not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return filmRepository.save(entity);
    }

    @Override
    public void deleteFilmById(String id) {
        filmRepository.deleteById(Integer.valueOf(id));
    }
}
