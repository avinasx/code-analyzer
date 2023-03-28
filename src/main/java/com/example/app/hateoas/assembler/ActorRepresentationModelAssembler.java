package com.example.app.hateoas.assembler;

import com.example.app.controller.ActorController;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.response.ActorResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
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
        model.add(linkTo(methodOn(ActorController.class).getActor(String.valueOf(entity.getActorId())))
                .withSelfRel().withType(HttpMethod.GET.name()).withTitle("Get actor"));
        model.add(linkTo(methodOn(ActorController.class).updateActor(String.valueOf(entity.getActorId()), null))
                .withRel("update").withType(HttpMethod.PUT.name()).withTitle("Update actor"));
        model.add(linkTo(methodOn(ActorController.class).deleteActor(String.valueOf(entity.getActorId())))
                .withRel("delete").withType(HttpMethod.DELETE.name()).withTitle("Delete actor"));
        model.add(linkTo(methodOn(ActorController.class).getActorDetail(String.valueOf(entity.getActorId())))
                .withRel("details").withType(HttpMethod.GET.name()).withTitle("Get details of actor"));
        model.add(linkTo(methodOn(ActorController.class).addActor(null))
                .withRel("add").withType(HttpMethod.POST.name()).withTitle("Add actor"));
        model.add(linkTo(methodOn(ActorController.class).getActors())
                .withRel("actors").withType(HttpMethod.GET.name()).withTitle("Get actors"));
        return model;
    }

    @Override
    @NonNull
    public CollectionModel<ActorResponseModel> toCollectionModel(@NonNull Iterable<? extends ActorModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(ActorController.class).getActors())
                .withSelfRel().withType(HttpMethod.GET.name()).withTitle("Get actors"));
        return collectionModel;
    }
}
