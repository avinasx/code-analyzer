package com.example.app.app.location.controller;

import com.example.app.app.location.assembler.AddressRepresentationModelAssembler;
import com.example.app.app.location.assembler.CityRepresentationModelAssembler;
import com.example.app.app.location.domain.dto.AddressDto;
import com.example.app.app.location.domain.dto.CityDto;
import com.example.app.app.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final AddressRepresentationModelAssembler addressAssembler;
    private final CityRepresentationModelAssembler cityAssembler;

    @GetMapping(path = "/addresses")
    public ResponseEntity<CollectionModel<AddressDto.AddressResponse>> getAddressList() {
        return ResponseEntity.ok(addressAssembler.toCollectionModel(
                locationService.getAddressList()));
    }

    @PostMapping(path = "/addresses")
    public ResponseEntity<Void> addAddress(@RequestBody AddressDto.AddressRequest model) {
        var result = locationService.addAddress(model);
        return ResponseEntity.created(linkTo(methodOn(LocationController.class)
                .getAddress(String.valueOf(result.getAddressId()))).toUri()).build();
    }

    @GetMapping(path = "/addresses/{addressId}")
    public ResponseEntity<AddressDto.AddressResponse> getAddress(@PathVariable String addressId) {
        return locationService.getAddress(addressId)
                .map(addressAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/addresses/{addressId}")
    public ResponseEntity<Void> updateAddress(@PathVariable String addressId, @RequestBody AddressDto.AddressRequest model) {
        var result = locationService.updateAddress(addressId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String addressId) {
        locationService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/addresses/{addressId}/details")
    public ResponseEntity<AddressDto.AddressResponse> getAddressDetails(@PathVariable String addressId) {
        return locationService.getAddressDetails(addressId)
                .map(addressAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<CollectionModel<CityDto.CityResponse>> getCityList() {
        return ResponseEntity.ok(cityAssembler.toCollectionModel(
                locationService.getCityList()));
    }

    @PostMapping(path = "/cities")
    public ResponseEntity<Void> addCity(@RequestBody CityDto.CityRequest model) {
        var result = locationService.addCity(model);
        return ResponseEntity.created(linkTo(methodOn(LocationController.class)
                .getCity(String.valueOf(result.getCityId()))).toUri()).build();
    }

    @GetMapping(path = "/cities/{cityId}")
    public ResponseEntity<CityDto.CityResponse> getCity(@PathVariable String cityId) {
        return locationService.getCity(cityId)
                .map(cityAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(path = "/cities/{cityId}")
    public ResponseEntity<Void> updateCity(@PathVariable String cityId, @RequestBody CityDto.CityRequest model) {
        var result = locationService.updateCity(cityId, model);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/cities/{cityId}")
    public ResponseEntity<Void> deleteCity(@PathVariable String cityId) {
        locationService.deleteCity(cityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/cities/{cityId}/details")
    public ResponseEntity<CityDto.CityResponse> getCityDetails(@PathVariable String cityId) {
        return locationService.getCityDetails(cityId)
                .map(cityAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
