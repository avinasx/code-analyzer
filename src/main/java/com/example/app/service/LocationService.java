package com.example.app.service;

import com.example.app.model.entity.AddressEntity;
import com.example.app.model.entity.CityEntity;
import com.example.app.model.request.AddressRequestModel;
import com.example.app.model.request.CityRequestModel;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<AddressEntity> getAllAddresses();

    Optional<AddressEntity> getAddressById(String id);

    List<AddressEntity> getAllAddressesDetail();

    Optional<AddressEntity> getAddressDetailById(String id);

    AddressEntity addAddress(AddressRequestModel model);

    AddressEntity updateAddress(String id, AddressRequestModel model);

    void deleteAddressById(String id);

    List<CityEntity> getAllCities();

    Optional<CityEntity> getCityById(String id);

    List<CityEntity> getAllCitiesDetail();

    Optional<CityEntity> getCityDetailById(String id);

    CityEntity addCity(CityRequestModel model);

    CityEntity updateCity(String id, CityRequestModel model);

    void deleteCityById(String id);
}
