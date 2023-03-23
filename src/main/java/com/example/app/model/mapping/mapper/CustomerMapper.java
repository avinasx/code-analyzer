package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.CustomerEntity;
import com.example.app.model.internal.CustomerModel;
import com.example.app.model.request.CustomerRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface CustomerMapper extends FullNameEmbeddedMapper<CustomerEntity, CustomerModel> {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "fullName.firstName", source = "firstName")
    @Mapping(target = "fullName.lastName", source = "lastName")
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "storeByStoreId", ignore = true)
    @Mapping(target = "addressByAddressId", ignore = true)
    @Mapping(target = "paymentsByCustomerId", ignore = true)
    @Mapping(target = "rentalsByCustomerId", ignore = true)
    CustomerEntity mapToEntity(CustomerRequestModel dto);
}
