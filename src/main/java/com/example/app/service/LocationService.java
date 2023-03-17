package com.example.app.service;

import com.example.app.model.entity.AddressEntity;
import com.example.app.model.entity.CityEntity;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<AddressEntity> getAllAddresses();

    Optional<AddressEntity> getAddressById(Integer id);

    List<AddressEntity> getAllAddressesDetail();

    Optional<AddressEntity> getAddressDetailById(Integer id);

    AddressEntity addAddress(AddressEntity entity);

    void updateAddress(AddressEntity entity);

    void deleteAddressById(Integer id);

    List<CityEntity> getAllCities();

    Optional<CityEntity> getCityById(Integer id);

    List<CityEntity> getAllCitiesDetail();

    Optional<CityEntity> getCityDetailById(Integer id);

    CityEntity addCity(CityEntity entity);

    void updateCity(CityEntity entity);

    void deleteCityById(Integer id);
}
