package com.example.app.app.rental.controller;

import com.example.app.app.rental.assembler.RentalRepresentationModelAssembler;
import com.example.app.app.rental.domain.dto.RentalDto;
import com.example.app.app.rental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    private final RentalRepresentationModelAssembler rentalAssembler;

    @GetMapping(path = "")
    public ResponseEntity<CollectionModel<RentalDto.RentalResponse>> getRentalList() {
        return ResponseEntity.ok(rentalAssembler.toCollectionModel(
                rentalService.getRentalList()));
    }

    @PostMapping(path = "")
    public ResponseEntity<Void> addRental(@RequestBody RentalDto.RentalRequest model) {
        var result = rentalService.addRental(model);
        return ResponseEntity.created(linkTo(methodOn(RentalController.class)
                .getRental(String.valueOf(result.getRentalId()))).toUri()).build();
    }

    @GetMapping(path = "/{rentalId}")
    public ResponseEntity<RentalDto.RentalResponse> getRental(@PathVariable String rentalId) {
        return rentalService.getRental(rentalId)
                .map(rentalAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/{rentalId}")
    public ResponseEntity<Void> updateRental(@PathVariable String rentalId, @ModelAttribute RentalDto.RentalRequest model) {
        var result = rentalService.updateRental(rentalId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{rentalId}")
    public ResponseEntity<Void> deleteRental(@PathVariable String rentalId) {
        rentalService.deleteRental(rentalId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{rentalId}/details")
    public ResponseEntity<RentalDto.RentalResponse> getRentalDetails(@PathVariable String rentalId) {
        return rentalService.getRentalDetails(rentalId)
                .map(rentalAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
