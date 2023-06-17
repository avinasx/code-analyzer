package com.example.app.app.location.service;

import com.example.app.app.location.domain.dto.AddressDto;
import com.example.app.app.location.domain.dto.CityDto;
import com.example.app.app.location.domain.mapper.AddressMapper;
import com.example.app.app.location.domain.mapper.CityMapper;
import com.example.app.app.location.repository.AddressRepository;
import com.example.app.app.location.repository.CityRepository;
import com.example.app.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final AddressMapper addressMapper;
    private final CityMapper cityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AddressDto.Address> getAddressList() {
        var list = addressRepository.findAll();
        return addressMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDto.Address> getAddress(String addressId) {
        var entity = addressRepository.findById(Integer.valueOf(addressId)).orElseThrow(() ->
                new ResourceNotFoundException("Address not found with id '" + addressId + "'"));
        return Optional.of(addressMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDto.Address> getAddressDetailsList() {
        return addressRepository.findAllAddressDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDto.Address> getAddressDetails(String addressId) {
        var model = addressRepository.findAddressDetailsById(Integer.valueOf(addressId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with id '" + addressId + "'");
        }
        return model;
    }

    @Override
    @Transactional
    public AddressDto.Address addAddress(AddressDto.AddressRequest model) {
        var entity = addressMapper.mapToEntity(model);
        var savedEntity = addressRepository.save(entity);
        return addressMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public AddressDto.Address updateAddress(String addressId, AddressDto.AddressRequest model) {
        var entity = addressRepository.findById(Integer.valueOf(addressId)).orElseThrow(() ->
                new ResourceNotFoundException("Address not found with id '" + addressId + "'"));
        entity.update(addressMapper.mapToEntity(model));
        return addressMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deleteAddress(String addressId) {
        addressRepository.deleteById(Integer.valueOf(addressId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityDto.City> getCityList() {
        var list = cityRepository.findAll();
        return cityMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityDto.City> getCity(String cityId) {
        var entity = cityRepository.findById(Integer.valueOf(cityId)).orElseThrow(() ->
                new ResourceNotFoundException("City not found with id '" + cityId + "'"));
        return Optional.of(cityMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityDto.City> getCityDetailsList() {
        return cityRepository.findAllCityDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityDto.City> getCityDetails(String cityId) {
        var model = cityRepository.findCityDetailsById(Integer.valueOf(cityId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("City not found with id '" + cityId + "'");
        }
        return model;
    }

    @Override
    @Transactional
    public CityDto.City addCity(CityDto.CityRequest model) {
        var entity = cityMapper.mapToEntity(model);
        var savedEntity = cityRepository.save(entity);
        return cityMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public CityDto.City updateCity(String cityId, CityDto.CityRequest model) {
        var entity = cityRepository.findById(Integer.valueOf(cityId)).orElseThrow(() ->
                new ResourceNotFoundException("City not found with id '" + cityId + "'"));
        entity.update(cityMapper.mapToEntity(model));
        return cityMapper.mapToDto(entity);
    }

    @Override
    @Transactional
    public void deleteCity(String cityId) {
        cityRepository.deleteById(Integer.valueOf(cityId));
    }
}
