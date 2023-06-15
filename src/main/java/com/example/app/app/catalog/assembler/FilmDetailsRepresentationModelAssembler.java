package com.example.app.app.catalog.assembler;

import com.example.app.app.catalog.controller.FilmController;
import com.example.app.app.catalog.domain.dto.FilmDetailResponseModel;
import com.example.app.app.catalog.domain.dto.FilmDetailsModel;
import com.example.app.common.constant.HalRelation;
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
        model.add(linkTo(methodOn(FilmController.class).getFilmList(null, null, null)).withRel(HalRelation.Fields.filmList));
        return model;
    }
}
