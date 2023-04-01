package com.example.app.hateoas.assembler;

import com.example.app.controller.FilmController;
import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.extra.FilmDetailsModel;
import com.example.app.model.response.FilmDetailResponseModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FilmDetailsRepresentationModelAssembler extends RepresentationModelAssemblerSupport<FilmDetailsModel, FilmDetailResponseModel> {
    public FilmDetailsRepresentationModelAssembler() {
        super(FilmDetailsModel.class, FilmDetailResponseModel.class);
    }

    @Override
    @lombok.NonNull
    public FilmDetailResponseModel toModel(@lombok.NonNull FilmDetailsModel entity) {
        var model = instantiateModel(entity);
        model.setFilmDetailsModel(entity);
        model.add(linkTo(methodOn(FilmController.class).getFilmDetails(String.valueOf(entity.getFilmId()))).withSelfRel());
        model.add(linkTo(methodOn(FilmController.class).getFilm(String.valueOf(entity.getFilmId()))).withRel(HalRelation.Fields.film));
        model.add(linkTo(methodOn(FilmController.class).getFilmList()).withRel(HalRelation.Fields.filmList));
        return model;
    }
}
