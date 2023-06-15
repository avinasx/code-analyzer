package com.example.app.app.location.service;

import com.example.app.app.catalog.domain.dto.AddressModel;
import com.example.app.app.location.domain.dto.AddressRequestModel;
import com.example.app.app.location.domain.dto.CityModel;
import com.example.app.app.location.domain.dto.CityRequestModel;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<AddressModel> getAddressList();

    Optional<AddressModel> getAddress(String addressId);

    List<AddressModel> getAddressDetailsList();

    Optional<AddressModel> getAddressDetails(String addressId);

    AddressModel addAddress(AddressRequestModel model);

    AddressModel updateAddress(String addressId, AddressRequestModel model);

    void deleteAddress(String addressId);

    List<CityModel> getCityList();

    Optional<CityModel> getCity(String cityId);

    List<CityModel> getCityDetailsList();

    Optional<CityModel> getCityDetails(String cityId);

    CityModel addCity(CityRequestModel model);

    CityModel updateCity(String cityId, CityRequestModel model);

    void deleteCity(String cityId);
}
