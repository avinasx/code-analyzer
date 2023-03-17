package com.example.app.controller;

import com.example.app.hateoas.assembler.ActorDetailRepresentationModelAssembler;
import com.example.app.hateoas.assembler.ActorRepresentationModelAssembler;
import com.example.app.model.entity.ActorEntity;
import com.example.app.model.request.ActorRequestModel;
import com.example.app.model.response.ActorDetailResponseModel;
import com.example.app.model.response.ActorResponseModel;
import com.example.app.service.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class ActorController {
    private final ActorService actorService;
    private final ActorRepresentationModelAssembler actorAssembler;
    private final ActorDetailRepresentationModelAssembler actorDetailAssembler;

    @GetMapping(path = "/actors")
    public ResponseEntity<CollectionModel<ActorResponseModel>> getAllActors() {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(actorService.getAllActors()));
    }

    @PostMapping(path = "/actors")
    public ResponseEntity<Void> addActor(@RequestBody ActorRequestModel model) {
        var entity = new ActorEntity();
        BeanUtils.copyProperties(model, entity);
        var result = actorService.addActor(entity);
        return ResponseEntity.created(linkTo(methodOn(ActorController.class)
                .getActor(String.valueOf(result.getActorId()))).toUri()).build();
    }

    @GetMapping(path = "/actors/{id}")
    public ResponseEntity<ActorResponseModel> getActor(@PathVariable String id) {
        return actorService.getActorById(Integer.valueOf(id))
                .map(actorAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/actors/{id}")
    public ResponseEntity<Void> updateActor(@PathVariable String id, @ModelAttribute ActorEntity entity) {
        actorService.updateActor(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/actors/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable String id) {
        actorService.deleteActorById(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/actors/{id}/details")
    public ResponseEntity<ActorDetailResponseModel> getActorDetail(@PathVariable String id) {
        return actorService.getActorDetailById(Integer.valueOf(id))
                .map(actorDetailAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/actors/search")
    public ResponseEntity<CollectionModel<ActorResponseModel>> findActorByName(@RequestBody Map<String, String> name) {
        return ResponseEntity.ok(actorAssembler.toCollectionModel(actorService.findActorByName(name.get("name"))));
    }
}
