package com.example.app.app.payment.domain.mapper;

import com.example.app.app.payment.domain.dto.PaymentModel;
import com.example.app.app.payment.domain.dto.PaymentRequestModel;
import com.example.app.app.payment.domain.entity.PaymentEntity;
import com.example.app.common.domain.mapper.GenericMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface PaymentMapper extends GenericMapper<PaymentEntity, PaymentModel> {
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "lastUpdate", expression = "java(LocalDateTime.now())")
    @Mapping(target = "customerByCustomerId", ignore = true)
    @Mapping(target = "staffByStaffId", ignore = true)
    @Mapping(target = "rentalByRentalId", ignore = true)
    PaymentEntity mapToEntity(PaymentRequestModel dto);
}
