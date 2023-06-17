package com.example.app.app.rental.domain.mapper;

import com.example.app.app.rental.domain.dto.RentalDto;
import com.example.app.app.rental.domain.entity.RentalEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface RentalMapper extends GenericMapper<RentalEntity, RentalDto.Rental> {
    @Mapping(target = "rentalId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "paymentsByRentalId", ignore = true)
    @Mapping(target = "inventoryByInventoryId", ignore = true)
    @Mapping(target = "customerByCustomerId", ignore = true)
    @Mapping(target = "staffByStaffId", ignore = true)
    RentalEntity mapToEntity(RentalDto.RentalRequest dto);
}
