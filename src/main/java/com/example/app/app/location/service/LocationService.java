package com.example.app.app.location.service;

import com.example.app.app.location.domain.dto.AddressDto;
import com.example.app.app.location.domain.dto.CityDto;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    List<AddressDto.Address> getAddressList();

    Optional<AddressDto.Address> getAddress(String addressId);

    List<AddressDto.Address> getAddressDetailsList();

    Optional<AddressDto.Address> getAddressDetails(String addressId);

    AddressDto.Address addAddress(AddressDto.AddressRequest model);

    AddressDto.Address updateAddress(String addressId, AddressDto.AddressRequest model);

    void deleteAddress(String addressId);

    List<CityDto.City> getCityList();

    Optional<CityDto.City> getCity(String cityId);

    List<CityDto.City> getCityDetailsList();

    Optional<CityDto.City> getCityDetails(String cityId);

    CityDto.City addCity(CityDto.CityRequest model);

    CityDto.City updateCity(String cityId, CityDto.CityRequest model);

    void deleteCity(String cityId);
}
