package com.example.app.repository.custom;

import com.blazebit.persistence.querydsl.BlazeJPAQuery;
import com.blazebit.persistence.querydsl.BlazeJPAQueryFactory;
import com.blazebit.persistence.querydsl.JPQLNextExpressions;
import com.example.app.model.entity.ActorEntity;
import com.example.app.model.entity.QActorEntity;
import com.example.app.model.entity.QFilmActorEntity;
import com.example.app.model.entity.QFilmEntity;
import com.example.app.model.internal.ActorDetailModel;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomActorRepositoryImpl implements CustomActorRepository {
    private JPAQueryFactory jpaQueryFactory;
    private BlazeJPAQueryFactory blazeJPAQueryFactory;

    @Override
    public List<ActorDetailModel> findAllActorsDetail() {
        var query = findActorDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<ActorDetailModel> findActorDetailById(Integer id) {
        var query = findActorDetail(id);
        return Optional.ofNullable(query.fetchFirst());
    }

    private BlazeJPAQuery<ActorDetailModel> findActorDetail(Integer id) {
        var actor = QActorEntity.actorEntity;
        var film = QFilmEntity.filmEntity;
        var filmActor = QFilmActorEntity.filmActorEntity;

        var query = blazeJPAQueryFactory
                .select(Projections.constructor(ActorDetailModel.class,
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
    public List<ActorEntity> findActorByName(String name) {
        var actor = QActorEntity.actorEntity;
        var query = jpaQueryFactory
                .selectFrom(actor)
                .where((actor.fullName.firstName.contains(name.toUpperCase())
                        .or(actor.fullName.lastName.contains(name.toUpperCase()))));
        return query.fetch();
    }
}
