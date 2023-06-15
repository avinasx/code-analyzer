package com.example.app.app.catalog.repository.custom;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.BlazeJPAQueryFactory;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.app.app.catalog.domain.dto.ActorDetailsModel;
import com.example.app.app.catalog.domain.dto.FilmDetailsModel;
import com.example.app.app.catalog.domain.dto.FilmModel;
import com.example.app.app.catalog.domain.entity.QActorEntity;
import com.example.app.app.catalog.domain.entity.QFilmActorEntity;
import com.example.app.app.catalog.domain.entity.QFilmCategoryEntity;
import com.example.app.app.catalog.domain.entity.QFilmEntity;
import com.example.app.common.constant.FilmRating;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomActorRepositoryImpl implements CustomActorRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final BlazeJPAQueryFactory blazeJPAQueryFactory;

    @Override
    public List<ActorDetailsModel> findAllActorDetailsList() {
        var query = findActorDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<ActorDetailsModel> findActorDetailsById(Integer actorId) {
        var query = findActorDetail(actorId);
        return Optional.of(query.fetchFirst());
    }

    private BlazeJPAQuery<ActorDetailsModel> findActorDetail(Integer id) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;

        var query = blazeJPAQueryFactory
                .select(Projections.constructor(ActorDetailsModel.class,
                        actor.actorId.as("actorId"),
                        actor.fullName.firstName.as("firstName"),
                        actor.fullName.lastName.as("lastName"),
                        JPQLNextExpressions.groupConcat(film.title, ", ").as("filmTitle")))
                .from(actor)
                .leftJoin(filmActor).on(filmActor.actorId.eq(actor.actorId))
                .leftJoin(film).on(filmActor.filmId.eq(film.filmId));
        if (id != null) {
            query.where(actor.actorId.eq(id));
        }
        return query;
    }

    @Override
    public List<FilmModel> findAllActorFilmListById(Integer actorId) {
        var query = findActorFilm(actorId, null, null, null);
        return query.fetch();
    }

    @Override
    public List<FilmModel> findAllActorFilmListByIdWithFilter(Integer actorId, LocalDate releaseYear, FilmRating rating) {
        var query = findActorFilm(actorId, null, releaseYear, rating);
        return query.fetch();
    }

    @Override
    public Optional<FilmModel> findActorFilmById(Integer actorId, Integer filmId) {
        var query = findActorFilm(actorId, filmId, null, null);
        return Optional.of(query.fetchFirst());
    }

    private JPAQuery<FilmModel> findActorFilm(Integer actorId, Integer filmId, LocalDate releaseYear, FilmRating rating) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(FilmModel.class))
                .from(actor)
                .leftJoin(filmActor).on(filmActor.actorId.eq(actor.actorId))
                .leftJoin(film).on(filmActor.filmId.eq(film.filmId))
                .where(actor.actorId.eq(actorId));
        if (filmId != null) {
            query.where(film.filmId.eq(filmId));
        }
        if (releaseYear != null) {
            query.where(film.releaseYear.eq(releaseYear));
        }
        if (rating != null) {
            query.where(film.rating.eq(rating));
        }
        return query;
    }

    @Override
    public Optional<FilmDetailsModel> findActorFilmDetailsById(Integer actorId, Integer filmId) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;
        var filmCategory = QFilmCategoryEntity.filmCategoryEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(FilmDetailsModel.class,
                        film.filmId.as("filmId"),
                        film.title.as("title"),
                        film.description.as("description"),
                        filmCategory.categoryId.as("category"),
                        film.rentalRate.as("price"),
                        film.length.as("length"),
                        film.rating.as("rating"),
                        JPQLNextExpressions.groupConcat(actor.fullName.firstName.concat(" ")
                                .concat(actor.fullName.lastName), ", ").as("actors")))
                .from(actor)
                .leftJoin(filmActor).on(filmActor.actorId.eq(actor.actorId))
                .leftJoin(film).on(film.filmId.eq(filmActor.filmId))
                .leftJoin(filmCategory).on(filmCategory.filmId.eq(film.filmId))
                .where(actor.actorId.eq(actorId))
                .where(film.filmId.eq(filmId))
                .groupBy(film.filmId, filmCategory.categoryId);
        return Optional.of(query.fetchFirst());
    }

    @Override

    public Optional<FilmModel> addActorFilm(Integer actorId, Integer filmId) {
        var filmActor = QFilmActorEntity.filmActorEntity;
        var query = jpaQueryFactory
                .insert(filmActor)
                .columns(filmActor.actorId, filmActor.filmId)
                .values(actorId, filmId);
        query.execute();
        return findActorFilmById(actorId, filmId);
    }

    @Override
    public void removeActorFilm(Integer actorId, Integer filmId) {
        var filmActor = QFilmActorEntity.filmActorEntity;
        var query = jpaQueryFactory
                .delete(filmActor)
                .where(filmActor.actorId.eq(actorId))
                .where(filmActor.filmId.eq(filmId));
        query.execute();
    }
}
