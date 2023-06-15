package com.example.app.app.staff.domain.mapper;

import com.example.app.app.staff.domain.dto.StaffModel;
import com.example.app.app.staff.domain.dto.StaffRequestModel;
import com.example.app.app.staff.domain.entity.StaffEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface StaffMapper extends GenericMapper<StaffEntity, StaffModel> {
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
