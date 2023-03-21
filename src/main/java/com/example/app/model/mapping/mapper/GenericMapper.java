package com.example.app.model.mapping.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface GenericMapper<T, U> {
    U mapToDto(T entity);

    T mapToEntity(U dto);

    void updateEntity(U dto, @MappingTarget T entity);

    List<U> mapToDtoList(List<T> entityList);

    List<T> mapToEntityList(List<U> dtoList);
}
