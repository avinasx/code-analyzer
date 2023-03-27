package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.StaffEntity;
import com.example.app.model.internal.core.StaffModel;
import com.example.app.model.request.StaffRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface StaffMapper extends FullNameEmbeddedMapper<StaffEntity, StaffModel> {
    StaffMapper INSTANCE = Mappers.getMapper(StaffMapper.class);

    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "fullName.firstName", source = "firstName")
    @Mapping(target = "fullName.lastName", source = "lastName")
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "paymentsByStaffId", ignore = true)
    @Mapping(target = "rentalsByStaffId", ignore = true)
    @Mapping(target = "addressByAddressId", ignore = true)
    @Mapping(target = "storeByStoreId", ignore = true)
    @Mapping(target = "storesByStaffId", ignore = true)
    StaffEntity mapToEntity(StaffRequestModel dto);
}
