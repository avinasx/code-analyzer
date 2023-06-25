package com.example.app.app.catalog.repository.custom;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.BlazeJPAQueryFactory;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.app.app.catalog.domain.dto.ActorDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import com.example.app.app.catalog.domain.entity.QActorEntity;
import com.example.app.app.catalog.domain.entity.QFilmActorEntity;
import com.example.app.app.catalog.domain.entity.QFilmCategoryEntity;
import com.example.app.app.catalog.domain.entity.QFilmEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.app.common.repository.ExpressionUtils.filterEquals;

@Repository
@RequiredArgsConstructor
public class CustomActorRepositoryImpl implements CustomActorRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final BlazeJPAQueryFactory blazeJPAQueryFactory;

    @Override
    public List<ActorDetailsDto.ActorDetails> findAllActorDetailsList() {
        var query = findActorDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<ActorDetailsDto.ActorDetails> findActorDetailsById(Integer actorId) {
        var query = findActorDetail(actorId);
        return Optional.of(query.fetchFirst());
    }

    private BlazeJPAQuery<ActorDetailsDto.ActorDetails> findActorDetail(Integer id) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;

        return blazeJPAQueryFactory
                .select(Projections.constructor(ActorDetailsDto.ActorDetails.class,
                        actor.actorId.as(ActorDetailsDto.ActorDetails.Fields.actorId),
                        actor.fullName.firstName.as(ActorDetailsDto.ActorDetails.Fields.firstName),
                        actor.fullName.lastName.as(ActorDetailsDto.ActorDetails.Fields.lastName),
                        JPQLNextExpressions.groupConcat(film.title, ", ").as(ActorDetailsDto.ActorDetails.Fields.filmInfo)))
                .from(actor)
                .leftJoin(filmActor).on(filmActor.actorId.eq(actor.actorId))
                .leftJoin(film).on(filmActor.filmId.eq(film.filmId))
                .where(filterEquals(actor.actorId, id));
    }

    @Override
    public List<FilmDto.Film> findAllActorFilmListByIdWithCondition(Integer actorId, FilmDto.Film condition, Pageable pageable) {
        var query = findActorFilm(actorId, null, condition, pageable);
        return query.fetch();
    }

    @Override
    public Optional<FilmDto.Film> findActorFilmById(Integer actorId, Integer filmId) {
        var query = findActorFilm(actorId, filmId, null, null);
        return Optional.of(query.fetchFirst());
    }

    private JPAQuery<FilmDto.Film> findActorFilm(Integer actorId, Integer filmId, FilmDto.Film condition, Pageable pageable) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;

        return jpaQueryFactory
                .select(Projections.constructor(FilmDto.Film.class))
                .from(actor)
                .leftJoin(filmActor).on(filmActor.actorId.eq(actor.actorId))
                .leftJoin(film).on(filmActor.filmId.eq(film.filmId))
                .where(actor.actorId.eq(actorId))
                .where(filterEquals(film.filmId, filmId))
                .where(filterEquals(film.releaseYear.year(), condition.getReleaseYear().getYear()))
                .where(filterEquals(film.rating, condition.getRating()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
    }

    @Override
    public Optional<FilmDetailsDto.FilmDetails> findActorFilmDetailsById(Integer actorId, Integer filmId) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;
        var filmCategory = QFilmCategoryEntity.filmCategoryEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(FilmDetailsDto.FilmDetails.class,
                        film.filmId.as(FilmDetailsDto.FilmDetails.Fields.filmId),
                        film.title.as(FilmDetailsDto.FilmDetails.Fields.title),
                        film.description.as(FilmDetailsDto.FilmDetails.Fields.description),
                        filmCategory.categoryId.as(FilmDetailsDto.FilmDetails.Fields.category),
                        film.rentalRate.as(FilmDetailsDto.FilmDetails.Fields.price),
                        film.length.as(FilmDetailsDto.FilmDetails.Fields.length),
                        film.rating.as(FilmDetailsDto.FilmDetails.Fields.rating),
                        JPQLNextExpressions.groupConcat(actor.fullName.firstName.concat(" ")
                                .concat(actor.fullName.lastName), ", ").as(FilmDetailsDto.FilmDetails.Fields.actors)))
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
    public Optional<FilmDto.Film> addActorFilm(Integer actorId, Integer filmId) {
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
