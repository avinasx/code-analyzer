package com.example.app.app.catalog.assembler;

import com.example.app.app.catalog.controller.ActorController;
import com.example.app.app.catalog.domain.dto.ActorDetailsDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorDetailsRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ActorDetailsDto.ActorDetails, ActorDetailsDto.ActorDetailsResponse> {
    public ActorDetailsRepresentationModelAssembler() {
        super(ActorController.class, ActorDetailsDto.ActorDetailsResponse.class);
    }

    @Override
    @lombok.NonNull
    public ActorDetailsDto.ActorDetailsResponse toModel(@lombok.NonNull ActorDetailsDto.ActorDetails entity) {
        var model = instantiateModel(entity);
        model.setActorDetailsModel(entity);
        model.add(linkTo(methodOn(ActorController.class).getActorDetails(String.valueOf(entity.getActorId()))).withSelfRel());
        model.add(linkTo(methodOn(ActorController.class).getActor(String.valueOf(entity.getActorId()))).withRel(HalRelation.Fields.actor));
        model.add(linkTo(methodOn(ActorController.class).getActorList()).withRel(HalRelation.Fields.actorList));
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<ActorDetailsDto.ActorDetailsResponse> toCollectionModel(@lombok.NonNull Iterable<? extends ActorDetailsDto.ActorDetails> entities) {
        return super.toCollectionModel(entities);
    }
}
