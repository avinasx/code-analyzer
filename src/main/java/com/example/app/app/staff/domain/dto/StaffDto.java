package com.example.app.app.staff.domain.dto;

import com.example.app.app.location.domain.dto.AddressDto;
import com.example.app.app.payment.domain.dto.PaymentDto;
import com.example.app.app.rental.domain.dto.RentalDto;
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
import java.util.Collection;

public class StaffDto {
    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Staff {
        @JsonProperty(Fields.staffId)
        private Integer staffId;

        @JsonUnwrapped
        private FullName fullName;

        @JsonProperty(Fields.addressId)
        private Integer addressId;

        // @JsonProperty(Fields.picture)
        // private byte[] picture;

        @JsonProperty(Fields.email)
        @Size(min = 1, max = 50)
        private String email;

        @JsonProperty(Fields.storeId)
        private Integer storeId;

        @JsonProperty(Fields.active)
        private Boolean active;

        @JsonProperty(Fields.username)
        @Size(min = 1, max = 16)
        private String username;

        @JsonProperty(Fields.password)
        @Size(min = 1, max = 40)
        private String password;

        @JsonProperty(Fields.lastUpdate)
        private LocalDateTime lastUpdate;

        @JsonIgnore
        @JsonProperty(Fields.paymentsByStaffId)
        @ToString.Exclude
        private Collection<PaymentDto.Payment> paymentsByStaffId;

        @JsonIgnore
        @JsonProperty(Fields.rentalsByStaffId)
        @ToString.Exclude
        private Collection<RentalDto.Rental> rentalsByStaffId;

        @JsonIgnore
        @JsonProperty(Fields.addressByAddressId)
        @ToString.Exclude
        private AddressDto.Address addressByAddressId;

        @JsonIgnore
        @JsonProperty(Fields.storeByStoreId)
        @ToString.Exclude
        private StoreDto.Store storeByStoreId;

        @JsonIgnore
        @JsonProperty(Fields.storesByStaffId)
        @ToString.Exclude
        private Collection<StoreDto.Store> storesByStaffId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Staff that = (Staff) o;
            return Objects.equal(staffId, that.staffId)
                    && Objects.equal(fullName, that.fullName)
                    && Objects.equal(addressId, that.addressId)
                    // && Objects.equal(picture, that.picture)
                    && Objects.equal(email, that.email)
                    && Objects.equal(storeId, that.storeId)
                    && Objects.equal(active, that.active)
                    && Objects.equal(username, that.username)
                    && Objects.equal(password, that.password)
                    && Objects.equal(lastUpdate, that.lastUpdate);
        }

        @Override
        public int hashCode() {
            // return Objects.hashCode(staffId, fullName, addressId, picture, email,
            //         storeId, active, username, password, lastUpdate);
            return Objects.hashCode(staffId, fullName, addressId, email,
                    storeId, active, username, password, lastUpdate);
        }
    }

    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StaffRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonProperty(Fields.firstName)
        private String firstName;

        @JsonProperty(Fields.lastName)
        private String lastName;

        @JsonProperty(Fields.addressId)
        private Integer addressId;

        // @JsonProperty(Fields.picture)
        // private byte[] picture;

        @JsonProperty(Fields.email)
        @Size(min = 1, max = 50)
        private String email;

        @JsonProperty(Fields.storeId)
        private Integer storeId;

        @JsonProperty(Fields.active)
        private Boolean active;

        @JsonProperty(Fields.username)
        @Size(min = 1, max = 16)
        private String username;

        @JsonProperty(Fields.password)
        @Size(min = 1, max = 40)
        private String password;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            StaffRequest that = (StaffRequest) o;
            return Objects.equal(firstName, that.firstName)
                    && Objects.equal(lastName, that.lastName)
                    && Objects.equal(addressId, that.addressId)
                    // && Objects.equal(picture, that.picture)
                    && Objects.equal(email, that.email)
                    && Objects.equal(storeId, that.storeId)
                    && Objects.equal(active, that.active)
                    && Objects.equal(username, that.username)
                    && Objects.equal(password, that.password);
        }

        @Override
        public int hashCode() {
            // return Objects.hashCode(firstName, lastName, addressId, picture, email, storeId, active, username, password);
            return Objects.hashCode(firstName, lastName, addressId, email, storeId, active, username, password);
        }
    }

    @Relation(collectionRelation = HalRelation.Fields.staffList,
            itemRelation = HalRelation.Fields.staff)
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StaffResponse extends RepresentationModel<StaffResponse> implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonUnwrapped
        private Staff staff;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            StaffResponse that = (StaffResponse) o;
            return Objects.equal(staff, that.staff);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), staff);
        }
    }
}
