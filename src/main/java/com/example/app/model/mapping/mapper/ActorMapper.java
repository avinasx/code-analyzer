package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.ActorEntity;
import com.example.app.model.internal.core.ActorModel;
import com.example.app.model.request.ActorRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface ActorMapper extends GenericMapper<ActorEntity, ActorModel> {
    ActorMapper INSTANCE = Mappers.getMapper(ActorMapper.class);

    @Mapping(target = "actorId", ignore = true)
    @Mapping(target = "fullName.firstName", source = "firstName")
    @Mapping(target = "fullName.lastName", source = "lastName")
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "filmActorsByActorId", ignore = true)
    ActorEntity mapToEntity(ActorRequestModel dto);
}
