package com.example.app.model.mapping.mapper;

import java.util.List;

public interface GenericMapper<T, U> {
    U mapToDto(T entity);

    T mapToEntity(U dto);

    List<U> mapToDtoList(List<T> entityList);

    List<T> mapToEntityList(List<U> dtoList);
}
