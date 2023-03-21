package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.ActorEntity;
import com.example.app.model.internal.ActorModel;
import com.example.app.model.request.ActorRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ActorMapper extends GenericMapper<ActorEntity, ActorModel> {
    ActorMapper INSTANCE = Mappers.getMapper(ActorMapper.class);

    @Mapping(target = "actorId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "filmActorsByActorId", ignore = true)
    ActorEntity mapToEntity(ActorRequestModel dto);

    @Mapping(target = "actorId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "filmActorsByActorId", ignore = true)
    void updateEntity(ActorRequestModel dto, @MappingTarget ActorEntity entity);
}
