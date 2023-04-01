package com.example.app.controller;

import com.example.app.hateoas.assembler.ActorDetailsRepresentationModelAssembler;
import com.example.app.hateoas.assembler.ActorRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmDetailsRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmRepresentationModelAssembler;
import com.example.app.model.internal.core.FilmModel;
import com.example.app.model.request.FilmRequestModel;
import com.example.app.model.response.ActorResponseModel;
import com.example.app.model.response.FilmDetailResponseModel;
import com.example.app.model.response.FilmResponseModel;
import com.example.app.service.FilmService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/films")
@AllArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final FilmRepresentationModelAssembler filmAssembler;
    private final FilmDetailsRepresentationModelAssembler filmDetailsAssembler;
    private final ActorRepresentationModelAssembler actorAssembler;
    private final ActorDetailsRepresentationModelAssembler actorDetailsAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<FilmResponseModel>> getFilmList() {
        return ResponseEntity.ok(filmAssembler.toCollectionModel(
                filmService.getFilmList()));
    }

    // @GetMapping(path = "")
    // public ResponseEntity<CollectionModel<FilmResponseModel>> getFilms(
    //         @RequestParam(required = false) String releaseYear,
    //         @RequestParam(required = false) String rating) {
    //     return ResponseEntity.ok(filmAssembler.toCollectionModel(
    //             filmService.getFilms(releaseYear, rating)));
    // }

    @PostMapping(path = "")
    public ResponseEntity<Void> addFilm(@RequestBody FilmRequestModel model) {
        var result = filmService.addFilm(model);
        return ResponseEntity.created(linkTo(methodOn(FilmController.class)
                .getFilm(String.valueOf(result.getFilmId()))).toUri()).build();
    }

    @GetMapping(path = "/{filmId}")
    public ResponseEntity<FilmResponseModel> getFilm(@PathVariable String filmId) {
        return filmService.getFilm(filmId)
                .map(filmAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{filmId}")
    public ResponseEntity<Void> updateFilm(@PathVariable String filmId, @RequestBody FilmRequestModel model) {
        var result = filmService.updateFilm(filmId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{filmId}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String filmId) {
        filmService.deleteFilm(filmId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{filmId}/actors")
    public ResponseEntity<CollectionModel<ActorResponseModel>> getFilmActorList(@PathVariable String filmId) {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(
                filmService.getFilmActorList(filmId)));
    }

    @GetMapping(path = "/{filmId}/actors/{actorId}")
    public ResponseEntity<ActorResponseModel> getFilmActor(@PathVariable String filmId, @PathVariable String actorId) {
        return filmService.getFilmActor(filmId, actorId)
                .map(actorAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{filmId}/details")
    public ResponseEntity<FilmDetailResponseModel> getFilmDetails(@PathVariable String filmId) {
        return filmService.getFilmDetails(filmId)
                .map(filmDetailsAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{filmId}/stock")
    public ResponseEntity<FilmModel> getFilmStock(@PathVariable String filmId) {
        return filmService.getFilmStock(filmId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
