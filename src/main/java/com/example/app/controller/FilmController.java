package com.example.app.controller;

import com.example.app.hateoas.assembler.FilmDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmRepresentationModelAssembler;
import com.example.app.model.entity.FilmEntity;
import com.example.app.model.request.FilmRequestModel;
import com.example.app.model.response.FilmDetailResponseModel;
import com.example.app.model.response.FilmResponseModel;
import com.example.app.service.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final FilmRepresentationModelAssembler filmAssembler;
    private final FilmDetailRepresentationModelAssembler filmDetailAssembler;

    @GetMapping(path = "/films")
    public ResponseEntity<CollectionModel<FilmResponseModel>> getAllFilms() {
        return ResponseEntity.ok(filmAssembler.toCollectionModel(filmService.getAllFilms()));
    }

    @PostMapping(path = "/films")
    public ResponseEntity<Void> addFilm(@RequestBody FilmRequestModel model) {
        var entity = new FilmEntity();
        BeanUtils.copyProperties(model, entity);
        var result = filmService.addFilm(entity);
        return ResponseEntity.created(linkTo(methodOn(FilmController.class)
                .getFilm(String.valueOf(result.getFilmId()))).toUri()).build();
    }

    @GetMapping(path = "/films/{id}")
    public ResponseEntity<FilmResponseModel> getFilm(@PathVariable String id) {
        return filmService.getFilmById(Integer.valueOf(id))
                .map(filmAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/films/{id}")
    public ResponseEntity<Void> updateFilm(@PathVariable String id, @RequestBody FilmRequestModel model) {
        var entity = new FilmEntity();
        BeanUtils.copyProperties(model, entity);
        entity.setFilmId(Integer.valueOf(id));
        var result = filmService.updateFilm(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/films/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String id) {
        filmService.deleteFilmById(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/films/{id}/details")
    public ResponseEntity<FilmDetailResponseModel> getFilmDetail(@PathVariable String id) {
        return filmService.getFilmDetailById(Integer.valueOf(id))
                .map(filmDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/films/{id}/stock")
    public ResponseEntity<FilmEntity> getFilmStock(@PathVariable String id) {
        return filmService.getFilmStockById(Integer.valueOf(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
