package com.example.app.app.customer.domain.mapper;

import com.example.app.app.customer.domain.dto.CustomerModel;
import com.example.app.app.customer.domain.dto.CustomerRequestModel;
import com.example.app.app.customer.domain.entity.CustomerEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface CustomerMapper extends GenericMapper<CustomerEntity, CustomerModel> {
    @Mapping(target = "customerId", ignore = true)
    @Mapping(target = "fullName.firstName", source = "firstName")
    @Mapping(target = "fullName.lastName", source = "lastName")
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "storeByStoreId", ignore = true)
    @Mapping(target = "addressByAddressId", ignore = true)
    @Mapping(target = "paymentsByCustomerId", ignore = true)
    @Mapping(target = "rentalsByCustomerId", ignore = true)
    @Mapping(target = "userByCustomerId", ignore = true)
    CustomerEntity mapToEntity(CustomerRequestModel dto);
}
