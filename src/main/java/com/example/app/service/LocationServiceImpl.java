package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.core.AddressModel;
import com.example.app.model.internal.core.CityModel;
import com.example.app.model.mapping.mapper.AddressMapper;
import com.example.app.model.mapping.mapper.CityMapper;
import com.example.app.model.request.AddressRequestModel;
import com.example.app.model.request.CityRequestModel;
import com.example.app.repository.AddressRepository;
import com.example.app.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final AddressRepository addressRepository;
    private final CityRepository cityRepository;
    private final AddressMapper addressMapper;
    private final CityMapper cityMapper;

    @Override
    public List<AddressModel> getAddresses() {
        var list = addressRepository.findAll();
        return addressMapper.mapToDtoList(list);
    }

    @Override
    public Optional<AddressModel> getAddress(String addressId) {
        var entity = addressRepository.findById(Integer.valueOf(addressId)).orElseThrow(() ->
                new ResourceNotFoundException("Address not found with id '" + addressId + "'"));
        return Optional.of(addressMapper.mapToDto(entity));
    }

    @Override
    public List<AddressModel> getAddressesDetail() {
        return addressRepository.findAllAddressesDetail();
    }

    @Override
    public Optional<AddressModel> getAddressDetail(String addressId) {
        var model = addressRepository.findAddressDetailById(Integer.valueOf(addressId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with id '" + addressId + "'");
        }
        return model;
    }

    @Override
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
    public List<CityModel> getCities() {
        var list = cityRepository.findAll();
        return cityMapper.mapToDtoList(list);
    }

    @Override
    public Optional<CityModel> getCity(String cityId) {
        var entity = cityRepository.findById(Integer.valueOf(cityId)).orElseThrow(() ->
                new ResourceNotFoundException("City not found with id '" + cityId + "'"));
        return Optional.of(cityMapper.mapToDto(entity));
    }

    @Override
    public List<CityModel> getCitiesDetail() {
        return cityRepository.findAllCitiesDetail();
    }

    @Override
    public Optional<CityModel> getCityDetail(String cityId) {
        var model = cityRepository.findCityDetailById(Integer.valueOf(cityId));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("City not found with id '" + cityId + "'");
        }
        return model;
    }

    @Override
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
