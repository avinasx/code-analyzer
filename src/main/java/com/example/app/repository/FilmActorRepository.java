package com.example.app.repository;

import com.example.app.model.entity.FilmActorEntity;
import com.example.app.repository.custom.CustomFilmActorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActorEntity, Integer>, CustomFilmActorRepository {
}
