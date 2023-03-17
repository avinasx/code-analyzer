package com.example.app.controller;

import com.example.app.hateoas.assembler.AddressRepresentationModelAssembler;
import com.example.app.hateoas.assembler.CityRepresentationModelAssembler;
import com.example.app.model.entity.AddressEntity;
import com.example.app.model.entity.CityEntity;
import com.example.app.model.request.AddressRequestModel;
import com.example.app.model.request.CityRequestModel;
import com.example.app.model.response.AddressResponseModel;
import com.example.app.model.response.CityResponseModel;
import com.example.app.service.LocationService;
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
public class LocationController {
    private final LocationService locationService;
    private final AddressRepresentationModelAssembler addressAssembler;
    private final CityRepresentationModelAssembler cityAssembler;

    @GetMapping(path = "/addresses")
    public ResponseEntity<CollectionModel<AddressResponseModel>> getAllAddresses() {
        return ResponseEntity.ok(addressAssembler.toCollectionModel(locationService.getAllAddresses()));
    }

    @PostMapping(path = "/addresses")
    public ResponseEntity<Void> addAddress(@RequestBody AddressRequestModel model) {
        var entity = new AddressEntity();
        BeanUtils.copyProperties(model, entity);
        var result = locationService.addAddress(entity);
        return ResponseEntity.created(linkTo(methodOn(LocationController.class)
                .getAddress(String.valueOf(result.getAddressId()))).toUri()).build();
    }

    @GetMapping(path = "/addresses/{id}")
    public ResponseEntity<AddressResponseModel> getAddress(@PathVariable String id) {
        var temp = locationService.getAddressById(Integer.valueOf(id));
        return locationService.getAddressById(Integer.valueOf(id))
                .map(addressAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/addresses/{id}")
    public ResponseEntity<Void> updateAddress(@PathVariable String id, @RequestBody AddressRequestModel model) {
        var entity = new AddressEntity();
        BeanUtils.copyProperties(model, entity);
        entity.setAddressId(Integer.valueOf(id));
        var reuslt = locationService.updateAddress(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/addresses/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        locationService.deleteAddressById(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/addresses/{id}/details")
    public ResponseEntity<AddressResponseModel> getAddressDetail(@PathVariable String id) {
        return locationService.getAddressDetailById(Integer.valueOf(id))
                .map(addressAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<CollectionModel<CityResponseModel>> getAllCities() {
        return ResponseEntity.ok(cityAssembler.toCollectionModel(locationService.getAllCities()));
    }

    @PostMapping(path = "/cities")
    public ResponseEntity<Void> addCity(@RequestBody CityRequestModel model) {
        var entity = new CityEntity();
        BeanUtils.copyProperties(model, entity);
        var result = locationService.addCity(entity);
        return ResponseEntity.created(linkTo(methodOn(LocationController.class)
                .getCity(String.valueOf(result.getCityId()))).toUri()).build();
    }

    @GetMapping(path = "/cities/{id}")
    public ResponseEntity<CityResponseModel> getCity(@PathVariable String id) {
        return locationService.getCityById(Integer.valueOf(id))
                .map(cityAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/cities/{id}")
    public ResponseEntity<Void> updateCity(@PathVariable String id, @RequestBody CityRequestModel model) {
        var entity = new CityEntity();
        BeanUtils.copyProperties(model, entity);
        entity.setCityId(Integer.valueOf(id));
        var reuslt = locationService.updateCity(entity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/cities/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable String id) {
        locationService.deleteCityById(Integer.valueOf(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/cities/{id}/details")
    public ResponseEntity<CityResponseModel> getCityDetail(@PathVariable String id) {
        return locationService.getCityDetailById(Integer.valueOf(id))
                .map(cityAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
