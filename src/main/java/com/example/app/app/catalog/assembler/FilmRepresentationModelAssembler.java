package com.example.app.app.catalog.assembler;

import com.example.app.app.catalog.controller.FilmController;
import com.example.app.app.catalog.domain.dto.FilmDto;
import com.example.app.common.constant.HalRelation;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FilmRepresentationModelAssembler extends RepresentationModelAssemblerSupport<FilmDto.Film, FilmDto.FilmResponse> {
    public FilmRepresentationModelAssembler() {
        super(FilmController.class, FilmDto.FilmResponse.class);
    }

    @Override
    @lombok.NonNull
    public FilmDto.FilmResponse toModel(@lombok.NonNull FilmDto.Film entity) {
        var model = instantiateModel(entity);
        model.setFilm(entity);
        model.add(linkTo(methodOn(FilmController.class).getFilm(entity.getFilmId())).withSelfRel());
        model.add(linkTo(methodOn(FilmController.class).getFilmList(null, null, Pageable.unpaged())).withRel(HalRelation.Fields.filmList));
        return model;
    }

    @Override
    @lombok.NonNull
    public CollectionModel<FilmDto.FilmResponse> toCollectionModel(@lombok.NonNull Iterable<? extends FilmDto.Film> entities) {
        var collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(FilmController.class).getFilmList(null, null, Pageable.unpaged())).withSelfRel());
        return collectionModel;
    }
}
