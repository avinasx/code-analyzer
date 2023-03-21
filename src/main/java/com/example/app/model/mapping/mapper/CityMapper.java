package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.CityEntity;
import com.example.app.model.internal.CityModel;
import com.example.app.model.request.CityRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CityMapper extends GenericMapper<CityEntity, CityModel> {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(target = "cityId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "addressesByCityId", ignore = true)
    @Mapping(target = "countryByCountryId", ignore = true)
    CityEntity mapToEntity(CityRequestModel dto);

    @Mapping(target = "cityId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "addressesByCityId", ignore = true)
    @Mapping(target = "countryByCountryId", ignore = true)
    void updateEntity(CityRequestModel dto, @MappingTarget CityEntity entity);
}
