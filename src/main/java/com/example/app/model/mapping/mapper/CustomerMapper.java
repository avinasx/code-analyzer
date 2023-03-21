package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.internal.CustomerModel;
import com.example.app.model.request.CustomerRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends GenericMapper<CustomerEntity, CustomerModel> {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "storeByStoreId", ignore = true)
    @Mapping(target = "addressByAddressId", ignore = true)
    @Mapping(target = "paymentsByCustomerId", ignore = true)
    @Mapping(target = "rentalsByCustomerId", ignore = true)
    CustomerEntity mapToEntity(CustomerRequestModel model);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "storeByStoreId", ignore = true)
    @Mapping(target = "addressByAddressId", ignore = true)
    @Mapping(target = "paymentsByCustomerId", ignore = true)
    @Mapping(target = "rentalsByCustomerId", ignore = true)
    void updateEntity(CustomerRequestModel model, @MappingTarget CustomerEntity entity);
}
