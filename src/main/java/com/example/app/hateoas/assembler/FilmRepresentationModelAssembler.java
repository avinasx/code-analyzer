package com.example.app.hateoas.assembler;

import com.example.app.controller.FilmController;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.response.FilmResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FilmRepresentationModelAssembler extends RepresentationModelAssemblerSupport<FilmModel, FilmResponseModel> {
    public FilmRepresentationModelAssembler() {
        super(FilmController.class, FilmResponseModel.class);
    }

    @Override
    @NonNull
    public FilmResponseModel toModel(@NonNull FilmModel entity) {
        var model = instantiateModel(entity);
        model.setFilmModel(entity);
        model.add(linkTo(methodOn(FilmController.class).getFilm(String.valueOf(entity.getFilmId()))).withSelfRel());
        model.add(linkTo(methodOn(FilmController.class).getFilms()).withRel("films"));
        return model;
    }

    @NonNull
    @Override
    public CollectionModel<FilmResponseModel> toCollectionModel(@NonNull Iterable<? extends FilmModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(FilmController.class).getFilms()).withSelfRel());
        return collectionModel;
    }
}
