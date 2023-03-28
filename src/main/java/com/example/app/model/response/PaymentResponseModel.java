package com.example.app.model.response;

import com.example.app.model.internal.core.PaymentModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = "payments", itemRelation = "payment")
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
