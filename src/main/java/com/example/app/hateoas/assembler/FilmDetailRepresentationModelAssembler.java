package com.example.app.hateoas.assembler;

import com.example.app.controller.FilmController;
import com.example.app.model.internal.FilmDetailModel;
import com.example.app.model.response.FilmDetailResponseModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FilmDetailRepresentationModelAssembler extends RepresentationModelAssemblerSupport<FilmDetailModel, FilmDetailResponseModel> {
    public FilmDetailRepresentationModelAssembler() {
        super(FilmDetailModel.class, FilmDetailResponseModel.class);
    }

    @NotNull
    @Override
    public FilmDetailResponseModel toModel(@NotNull FilmDetailModel entity) {
        var model = new FilmDetailResponseModel();
        BeanUtils.copyProperties(entity, model);
        model.add(linkTo(methodOn(FilmController.class).getFilmDetail(String.valueOf(model.getFid()))).withSelfRel());
        model.add(linkTo(methodOn(FilmController.class).getFilm(String.valueOf(model.getFid()))).withRel("film"));
        model.add(linkTo(methodOn(FilmController.class).getAllFilms()).withRel("films"));
        return model;
    }
}
