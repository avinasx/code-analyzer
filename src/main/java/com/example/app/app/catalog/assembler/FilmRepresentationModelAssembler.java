package com.example.app.app.catalog.assembler;

import com.example.app.app.catalog.controller.FilmController;
import com.example.app.app.catalog.domain.dto.FilmModel;
import com.example.app.app.catalog.domain.dto.FilmResponseModel;
import com.example.app.common.constant.HalRelation;
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
    @lombok.NonNull
    public FilmResponseModel toModel(@lombok.NonNull FilmModel entity) {
        var model = instantiateModel(entity);
        model.setFilmModel(entity);
        model.add(linkTo(methodOn(FilmController.class).getFilm(String.valueOf(entity.getFilmId()))).withSelfRel());
        model.add(linkTo(methodOn(FilmController.class).getFilmList(null, null, null)).withRel(HalRelation.Fields.filmList));
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<FilmResponseModel> toCollectionModel(@lombok.NonNull Iterable<? extends FilmModel> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(FilmController.class).getFilmList(null, null, null)).withSelfRel());
        return collectionModel;
    }
}
