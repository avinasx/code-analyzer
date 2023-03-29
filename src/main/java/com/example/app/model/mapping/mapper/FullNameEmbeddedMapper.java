package com.example.app.model.mapping.mapper;

import org.mapstruct.Mapping;

public interface FullNameEmbeddedMapper<T, U> extends GenericMapper<T, U> {
    @Override
    @Mapping(target = "fullName.firstName", source = "fullNameEmbed.firstName")
    @Mapping(target = "fullName.lastName", source = "fullNameEmbed.lastName")
    U mapToDto(T entity);

    @Override
    @Mapping(target = "fullNameEmbed.firstName", source = "fullName.firstName")
    @Mapping(target = "fullNameEmbed.lastName", source = "fullName.lastName")
    T mapToEntity(U dto);
}
