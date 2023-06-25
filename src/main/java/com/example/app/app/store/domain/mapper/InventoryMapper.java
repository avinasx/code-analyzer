package com.example.app.app.store.domain.mapper;

import com.example.app.app.store.domain.dto.InventoryDto;
import com.example.app.app.store.domain.entity.InventoryEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper extends GenericMapper<InventoryEntity, InventoryDto.Inventory> {
    @Override
    @Mapping(target = "filmByFilmId", ignore = true)
    @Mapping(target = "storeByStoreId", ignore = true)
    InventoryDto.Inventory mapToDto(InventoryEntity entity);
}
