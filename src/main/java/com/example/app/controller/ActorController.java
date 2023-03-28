package com.example.app.controller;

import com.example.app.hateoas.assembler.ActorDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.ActorRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmRepresentationModelAssembler;
import com.example.app.model.request.ActorRequestModel;
import com.example.app.model.response.ActorDetailResponseModel;
import com.example.app.model.response.ActorResponseModel;
import com.example.app.model.response.FilmDetailResponseModel;
import com.example.app.model.response.FilmResponseModel;
import com.example.app.service.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/actors")
@AllArgsConstructor
public class ActorController {
    private final ActorService actorService;
    private final ActorRepresentationModelAssembler actorAssembler;
    private final ActorDetailRepresentationModelAssembler actorDetailAssembler;
    private final FilmRepresentationModelAssembler filmAssembler;
    private final FilmDetailRepresentationModelAssembler filmDetailAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<ActorResponseModel>> getActors() {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(
                actorService.getActors()));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addActor(@RequestBody ActorRequestModel model) {
        var result = actorService.addActor(model);
        return ResponseEntity.created(linkTo(methodOn(ActorController.class)
                .getActor(String.valueOf(result.getActorId()))).toUri()).build();
    }

    @GetMapping(path = "/{actorId}")
    public ResponseEntity<ActorResponseModel> getActor(@PathVariable String actorId) {
        return actorService.getActor(actorId)
                .map(actorAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{actorId}")
    public ResponseEntity<Void> updateActor(@PathVariable String actorId, @RequestBody ActorRequestModel model) {
        var result = actorService.updateActor(actorId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{actorId}")
    public ResponseEntity<Void> deleteActor(@PathVariable String actorId) {
        actorService.deleteActor(actorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{actorId}/details")
    public ResponseEntity<ActorDetailResponseModel> getActorDetail(@PathVariable String actorId) {
        return actorService.getActorDetail(actorId)
                .map(actorDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{actorId}/films")
    public ResponseEntity<CollectionModel<FilmResponseModel>> getActorFilms(@PathVariable String actorId) {
        var representation = filmAssembler.toCollectionModel(
                actorService.getActorFilms(actorId));
        var updatedRepresentation = representation.removeLinks()
                .add(linkTo(methodOn(ActorController.class).getActorFilms(actorId)).withSelfRel())
                .add(linkTo(methodOn(ActorController.class).getActor(actorId)).withRel("actor"));
        return ResponseEntity.ok(updatedRepresentation);
    }

    // @GetMapping(path = "/{actorId}/films")
    // public ResponseEntity<CollectionModel<FilmResponseModel>> getActorFilms(
    //         @PathVariable String actorId, @RequestParam(required = false) String releaseYear,
    //         @RequestParam(required = false) String rating) {
    //     return ResponseEntity.ok(filmAssembler.toCollectionModel(
    //             actorService.getActorFilms(actorId, releaseYear, rating)));
    // }

    @PostMapping(path = "/{actorId}/films")
    public ResponseEntity<Void> addActorFilm(@PathVariable String actorId, @RequestBody Map<String, String> input) {
        var result = actorService.addActorFilm(actorId, input.get("filmId"));
        return ResponseEntity.created(linkTo(methodOn(ActorController.class)
                .getActorFilm(actorId, String.valueOf(result.getFilmId()))).toUri()).build();
    }

    @GetMapping(path = "/{actorId}/films/{filmId}")
    public ResponseEntity<FilmResponseModel> getActorFilm(@PathVariable String actorId, @PathVariable String filmId) {
        var representation = actorService.getActorFilm(actorId, filmId)
                .map(filmAssembler::toModel);
        var updatedRepresentation = representation.map(r -> r.removeLinks()
                .add(linkTo(methodOn(ActorController.class).getActorFilm(actorId, filmId)).withSelfRel())
                .add(linkTo(methodOn(ActorController.class).getActorFilms(actorId)).withRel("films"))
                .add(linkTo(methodOn(ActorController.class).getActor(actorId)).withRel("actor")));
        return updatedRepresentation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{actorId}/films/{filmId}")
    public ResponseEntity<Void> deleteActorFilm(@PathVariable String actorId, @PathVariable String filmId) {
        actorService.removeActorFilm(actorId, filmId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{actorId}/films/{filmId}/details")
    public ResponseEntity<FilmDetailResponseModel> getActorFilmDetail(@PathVariable String actorId,
                                                                      @PathVariable String filmId) {
        var representation = actorService.getActorFilmDetail(actorId, filmId)
                .map(filmDetailAssembler::toModel);
        var updatedRepresentation = representation.map(r -> r.removeLinks()
                .add(linkTo(methodOn(ActorController.class).getActorFilmDetail(actorId, filmId)).withSelfRel())
                .add(linkTo(methodOn(ActorController.class).getActorFilm(actorId, filmId)).withRel("film"))
                .add(linkTo(methodOn(ActorController.class).getActorFilms(actorId)).withRel("films"))
                .add(linkTo(methodOn(ActorController.class).getActor(actorId)).withRel("actor")));
        return updatedRepresentation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/search")
    public ResponseEntity<CollectionModel<ActorResponseModel>> searchActorByName(@RequestBody Map<String, String> input) {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(
                actorService.searchActors(input.get("name"))));
    }
}
