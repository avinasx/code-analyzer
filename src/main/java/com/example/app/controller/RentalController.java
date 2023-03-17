package com.example.app.controller;

import com.example.app.hateoas.assembler.RentalRepresentationModelAssembler;
import com.example.app.model.entity.RentalEntity;
import com.example.app.model.request.RentalRequestModel;
import com.example.app.model.response.RentalResponseModel;
import com.example.app.service.RentalService;
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
public class RentalController {
    private RentalService rentalService;
    private RentalRepresentationModelAssembler rentalAssembler;

    @GetMapping(path = "/rentals")
    public ResponseEntity<CollectionModel<RentalResponseModel>> getAllRentals() {
        return ResponseEntity.ok(rentalAssembler.toCollectionModel(rentalService.getAllRentals()));
    }

    @PostMapping(path = "/rentals")
    public ResponseEntity<Void> addRental(@RequestBody RentalRequestModel model) {
        var entity = new RentalEntity();
        BeanUtils.copyProperties(model, entity);
        var result = rentalService.addRental(entity);
        return ResponseEntity.created(linkTo(methodOn(RentalController.class)
                .getRental(String.valueOf(result.getRentalId()))).toUri()).build();
    }

    @GetMapping(path = "/rentals/{id}")
    public ResponseEntity<RentalResponseModel> getRental(@PathVariable String id) {
        return rentalService.getRentalById(Integer.valueOf(id))
                .map(rentalAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/rentals/{id}")
    public ResponseEntity<Void> updateRental(@PathVariable String id, @ModelAttribute RentalEntity entity) {
        rentalService.updateRental(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/rentals/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable String id) {
        rentalService.removeRentalById(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/rentals/{id}/details")
    public ResponseEntity<RentalResponseModel> getRentalDetail(@PathVariable String id) {
        return rentalService.getRentalDetailById(Integer.valueOf(id))
                .map(rentalAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
