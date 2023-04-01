package com.example.app.hateoas.assembler;

import com.example.app.controller.ActorController;
import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.extra.ActorDetailsModel;
import com.example.app.model.response.ActorDetailResponseModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorDetailsRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ActorDetailsModel, ActorDetailResponseModel> {
    public ActorDetailsRepresentationModelAssembler() {
        super(ActorController.class, ActorDetailResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public ActorDetailResponseModel toModel(@lombok.NonNull ActorDetailsModel entity) {
        var model = instantiateModel(entity);
        model.setActorDetailsModel(entity);
        model.add(linkTo(methodOn(ActorController.class).getActorDetails(String.valueOf(entity.getActorId()))).withSelfRel());
        model.add(linkTo(methodOn(ActorController.class).getActor(String.valueOf(entity.getActorId()))).withRel(HalRelation.Fields.actor));
        model.add(linkTo(methodOn(ActorController.class).getActorList()).withRel(HalRelation.Fields.actorList));
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<ActorDetailResponseModel> toCollectionModel(@lombok.NonNull Iterable<? extends ActorDetailsModel> entities) {
        return super.toCollectionModel(entities);
    }
}
