package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.FilmEntity;
import com.example.app.model.internal.FilmModel;
import com.example.app.model.request.FilmRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface FilmMapper extends GenericMapper<FilmEntity, FilmModel> {
    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    @Mapping(target = "filmId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "languageByLanguageId", ignore = true)
    @Mapping(target = "languageByOriginalLanguageId", ignore = true)
    @Mapping(target = "filmActorsByFilmId", ignore = true)
    @Mapping(target = "filmCategoriesByFilmId", ignore = true)
    @Mapping(target = "inventoriesByFilmId", ignore = true)
    FilmEntity mapToEntity(FilmRequestModel dto);
}
