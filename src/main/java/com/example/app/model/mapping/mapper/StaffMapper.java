package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.StaffEntity;
import com.example.app.model.internal.StaffModel;
import com.example.app.model.request.StaffRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StaffMapper extends GenericMapper<StaffEntity, StaffModel> {
    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "paymentsByStaffId", ignore = true)
    @Mapping(target = "rentalsByStaffId", ignore = true)
    @Mapping(target = "addressByAddressId", ignore = true)
    @Mapping(target = "storeByStoreId", ignore = true)
    @Mapping(target = "storesByStaffId", ignore = true)
    StaffEntity mapToEntity(StaffRequestModel model);

    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "paymentsByStaffId", ignore = true)
    @Mapping(target = "rentalsByStaffId", ignore = true)
    @Mapping(target = "addressByAddressId", ignore = true)
    @Mapping(target = "storeByStoreId", ignore = true)
    @Mapping(target = "storesByStaffId", ignore = true)
    void updateEntity(StaffRequestModel model, @MappingTarget StaffEntity entity);
}
