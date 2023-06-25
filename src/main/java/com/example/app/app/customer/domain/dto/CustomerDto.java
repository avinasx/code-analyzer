package com.example.app.app.customer.domain.dto;

import com.example.app.app.admin.domain.entity.UserEntity;
import com.example.app.app.location.domain.dto.AddressDto;
import com.example.app.app.store.domain.dto.StoreDto;
import com.example.app.common.constant.HalRelation;
import com.example.app.common.domain.dto.FullName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CustomerDto {
    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Customer {
        @JsonProperty(Fields.customerId)
        private Integer customerId;

        @JsonProperty(Fields.storeId)
        private Integer storeId;

        @JsonUnwrapped
        private FullName fullName;

        @JsonProperty(Fields.email)
        @Size(min = 1, max = 50)
        private String email;

        @JsonProperty(Fields.addressId)
        private Integer addressId;

        @JsonProperty(Fields.active)
        private Boolean active;

        @JsonProperty(Fields.createDate)
        private LocalDateTime createDate;

        @JsonProperty(Fields.lastUpdate)
        private LocalDateTime lastUpdate;

        @JsonIgnore
        @JsonProperty(Fields.storeByStoreId)
        @ToString.Exclude
        private StoreDto.Store storeByStoreId;

        @JsonIgnore
        @JsonProperty(Fields.addressByAddressId)
        @ToString.Exclude
        private AddressDto.Address addressByAddressId;

        @JsonIgnore
        @JsonProperty(Fields.userByCustomerId)
        @ToString.Exclude
        private UserEntity userByCustomerId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Customer that = (Customer) o;
            return Objects.equal(customerId, that.customerId)
                    && Objects.equal(storeId, that.storeId)
                    && Objects.equal(fullName, that.fullName)
                    && Objects.equal(email, that.email)
                    && Objects.equal(addressId, that.addressId)
                    && Objects.equal(active, that.active)
                    && Objects.equal(createDate, that.createDate)
                    && Objects.equal(lastUpdate, that.lastUpdate);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(customerId, storeId, fullName, email,
                    addressId, active, createDate, lastUpdate);
        }
    }

    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonProperty(Fields.storeId)
        private Integer storeId;

        @JsonProperty(Fields.firstName)
        private String firstName;

        @JsonProperty(Fields.lastName)
        private String lastName;

        @JsonProperty(Fields.email)
        @Size(min = 1, max = 50)
        private String email;

        @JsonProperty(Fields.addressId)
        private Integer addressId;

        @JsonProperty(Fields.active)
        private Boolean active;

        @JsonProperty(Fields.createDate)
        private LocalDateTime createDate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CustomerRequest that = (CustomerRequest) o;
            return Objects.equal(storeId, that.storeId)
                    && Objects.equal(firstName, that.firstName)
                    && Objects.equal(lastName, that.lastName)
                    && Objects.equal(email, that.email)
                    && Objects.equal(addressId, that.addressId)
                    && Objects.equal(active, that.active)
                    && Objects.equal(createDate, that.createDate);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(storeId, firstName, lastName, email, addressId, active, createDate);
        }
    }

    @Relation(collectionRelation = HalRelation.Fields.customerList,
            itemRelation = HalRelation.Fields.customer)
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerResponse extends RepresentationModel<CustomerResponse> implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonUnwrapped
        private Customer customer;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            CustomerResponse that = (CustomerResponse) o;
            return Objects.equal(customer, that.customer);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), customer);
        }
    }
}
