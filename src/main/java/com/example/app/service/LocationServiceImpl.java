package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.internal.AddressModel;
import com.example.app.model.internal.CityModel;
import com.example.app.model.mapping.mapper.AddressMapper;
import com.example.app.model.mapping.mapper.CityMapper;
import com.example.app.model.request.AddressRequestModel;
import com.example.app.model.request.CityRequestModel;
import com.example.app.repository.AddressRepository;
import com.example.app.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<AddressModel> getAllAddresses() {
        var list = addressRepository.findAll();
        return addressMapper.mapToDtoList(list);
    }

    @Override
    public Optional<AddressModel> getAddressById(String id) {
        var entity = addressRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Address not found with id '" + id + "'"));
        return Optional.of(addressMapper.mapToDto(entity));
    }

    @Override
    public List<AddressModel> getAllAddressesDetail() {
        return addressRepository.findAllAddressesDetail();
    }

    @Override
    public Optional<AddressModel> getAddressDetailById(String id) {
        var model = addressRepository.findAddressDetailById(Integer.valueOf(id));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with id '" + id + "'");
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
    public AddressModel updateAddress(String id, AddressRequestModel model) {
        var entity = addressRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("Address not found with id '" + id + "'"));
        entity.update(addressMapper.mapToEntity(model));
        return addressMapper.mapToDto(entity);
    }

    @Override
    public void deleteAddressById(String id) {
        addressRepository.deleteById(Integer.valueOf(id));
    }

    @Override
    public List<CityModel> getAllCities() {
        var list = cityRepository.findAll();
        return cityMapper.mapToDtoList(list);
    }

    @Override
    public Optional<CityModel> getCityById(String id) {
        var entity = cityRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("City not found with id '" + id + "'"));
        return Optional.of(cityMapper.mapToDto(entity));
    }

    @Override
    public List<CityModel> getAllCitiesDetail() {
        return cityRepository.findAllCitiesDetail();
    }

    @Override
    public Optional<CityModel> getCityDetailById(String id) {
        var model = cityRepository.findCityDetailById(Integer.valueOf(id));
        if (model.isEmpty()) {
            throw new ResourceNotFoundException("City not found with id '" + id + "'");
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
    public CityModel updateCity(String id, CityRequestModel model) {
        var entity = cityRepository.findById(Integer.valueOf(id)).orElseThrow(() ->
                new ResourceNotFoundException("City not found with id '" + id + "'"));
        entity.update(cityMapper.mapToEntity(model));
        return cityMapper.mapToDto(entity);
    }

    @Override
    public void deleteCityById(String id) {
        cityRepository.deleteById(Integer.valueOf(id));
    }
}
