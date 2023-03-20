package com.example.app.hateoas.assembler;

import com.example.app.controller.ActorController;
import com.example.app.model.internal.ActorDetailModel;
import com.example.app.model.response.ActorDetailResponseModel;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ActorDetailModel, ActorDetailResponseModel> {
    public ActorDetailRepresentationModelAssembler() {
        super(ActorController.class, ActorDetailResponseModel.class);
    }

    @NonNull
    @Override
    public ActorDetailResponseModel toModel(@NonNull ActorDetailModel entity) {
        var model = instantiateModel(entity);
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(ActorController.class).getActorDetail(String.valueOf(model.getActorId()))).withSelfRel());
        model.add(linkTo(methodOn(ActorController.class).getActor(String.valueOf(model.getActorId()))).withRel("actor"));
        model.add(linkTo(methodOn(ActorController.class).getAllActors()).withRel("actors"));
        return model;
    }
}
