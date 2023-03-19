package com.example.app.service;

import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.entity.AddressEntity;
import com.example.app.model.entity.CityEntity;
import com.example.app.model.mapping.CopyUtils;
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

    @Override
    public List<AddressEntity> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<AddressEntity> getAddressById(String id) {
        return addressRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<AddressEntity> getAllAddressesDetail() {
        return addressRepository.findAllAddressesDetail();
    }

    @Override
    public Optional<AddressEntity> getAddressDetailById(String id) {
        return addressRepository.findAddressDetailById(Integer.valueOf(id));
    }

    @Override
    public AddressEntity addAddress(AddressRequestModel model) {
        var entity = new AddressEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return addressRepository.save(entity);
    }

    @Override
    public AddressEntity updateAddress(String id, AddressRequestModel model) {
        var resource = addressRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("Address not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return addressRepository.save(entity);
    }

    @Override
    public void deleteAddressById(String id) {
        addressRepository.deleteById(Integer.valueOf(id));
    }

    @Override
    public List<CityEntity> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<CityEntity> getCityById(String id) {
        return cityRepository.findById(Integer.valueOf(id));
    }

    @Override
    public List<CityEntity> getAllCitiesDetail() {
        return cityRepository.findAllCitiesDetail();
    }

    @Override
    public Optional<CityEntity> getCityDetailById(String id) {
        return cityRepository.findCityDetailById(Integer.valueOf(id));
    }

    @Override
    public CityEntity addCity(CityRequestModel model) {
        var entity = new CityEntity();
        CopyUtils.copyNonNullProperties(model, entity);
        return cityRepository.save(entity);
    }

    @Override
    public CityEntity updateCity(String id, CityRequestModel model) {
        var resource = cityRepository.findById(Integer.valueOf(id));
        if (resource.isEmpty()) {
            throw new ResourceNotFoundException("City not found with id '" + id + "'");
        }
        var entity = resource.get();
        CopyUtils.copyNonNullProperties(model, entity);
        return cityRepository.save(entity);
    }

    @Override
    public void deleteCityById(String id) {
        cityRepository.deleteById(Integer.valueOf(id));
    }
}
