package com.example.app.controller;

import com.example.app.hateoas.assembler.ActorDetailsRepresentationModelAssembler;
import com.example.app.hateoas.assembler.ActorRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmDetailsRepresentationModelAssembler;
import com.example.app.hateoas.assembler.FilmRepresentationModelAssembler;
import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.core.FilmModel;
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
    private final ActorDetailsRepresentationModelAssembler actorDetailsAssembler;
    private final FilmRepresentationModelAssembler filmAssembler;
    private final FilmDetailsRepresentationModelAssembler filmDetailsAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<ActorResponseModel>> getActorList() {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(
                actorService.getActorList()));
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
    public ResponseEntity<ActorDetailResponseModel> getActorDetails(@PathVariable String actorId) {
        return actorService.getActorDetails(actorId)
                .map(actorDetailsAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{actorId}/films")
    public ResponseEntity<CollectionModel<FilmResponseModel>> getActorFilmList(@PathVariable String actorId) {
        var representation = filmAssembler.toCollectionModel(
                actorService.getActorFilmList(actorId));
        var updatedRepresentation = representation.removeLinks()
                .add(linkTo(methodOn(ActorController.class).getActorFilmList(actorId)).withSelfRel())
                .add(linkTo(methodOn(ActorController.class).getActor(actorId)).withRel(HalRelation.Fields.actor));
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
        var result = actorService.addActorFilm(actorId, input.get(FilmModel.Fields.filmId));
        return ResponseEntity.created(linkTo(methodOn(ActorController.class)
                .getActorFilm(actorId, String.valueOf(result.getFilmId()))).toUri()).build();
    }

    @GetMapping(path = "/{actorId}/films/{filmId}")
    public ResponseEntity<FilmResponseModel> getActorFilm(@PathVariable String actorId, @PathVariable String filmId) {
        var representation = actorService.getActorFilm(actorId, filmId)
                .map(filmAssembler::toModel);
        var updatedRepresentation = representation.map(r -> r.removeLinks()
                .add(linkTo(methodOn(ActorController.class).getActorFilm(actorId, filmId)).withSelfRel())
                .add(linkTo(methodOn(ActorController.class).getActorFilmList(actorId)).withRel(HalRelation.Fields.filmList))
                .add(linkTo(methodOn(ActorController.class).getActor(actorId)).withRel(HalRelation.Fields.actor)));
        return updatedRepresentation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{actorId}/films/{filmId}")
    public ResponseEntity<Void> deleteActorFilm(@PathVariable String actorId, @PathVariable String filmId) {
        actorService.removeActorFilm(actorId, filmId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{actorId}/films/{filmId}/details")
    public ResponseEntity<FilmDetailResponseModel> getActorFilmDetails(@PathVariable String actorId,
                                                                       @PathVariable String filmId) {
        var representation = actorService.getActorFilmDetails(actorId, filmId)
                .map(filmDetailsAssembler::toModel);
        var updatedRepresentation = representation.map(r -> r.removeLinks()
                .add(linkTo(methodOn(ActorController.class).getActorFilmDetails(actorId, filmId)).withSelfRel())
                .add(linkTo(methodOn(ActorController.class).getActorFilm(actorId, filmId)).withRel(HalRelation.Fields.film))
                .add(linkTo(methodOn(ActorController.class).getActorFilmList(actorId)).withRel(HalRelation.Fields.filmList))
                .add(linkTo(methodOn(ActorController.class).getActor(actorId)).withRel(HalRelation.Fields.actor)));
        return updatedRepresentation.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/search")
    public ResponseEntity<CollectionModel<ActorResponseModel>> searchActorByName(@RequestBody Map<String, String> input) {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(
                actorService.searchActorList(input.get("name"))));
    }
}
