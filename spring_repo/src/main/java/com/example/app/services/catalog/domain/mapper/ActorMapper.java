package com.example.app.services.catalog.domain.mapper;

import com.example.app.common.domain.mapper.GenericMapper;
import com.example.app.services.catalog.domain.dto.ActorDto;
import com.example.app.services.catalog.domain.entity.ActorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface ActorMapper extends GenericMapper<ActorEntity, ActorDto.Actor> {
    @Override
    @Mapping(target = "actorId", source = "actorId")
    @Mapping(target = "fullName.firstName", source = "fullName.firstName")
    @Mapping(target = "fullName.lastName", source = "fullName.lastName")
    ActorDto.Actor mapToDto(ActorEntity entity);

    @Mapping(target = "actorId", ignore = true)
    @Mapping(target = "fullName.firstName", source = "firstName")
    @Mapping(target = "fullName.lastName", source = "lastName")
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    ActorEntity mapToEntity(ActorDto.ActorRequest dto);
}
