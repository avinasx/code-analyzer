package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.FullName;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FullNameMapper {
    FullName mapToFullName(String firstName, String lastName);
}
