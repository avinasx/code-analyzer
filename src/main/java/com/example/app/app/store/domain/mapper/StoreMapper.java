package com.example.app.app.store.domain.mapper;

import com.example.app.app.store.domain.dto.StoreDto;
import com.example.app.app.store.domain.entity.StoreEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface StoreMapper extends GenericMapper<StoreEntity, StoreDto.Store> {
    @Mapping(target = "storeId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "staffByManagerStaffId", ignore = true)
    @Mapping(target = "addressByAddressId", ignore = true)
    StoreEntity mapToEntity(StoreDto.StoreRequest dto);
}
