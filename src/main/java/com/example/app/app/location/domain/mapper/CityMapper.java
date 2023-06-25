package com.example.app.app.location.domain.mapper;

import com.example.app.app.location.domain.dto.CityDto;
import com.example.app.app.location.domain.entity.CityEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface CityMapper extends GenericMapper<CityEntity, CityDto.City> {
    @Mapping(target = "cityId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "countryByCountryId", ignore = true)
    CityEntity mapToEntity(CityDto.CityRequest dto);
}
