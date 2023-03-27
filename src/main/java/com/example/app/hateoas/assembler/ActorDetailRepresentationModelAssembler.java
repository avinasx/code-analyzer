package com.example.app.hateoas.assembler;

import com.example.app.controller.ActorController;
import com.example.app.model.internal.extra.ActorDetailModel;
import com.example.app.model.response.ActorDetailResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ActorDetailModel, ActorDetailResponseModel> {
    public ActorDetailRepresentationModelAssembler() {
        super(ActorController.class, ActorDetailResponseModel.class);
    }

    @Override
    @NonNull
    public ActorDetailResponseModel toModel(@NonNull ActorDetailModel entity) {
        var model = instantiateModel(entity);
        model.setActorDetailModel(entity);
        model.add(linkTo(methodOn(ActorController.class).getActorDetail(String.valueOf(entity.getActorId()))).withSelfRel());
        model.add(linkTo(methodOn(ActorController.class).getActor(String.valueOf(entity.getActorId()))).withRel("actor"));
        model.add(linkTo(methodOn(ActorController.class).getActors()).withRel("actors"));
        return model;
    }

    @Override
    @NonNull
    public CollectionModel<ActorDetailResponseModel> toCollectionModel(@NonNull Iterable<? extends ActorDetailModel> entities) {
        return super.toCollectionModel(entities);
    }
}
