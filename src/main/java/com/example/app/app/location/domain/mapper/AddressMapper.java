package com.example.app.app.location.domain.mapper;

import com.example.app.app.catalog.domain.dto.AddressModel;
import com.example.app.app.location.domain.dto.AddressRequestModel;
import com.example.app.app.location.domain.entity.AddressEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface AddressMapper extends GenericMapper<AddressEntity, AddressModel> {
    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "cityByCityId", ignore = true)
    @Mapping(target = "customersByAddressId", ignore = true)
    @Mapping(target = "staffByAddressId", ignore = true)
    @Mapping(target = "storesByAddressId", ignore = true)
    AddressEntity mapToEntity(AddressRequestModel dto);
}
