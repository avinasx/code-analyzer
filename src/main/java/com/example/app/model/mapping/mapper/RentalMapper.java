package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.RentalEntity;
import com.example.app.model.internal.RentalModel;
import com.example.app.model.request.RentalRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RentalMapper extends GenericMapper<RentalEntity, RentalModel> {
    RentalMapper INSTANCE = Mappers.getMapper(RentalMapper.class);

    @Mapping(target = "rentalId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "paymentsByRentalId", ignore = true)
    @Mapping(target = "inventoryByInventoryId", ignore = true)
    @Mapping(target = "customerByCustomerId", ignore = true)
    @Mapping(target = "staffByStaffId", ignore = true)
    RentalEntity mapToEntity(RentalRequestModel model);

    @Mapping(target = "rentalId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "paymentsByRentalId", ignore = true)
    @Mapping(target = "inventoryByInventoryId", ignore = true)
    @Mapping(target = "customerByCustomerId", ignore = true)
    @Mapping(target = "staffByStaffId", ignore = true)
    void updateEntity(RentalRequestModel model, @MappingTarget RentalEntity entity);
}
