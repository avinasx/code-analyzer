package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.CityEntity;
import com.example.app.model.internal.core.CityModel;
import com.example.app.model.request.CityRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface CityMapper extends GenericMapper<CityEntity, CityModel> {
    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);

    @Mapping(target = "cityId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "addressesByCityId", ignore = true)
    @Mapping(target = "countryByCountryId", ignore = true)
    CityEntity mapToEntity(CityRequestModel dto);
}
