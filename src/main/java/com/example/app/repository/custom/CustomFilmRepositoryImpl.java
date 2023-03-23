package com.example.app.repository.custom;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.BlazeJPAQueryFactory;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.app.model.entity.QActorEntity;
import com.example.app.model.entity.QFilmActorEntity;
import com.example.app.model.entity.QFilmCategoryEntity;
import com.example.app.model.entity.QFilmEntity;
import com.example.app.model.internal.FilmDetailModel;
import com.example.app.model.internal.FilmModel;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
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
                                Expressions.asString(actor.fullName.firstName).concat(" ")
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
    public Optional<FilmModel> findFilmStockById(Integer id) {
        return Optional.empty();
    }
}
