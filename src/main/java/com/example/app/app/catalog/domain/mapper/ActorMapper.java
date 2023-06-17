package com.example.app.app.catalog.domain.mapper;

import com.example.app.app.catalog.domain.dto.ActorDto;
import com.example.app.app.catalog.domain.entity.ActorEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface ActorMapper extends GenericMapper<ActorEntity, ActorDto.Actor> {
    @Mapping(target = "actorId", ignore = true)
    @Mapping(target = "fullName.firstName", source = "firstName")
    @Mapping(target = "fullName.lastName", source = "lastName")
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "filmActorsByActorId", ignore = true)
    ActorEntity mapToEntity(ActorDto.ActorRequest dto);

    @Mapping(target = "actorId", source = "actorId")
    @Mapping(target = "fullName.firstName", source = "fullName.firstName")
    @Mapping(target = "fullName.lastName", source = "fullName.lastName")
    @Mapping(target = "filmActorsByActorId", ignore = true)
    ActorDto.Actor mapToDto(ActorEntity entity);
}
