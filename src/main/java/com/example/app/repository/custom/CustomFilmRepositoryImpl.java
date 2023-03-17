package com.example.app.repository.custom;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.BlazeJPAQueryFactory;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.app.model.entity.*;
import com.example.app.model.internal.FilmDetailModel;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomFilmRepositoryImpl implements CustomFilmRepository {
    private BlazeJPAQueryFactory blazeJPAQueryFactory;

    @Override
    public List<FilmDetailModel> findAllFilmsDetail() {
        var query = findFilmDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<FilmDetailModel> findFilmDetailById(Integer id) {
        var query = findFilmDetail(id);
        return Optional.ofNullable(query.fetchFirst());
    }

    private BlazeJPAQuery<FilmDetailModel> findFilmDetail(Integer id) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;
        var filmCategory = QFilmCategoryEntity.filmCategoryEntity;

        var query = blazeJPAQueryFactory
                .select(Projections.constructor(FilmDetailModel.class,
                        film.filmId.as("fid"),
                        film.title.as("title"),
                        film.description.as("description"),
                        filmCategory.categoryId.as("category"),
                        film.rentalRate.as("price"),
                        film.length.as("length"),
                        film.rating.as("rating"),
                        JPQLNextExpressions.groupConcat(
                                Expressions.asString(actor.firstName).concat(" ").concat(actor.lastName),
                                ", ").as("actors")))
                .from(film)
                .leftJoin(filmCategory).on(filmCategory.filmId.eq(film.filmId))
                .leftJoin(filmActor).on(filmActor.filmId.eq(film.filmId))
                .leftJoin(actor).on(actor.actorId.eq(filmActor.actorId))
                .groupBy(film.filmId, filmCategory.categoryId);
        if (Objects.nonNull(id)) {
            query.where(film.filmId.eq(id));
        }
        return query;
    }

    @Override
    public Optional<FilmEntity> findFilmStockById(Integer id) {
        return Optional.empty();
    }
}
