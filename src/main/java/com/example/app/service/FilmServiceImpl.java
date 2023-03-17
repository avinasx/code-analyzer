package com.example.app.service;

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
    public void addFilm(FilmEntity entity) {
        filmRepository.save(entity);
    }

    @Override
    public void updateFilm(FilmEntity entity) {
        filmRepository.save(entity);
    }

    @Override
    public void deleteFilmById(Integer id) {
        filmRepository.deleteById(id);
    }
}
