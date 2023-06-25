package com.example.app.app.catalog.controller;

import com.example.app.app.catalog.assembler.ActorDetailsRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.ActorRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.FilmDetailsRepresentationModelAssembler;
import com.example.app.app.catalog.assembler.FilmRepresentationModelAssembler;
import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.dto.FilmDetailsDto;
import com.example.app.app.catalog.domain.dto.FilmDto;
import com.example.app.app.catalog.service.FilmService;
import com.example.app.common.constant.FilmRating;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
        var condition = FilmDto.Film.builder()
                .releaseYear(releaseYear == null ? null : LocalDate.parse(releaseYear))
                .rating(rating == null ? null : FilmRating.valueOf(rating))
                .build();
        return ResponseEntity.ok(filmAssembler.toCollectionModel(
                filmService.getFilmList(condition, pageable)));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addFilm(@RequestBody FilmDto.FilmRequest model) {
        var result = filmService.addFilm(model);
        return ResponseEntity.created(linkTo(methodOn(FilmController.class)
                .getFilm(result.getFilmId())).toUri()).build();
    }

    @GetMapping(path = "/{filmId}")
    public ResponseEntity<FilmDto.FilmResponse> getFilm(@PathVariable Integer filmId) {
        return filmService.getFilm(filmId)
                .map(filmAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{filmId}")
    public ResponseEntity<Void> updateFilm(@PathVariable Integer filmId,
                                           @RequestBody FilmDto.FilmRequest model) {
        var result = filmService.updateFilm(filmId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{filmId}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Integer filmId) {
        filmService.deleteFilm(filmId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{filmId}/actors")
    public ResponseEntity<CollectionModel<ActorDto.ActorResponse>> getFilmActorList(
            @PathVariable Integer filmId,
            @PageableDefault(size = 10, page = 0, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(
                filmService.getFilmActorList(filmId, pageable)));
    }

    @GetMapping(path = "/{filmId}/actors/{actorId}")
    public ResponseEntity<ActorDto.ActorResponse> getFilmActor(@PathVariable Integer filmId,
                                                               @PathVariable Integer actorId) {
        return filmService.getFilmActor(filmId, actorId)
                .map(actorAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/{filmId}/details")
    public ResponseEntity<FilmDetailsDto.FilmDetailsResponse> getFilmDetails(@PathVariable Integer filmId) {
        return filmService.getFilmDetails(filmId)
                .map(filmDetailsAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
