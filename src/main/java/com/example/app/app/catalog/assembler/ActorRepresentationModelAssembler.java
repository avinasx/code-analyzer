package com.example.app.app.catalog.assembler;

import com.example.app.app.catalog.controller.ActorController;
import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ActorDto.Actor, ActorDto.ActorResponse> {
    public ActorRepresentationModelAssembler() {
        super(ActorController.class, ActorDto.ActorResponse.class);
    }

    @Override
    @lombok.NonNull
    public ActorDto.ActorResponse toModel(@lombok.NonNull ActorDto.Actor entity) {
        final var model = instantiateModel(entity);
        model.setActor(entity);
        model.add(linkTo(methodOn(ActorController.class).getActor(entity.getActorId()))
                .withSelfRel().withType(HttpMethod.GET.name()).withTitle("Get actor"));
        model.add(linkTo(methodOn(ActorController.class).updateActor(entity.getActorId(), null))
                .withRel(HalRelation.Fields.update).withType(HttpMethod.PUT.name()).withTitle("Update actor"));
        model.add(linkTo(methodOn(ActorController.class).deleteActor(entity.getActorId()))
                .withRel(HalRelation.Fields.delete).withType(HttpMethod.DELETE.name()).withTitle("Delete actor"));
        model.add(linkTo(methodOn(ActorController.class).getActorDetails(entity.getActorId()))
                .withRel(HalRelation.Fields.details).withType(HttpMethod.GET.name()).withTitle("Get details of actor"));
        model.add(linkTo(methodOn(ActorController.class).addActor(null))
                .withRel(HalRelation.Fields.create).withType(HttpMethod.POST.name()).withTitle("Add actor"));
        model.add(linkTo(methodOn(ActorController.class).getActorList(Pageable.unpaged()))
                .withRel(HalRelation.Fields.actorList).withType(HttpMethod.GET.name()).withTitle("Get actors"));
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<ActorDto.ActorResponse> toCollectionModel(@lombok.NonNull Iterable<? extends ActorDto.Actor> entities) {
        final var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ActorController.class).getActorList(Pageable.unpaged()))
                .withSelfRel().withType(HttpMethod.GET.name()).withTitle("Get actors"));
        return collectionModel;
    }
}
