package com.example.app.hateoas.assembler;

import com.example.app.controller.ActorController;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.response.ActorResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ActorModel, ActorResponseModel> {
    public ActorRepresentationModelAssembler() {
        super(ActorController.class, ActorResponseModel.class);
    }

    @Override
    @NonNull
    public ActorResponseModel toModel(@NonNull ActorModel entity) {
        var model = instantiateModel(entity);
        model.setActorModel(entity);
        model.add(linkTo(methodOn(ActorController.class).getActor(String.valueOf(entity.getActorId()))).withSelfRel());
        model.add(linkTo(methodOn(ActorController.class).getActorDetail(String.valueOf(entity.getActorId()))).withRel("actorDetails"));
        model.add(linkTo(methodOn(ActorController.class).getActors()).withRel("actors"));
        return model;
    }

    @Override
    @NonNull
    public CollectionModel<ActorResponseModel> toCollectionModel(@NonNull Iterable<? extends ActorModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ActorController.class).getActors()).withSelfRel());
        return collectionModel;
    }
}
