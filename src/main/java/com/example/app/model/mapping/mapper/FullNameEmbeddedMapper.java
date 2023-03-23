package com.example.app.model.mapping.mapper;

import org.mapstruct.Mapping;

public interface FullNameEmbeddedMapper<T, U> extends GenericMapper<T, U> {
    @Override
    @Mapping(target = "firstName", source = "fullName.firstName")
    @Mapping(target = "lastName", source = "fullName.lastName")
    U mapToDto(T entity);

    @Override
    @Mapping(target = "fullName.firstName", source = "firstName")
    @Mapping(target = "fullName.lastName", source = "lastName")
    T mapToEntity(U dto);
}
