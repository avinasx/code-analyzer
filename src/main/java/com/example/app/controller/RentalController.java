package com.example.app.controller;

import com.example.app.hateoas.assembler.RentalRepresentationModelAssembler;
import com.example.app.model.request.RentalRequestModel;
import com.example.app.model.response.RentalResponseModel;
import com.example.app.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@AllArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    private final RentalRepresentationModelAssembler rentalAssembler;

    @GetMapping(path = "/rentals")
    public ResponseEntity<CollectionModel<RentalResponseModel>> getAllRentals() {
        return ResponseEntity.ok(rentalAssembler.toCollectionModel(rentalService.getAllRentals()));
    }

    @PostMapping(path = "/rentals")
    public ResponseEntity<Void> addRental(@RequestBody RentalRequestModel model) {
        var result = rentalService.addRental(model);
        return ResponseEntity.created(linkTo(methodOn(RentalController.class)
                .getRental(String.valueOf(result.getRentalId()))).toUri()).build();
    }

    @GetMapping(path = "/rentals/{id}")
    public ResponseEntity<RentalResponseModel> getRental(@PathVariable String id) {
        return rentalService.getRentalById(id)
                .map(rentalAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/rentals/{id}")
    public ResponseEntity<Void> updateRental(@PathVariable String id, @ModelAttribute RentalRequestModel model) {
        var reuslt = rentalService.updateRental(id, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/rentals/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable String id) {
        rentalService.removeRentalById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/rentals/{id}/details")
    public ResponseEntity<RentalResponseModel> getRentalDetail(@PathVariable String id) {
        return rentalService.getRentalDetailById(id)
                .map(rentalAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
