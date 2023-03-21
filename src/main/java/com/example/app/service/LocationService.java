package com.example.app.service;

import com.example.app.model.internal.AddressModel;
import com.example.app.model.internal.CityModel;
import com.example.app.model.request.AddressRequestModel;
import com.example.app.model.request.CityRequestModel;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<AddressModel> getAllAddresses();

    Optional<AddressModel> getAddressById(String id);

    List<AddressModel> getAllAddressesDetail();

    Optional<AddressModel> getAddressDetailById(String id);

    AddressModel addAddress(AddressRequestModel model);

    AddressModel updateAddress(String id, AddressRequestModel model);

    void deleteAddressById(String id);

    List<CityModel> getAllCities();

    Optional<CityModel> getCityById(String id);

    List<CityModel> getAllCitiesDetail();

    Optional<CityModel> getCityDetailById(String id);

    CityModel addCity(CityRequestModel model);

    CityModel updateCity(String id, CityRequestModel model);

    void deleteCityById(String id);
}
