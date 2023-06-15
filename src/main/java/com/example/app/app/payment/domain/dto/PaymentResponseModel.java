package com.example.app.app.payment.domain.dto;

import com.example.app.common.constant.HalRelation;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.paymentList,
        itemRelation = HalRelation.Fields.payment)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseModel extends RepresentationModel<PaymentResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private PaymentModel paymentModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PaymentResponseModel that = (PaymentResponseModel) o;
        return Objects.equal(paymentModel, that.paymentModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), paymentModel);
    }
}
