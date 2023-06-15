package com.example.app.app.location.service;

import com.example.app.app.catalog.domain.dto.AddressModel;
import com.example.app.app.location.domain.dto.AddressRequestModel;
import com.example.app.app.location.domain.dto.CityModel;
import com.example.app.app.location.domain.dto.CityRequestModel;
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
    public List<AddressModel> getAddressList() {
        var list = addressRepository.findAll();
        return addressMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressModel> getAddress(String addressId) {
        var entity = addressRepository.findById(Integer.valueOf(addressId)).orElseThrow(() ->
                new ResourceNotFoundException("Address not found with id '" + addressId + "'"));
        return Optional.of(addressMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressModel> getAddressDetailsList() {
        return addressRepository.findAllAddressDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AddressModel> getAddressDetails(String addressId) {
        var model = addressRepository.findAddressDetailsById(Integer.valueOf(addressId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with id '" + addressId + "'");
        }
        return model;
    }

    @Override
    @Transactional
    public AddressModel addAddress(AddressRequestModel model) {
        var entity = addressMapper.mapToEntity(model);
        var savedEntity = addressRepository.save(entity);
        return addressMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public AddressModel updateAddress(String addressId, AddressRequestModel model) {
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
    public List<CityModel> getCityList() {
        var list = cityRepository.findAll();
        return cityMapper.mapToDtoList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityModel> getCity(String cityId) {
        var entity = cityRepository.findById(Integer.valueOf(cityId)).orElseThrow(() ->
                new ResourceNotFoundException("City not found with id '" + cityId + "'"));
        return Optional.of(cityMapper.mapToDto(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CityModel> getCityDetailsList() {
        return cityRepository.findAllCityDetailsList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CityModel> getCityDetails(String cityId) {
        var model = cityRepository.findCityDetailsById(Integer.valueOf(cityId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("City not found with id '" + cityId + "'");
        }
        return model;
    }

    @Override
    @Transactional
    public CityModel addCity(CityRequestModel model) {
        var entity = cityMapper.mapToEntity(model);
        var savedEntity = cityRepository.save(entity);
        return cityMapper.mapToDto(savedEntity);
    }

    @Override
    @Transactional
    public CityModel updateCity(String cityId, CityRequestModel model) {
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
