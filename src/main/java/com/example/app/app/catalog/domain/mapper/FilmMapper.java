package com.example.app.app.catalog.domain.mapper;

import com.example.app.app.catalog.domain.dto.FilmModel;
import com.example.app.app.catalog.domain.dto.FilmRequestModel;
import com.example.app.app.catalog.domain.entity.FilmEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface FilmMapper extends GenericMapper<FilmEntity, FilmModel> {
    @Mapping(target = "filmId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "languageByLanguageId", ignore = true)
    @Mapping(target = "languageByOriginalLanguageId", ignore = true)
    @Mapping(target = "filmActorsByFilmId", ignore = true)
    @Mapping(target = "filmCategoriesByFilmId", ignore = true)
    @Mapping(target = "inventoriesByFilmId", ignore = true)
    FilmEntity mapToEntity(FilmRequestModel dto);
}
