package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.FilmEntity;
import com.example.app.model.internal.FilmDetailModel;
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
    public Optional<FilmEntity> getFilmById(Integer id) {
        return filmRepository.findById(id);
    }

    @Override
    public List<FilmDetailModel> getAllFilmsDetail() {
        return filmRepository.findAllFilmsDetail();
    }

    @Override
    public Optional<FilmDetailModel> getFilmDetailById(Integer id) {
        return filmRepository.findFilmDetailById(id);
    }

    @Override
    public Optional<FilmEntity> getFilmStockById(Integer id) {
        return filmRepository.findFilmStockById(id);
    }

    @Override
    public FilmEntity addFilm(FilmEntity entity) {
        return filmRepository.save(entity);
    }

    @Override
    public FilmEntity updateFilm(FilmEntity entity) {
        var id = entity.getFilmId();
        var resource = filmRepository.findById(id);
        if (resource.isPresent()) {
            return filmRepository.save(entity);
        } else {
            throw new ResourceNotFoundException("Film not found with id '" + id + "'");
        }
    }

    @Override
    public void deleteFilmById(Integer id) {
        filmRepository.deleteById(id);
    }
}
