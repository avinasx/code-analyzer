package com.example.app.app.catalog.repository;

import com.example.app.app.catalog.domain.entity.FilmActorEntity;
import com.example.app.app.catalog.repository.custom.CustomFilmActorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActorEntity, Integer>, CustomFilmActorRepository {
}
