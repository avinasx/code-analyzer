package com.example.app.app.catalog.repository.custom;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.BlazeJPAQueryFactory;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import com.example.app.app.catalog.domain.entity.QActorEntity;
import com.example.app.app.catalog.domain.entity.QFilmActorEntity;
import com.example.app.app.catalog.domain.entity.QFilmCategoryEntity;
import com.example.app.app.catalog.domain.entity.QFilmEntity;
import com.example.app.app.catalog.domain.mapper.FilmMapper;
import com.example.app.common.constant.FilmRating;
import com.example.app.common.domain.dto.FullName;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.app.common.repository.ExpressionUtils.filterEquals;

@Repository
@RequiredArgsConstructor
public class CustomFilmRepositoryImpl implements CustomFilmRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final BlazeJPAQueryFactory blazeJPAQueryFactory;
    private final FilmMapper filmMapper;

    @Override
    public List<FilmDto.Film> findAllFilmList(Pageable pageable) {
        var film = QFilmEntity.filmEntity;
        var query = jpaQueryFactory
                .selectFrom(film)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        return query.fetch().stream().map(filmMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<FilmDto.Film> findAllFilmListWithFilter(Year releaseYear, FilmRating rating, Pageable pageable) {
        var film = QFilmEntity.filmEntity;
        var query = jpaQueryFactory
                .selectFrom(film)
                .where(filterEquals(film.releaseYear.year(), releaseYear.getValue()))
                .where(filterEquals(film.rating, rating))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());
        return query.fetch().stream().map(filmMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ActorDto.Actor> findAllFilmActorListById(Integer filmId) {
        var query = findFilmActor(filmId, null);
        return query.fetch();
    }

    @Override
    public Optional<ActorDto.Actor> findFilmActorById(Integer filmId, Integer actorId) {
        var query = findFilmActor(filmId, actorId);
        return Optional.of(query.fetchFirst());
    }

    private JPAQuery<ActorDto.Actor> findFilmActor(Integer filmId, Integer actorId) {
        var actor = QActorEntity.actorEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;
        var film = QFilmEntity.filmEntity;

        return jpaQueryFactory
                .select(Projections.constructor(ActorDto.Actor.class,
                        actor.actorId.as(ActorDto.Actor.Fields.actorId),
                        actor.fullName.firstName.as(FullName.Fields.firstName),
                        actor.fullName.lastName.as(FullName.Fields.lastName)))
                .from(film)
                .leftJoin(filmActor).on(filmActor.filmId.eq(film.filmId))
                .leftJoin(actor).on(actor.actorId.eq(filmActor.actorId))
                .where(film.filmId.eq(filmId))
                .where(filterEquals(actor.actorId, actorId));
    }

    @Override
    public List<FilmDetailsDto.FilmDetails> findAllFilmListDetail() {
        var query = findFilmDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<FilmDetailsDto.FilmDetails> findFilmDetailsById(Integer filmId) {
        var query = findFilmDetail(filmId);
        return Optional.of(query.fetchFirst());
    }

    private BlazeJPAQuery<FilmDetailsDto.FilmDetails> findFilmDetail(Integer id) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;
        var filmCategory = QFilmCategoryEntity.filmCategoryEntity;

        return blazeJPAQueryFactory
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
                .from(film)
                .leftJoin(filmCategory).on(filmCategory.filmId.eq(film.filmId))
                .leftJoin(filmActor).on(filmActor.filmId.eq(film.filmId))
                .leftJoin(actor).on(actor.actorId.eq(filmActor.actorId))
                .where(filterEquals(film.filmId, id))
                .groupBy(film.filmId, filmCategory.categoryId);
    }

    @Override
    public Optional<FilmDto.Film> findFilmStockById(Integer filmId) {
        return Optional.empty();
    }
}
