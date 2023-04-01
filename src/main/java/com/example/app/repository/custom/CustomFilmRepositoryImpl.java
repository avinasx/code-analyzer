package com.example.app.repository.custom;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.BlazeJPAQueryFactory;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.app.model.constant.FilmRating;
import com.example.app.model.entity.QActorEntity;
import com.example.app.model.entity.QFilmActorEntity;
import com.example.app.model.entity.QFilmCategoryEntity;
import com.example.app.model.entity.QFilmEntity;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.internal.extra.FilmDetailsModel;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomFilmRepositoryImpl implements CustomFilmRepository {
    private JPAQueryFactory jpaQueryFactory;
    private BlazeJPAQueryFactory blazeJPAQueryFactory;

    @Override
    public List<FilmModel> findAllWithFilter(LocalDate releaseYear, FilmRating rating) {
        var film = QFilmEntity.filmEntity;
        var query = jpaQueryFactory
                .select(Projections.constructor(FilmModel.class))
                .from(film);
        if (releaseYear != null) {
            query.where(film.releaseYear.eq(releaseYear));
        }
        if (rating != null) {
            query.where(film.rating.eq(rating));
        }
        return query.fetch();
    }

    @Override
    public List<ActorModel> findAllFilmActorListById(Integer filmId) {
        var query = findFilmActor(filmId, null);
        return query.fetch();
    }

    @Override
    public Optional<ActorModel> findFilmActorById(Integer filmId, Integer actorId) {
        var query = findFilmActor(filmId, actorId);
        return Optional.of(query.fetchFirst());
    }

    private JPAQuery<ActorModel> findFilmActor(Integer filmId, Integer actorId) {
        var actor = QActorEntity.actorEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;
        var film = QFilmEntity.filmEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(ActorModel.class,
                        actor.actorId.as("actorId"),
                        actor.fullName.firstName.as("firstName"),
                        actor.fullName.lastName.as("lastName")))
                .from(film)
                .leftJoin(filmActor).on(filmActor.filmId.eq(film.filmId))
                .leftJoin(actor).on(actor.actorId.eq(filmActor.actorId))
                .where(film.filmId.eq(filmId));
        if (actorId != null) {
            query.where(actor.actorId.eq(actorId));
        }
        return query;
    }

    @Override
    public List<FilmDetailsModel> findAllFilmListDetail() {
        var query = findFilmDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<FilmDetailsModel> findFilmDetailsById(Integer filmId) {
        var query = findFilmDetail(filmId);
        return Optional.of(query.fetchFirst());
    }

    private BlazeJPAQuery<FilmDetailsModel> findFilmDetail(Integer id) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;
        var filmCategory = QFilmCategoryEntity.filmCategoryEntity;

        var query = blazeJPAQueryFactory
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
                .from(film)
                .leftJoin(filmCategory).on(filmCategory.filmId.eq(film.filmId))
                .leftJoin(filmActor).on(filmActor.filmId.eq(film.filmId))
                .leftJoin(actor).on(actor.actorId.eq(filmActor.actorId))
                .groupBy(film.filmId, filmCategory.categoryId);
        if (id != null) {
            query.where(film.filmId.eq(id));
        }
        return query;
    }

    @Override
    public Optional<FilmModel> findFilmStockById(Integer filmId) {
        return Optional.empty();
    }
}
