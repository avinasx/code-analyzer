package com.example.app.repository.custom;

import com.example.app.model.internal.FilmDetailModel;
import com.example.app.model.internal.FilmModel;

import java.util.List;
import java.util.Optional;

public interface CustomFilmRepository {
    List<FilmDetailModel> findAllFilmsDetail();

    Optional<FilmDetailModel> findFilmDetailById(Integer id);

    Optional<FilmModel> findFilmStockById(Integer id);
}
