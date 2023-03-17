package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.AddressEntity;
import com.example.app.model.entity.CityEntity;
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

    @Override
    public List<AddressEntity> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<AddressEntity> getAddressById(Integer id) {
        return addressRepository.findById(id);
    }

    @Override
    public List<AddressEntity> getAllAddressesDetail() {
        return addressRepository.findAllAddressesDetail();
    }

    @Override
    public Optional<AddressEntity> getAddressDetailById(Integer id) {
        return addressRepository.findAddressDetailById(id);
    }

    @Override
    public AddressEntity addAddress(AddressEntity entity) {
        return addressRepository.save(entity);
    }

    @Override
    public AddressEntity updateAddress(AddressEntity entity) {
        var id = entity.getAddressId();
        var resource = addressRepository.findById(id);
        if (resource.isPresent()) {
            return addressRepository.save(entity);
        } else {
            throw new ResourceNotFoundException("Address not found with id '" + id + "'");
        }
    }

    @Override
    public void deleteAddressById(Integer id) {
        addressRepository.deleteById(id);
    }

    @Override
    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<CityEntity> getCityById(Integer id) {
        return cityRepository.findById(id);
    }

    @Override
    public List<CityEntity> getAllCitiesDetail() {
        return cityRepository.findAllCitiesDetail();
    }

    @Override
    public Optional<CityEntity> getCityDetailById(Integer id) {
        return cityRepository.findCityDetailById(id);
    }

    @Override
    public CityEntity addCity(CityEntity entity) {
        return cityRepository.save(entity);
    }

    @Override
    public CityEntity updateCity(CityEntity entity) {
        var id = entity.getCityId();
        var resource = cityRepository.findById(id);
        if (resource.isPresent()) {
            return cityRepository.save(entity);
        } else {
            throw new ResourceNotFoundException("City not found with id '" + id + "'");
        }
    }

    @Override
    public void deleteCityById(Integer id) {
        cityRepository.deleteById(id);
    }
}
