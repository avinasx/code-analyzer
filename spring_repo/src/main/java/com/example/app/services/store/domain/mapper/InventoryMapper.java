package com.example.app.services.store.domain.mapper;

import com.example.app.common.domain.mapper.GenericMapper;
import com.example.app.services.store.domain.dto.InventoryDto;
import com.example.app.services.store.domain.entity.InventoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper extends GenericMapper<InventoryEntity, InventoryDto.Inventory> {
    @Override
    @Mapping(target = "filmByFilmId", ignore = true)
    @Mapping(target = "storeByStoreId", ignore = true)
    InventoryDto.Inventory mapToDto(InventoryEntity entity);
}
