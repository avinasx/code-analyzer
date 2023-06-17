package com.example.app.app.catalog.controller;

import com.example.app.app.catalog.assembler.ActorDetailsRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.ActorRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.FilmDetailsRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.FilmRepresentationModelAssembler;
import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import com.example.app.app.catalog.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/films")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final FilmRepresentationModelAssembler filmAssembler;
    private final FilmDetailsRepresentationModelAssembler filmDetailsAssembler;
    private final ActorRepresentationModelAssembler actorAssembler;
    private final ActorDetailsRepresentationModelAssembler actorDetailsAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<FilmDto.FilmResponse>> getFilmList(
            @RequestParam(required = false) String releaseYear,
            @RequestParam(required = false) String rating,
            @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(filmAssembler.toCollectionModel(
                filmService.getFilmList(releaseYear, rating, pageable)));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addFilm(@RequestBody FilmDto.FilmRequest model) {
        var result = filmService.addFilm(model);
        return ResponseEntity.created(linkTo(methodOn(FilmController.class)
                .getFilm(String.valueOf(result.getFilmId()))).toUri()).build();
    }

    @GetMapping(path = "/{filmId}")
    public ResponseEntity<FilmDto.FilmResponse> getFilm(@PathVariable String filmId) {
        return filmService.getFilm(filmId)
                .map(filmAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{filmId}")
    public ResponseEntity<Void> updateFilm(@PathVariable String filmId, @RequestBody FilmDto.FilmRequest model) {
        var result = filmService.updateFilm(filmId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{filmId}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String filmId) {
        filmService.deleteFilm(filmId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{filmId}/actors")
    public ResponseEntity<CollectionModel<ActorDto.ActorResponse>> getFilmActorList(@PathVariable String filmId) {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(
                filmService.getFilmActorList(filmId)));
    }

    @GetMapping(path = "/{filmId}/actors/{actorId}")
    public ResponseEntity<ActorDto.ActorResponse> getFilmActor(@PathVariable String filmId, @PathVariable String actorId) {
        return filmService.getFilmActor(filmId, actorId)
                .map(actorAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{filmId}/details")
    public ResponseEntity<FilmDetailsDto.FilmDetailsResponse> getFilmDetails(@PathVariable String filmId) {
        return filmService.getFilmDetails(filmId)
                .map(filmDetailsAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{filmId}/stock")
    public ResponseEntity<FilmDto.Film> getFilmStock(@PathVariable String filmId) {
        return filmService.getFilmStock(filmId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
