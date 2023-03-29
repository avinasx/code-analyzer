package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.FullNameEmbed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FullNameMapper {
    FullNameEmbed mapToFullName(String firstName, String lastName);
}
