package com.example.app.model.mapping.mapper;

import com.example.app.model.entity.PaymentEntity;
import com.example.app.model.internal.PaymentModel;
import com.example.app.model.request.PaymentRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper extends GenericMapper<PaymentEntity, PaymentModel> {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "customerByCustomerId", ignore = true)
    @Mapping(target = "staffByStaffId", ignore = true)
    @Mapping(target = "rentalByRentalId", ignore = true)
    PaymentEntity mapToEntity(PaymentRequestModel model);

    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "customerByCustomerId", ignore = true)
    @Mapping(target = "staffByStaffId", ignore = true)
    @Mapping(target = "rentalByRentalId", ignore = true)
    void updateEntity(PaymentRequestModel model, @MappingTarget PaymentEntity entity);
}
