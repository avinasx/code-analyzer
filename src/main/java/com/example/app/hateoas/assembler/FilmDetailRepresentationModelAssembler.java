package com.example.app.hateoas.assembler;

import com.example.app.controller.FilmController;
import com.example.app.model.internal.extra.FilmDetailModel;
import com.example.app.model.response.FilmDetailResponseModel;
import lombok.NonNull;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FilmDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<FilmDetailModel, FilmDetailResponseModel> {
    public FilmDetailRepresentationModelAssembler() {
        super(FilmDetailModel.class, FilmDetailResponseModel.class);
    }

    @Override
    @NonNull
    public FilmDetailResponseModel toModel(@NonNull FilmDetailModel entity) {
        var model = instantiateModel(entity);
        model.setFilmDetailModel(entity);
        model.add(linkTo(methodOn(FilmController.class).getFilmDetail(String.valueOf(entity.getFilmId()))).withSelfRel());
        model.add(linkTo(methodOn(FilmController.class).getFilm(String.valueOf(entity.getFilmId()))).withRel("film"));
        model.add(linkTo(methodOn(FilmController.class).getFilms()).withRel("films"));
        return model;
    }
}
