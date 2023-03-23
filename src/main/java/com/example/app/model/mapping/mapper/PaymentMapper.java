package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.internal.PaymentModel;
import com.example.app.model.request.PaymentRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface PaymentMapper extends GenericMapper<PaymentEntity, PaymentModel> {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "customerByCustomerId", ignore = true)
    @Mapping(target = "staffByStaffId", ignore = true)
    @Mapping(target = "rentalByRentalId", ignore = true)
    PaymentEntity mapToEntity(PaymentRequestModel dto);
}
