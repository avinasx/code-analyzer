package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.AddressEntity;
import com.example.app.model.internal.AddressModel;
import com.example.app.model.request.AddressRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AddressMapper extends GenericMapper<AddressEntity, AddressModel> {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "cityByCityId", ignore = true)
    @Mapping(target = "customersByAddressId", ignore = true)
    @Mapping(target = "staffByAddressId", ignore = true)
    @Mapping(target = "storesByAddressId", ignore = true)
    AddressEntity mapToEntity(AddressRequestModel dto);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "cityByCityId", ignore = true)
    @Mapping(target = "customersByAddressId", ignore = true)
    @Mapping(target = "staffByAddressId", ignore = true)
    @Mapping(target = "storesByAddressId", ignore = true)
    void updateEntity(AddressRequestModel dto, @MappingTarget AddressEntity entity);
}
