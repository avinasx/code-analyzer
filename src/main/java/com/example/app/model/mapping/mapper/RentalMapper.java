package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.RentalEntity;
import com.example.app.model.internal.core.RentalModel;
import com.example.app.model.request.RentalRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface RentalMapper extends GenericMapper<RentalEntity, RentalModel> {
    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    @Mapping(target = "rentalId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "paymentsByRentalId", ignore = true)
    @Mapping(target = "inventoryByInventoryId", ignore = true)
    @Mapping(target = "customerByCustomerId", ignore = true)
    @Mapping(target = "staffByStaffId", ignore = true)
    RentalEntity mapToEntity(RentalRequestModel dto);
}
