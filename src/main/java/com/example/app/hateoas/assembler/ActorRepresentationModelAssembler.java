package com.example.app.hateoas.assembler;

import com.example.app.controller.ActorController;
import com.example.app.model.entity.ActorEntity;
import com.example.app.model.response.ActorResponseModel;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorRepresentationModelAssembler extends RepresentationModelAssemblerSupport<ActorEntity, ActorResponseModel> {
    public ActorRepresentationModelAssembler() {
        super(ActorController.class, ActorResponseModel.class);
    }

    @NotNull
    @Override
    public ActorResponseModel toModel(@NonNull ActorEntity entity) {
        var model = instantiateModel(entity);
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(ActorController.class).getActor(String.valueOf(model.getActorId()))).withSelfRel());
        model.add(linkTo(methodOn(ActorController.class).getAllActors()).withRel("actors"));
        return model;
    }
}
