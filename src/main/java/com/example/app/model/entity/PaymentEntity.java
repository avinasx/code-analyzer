package com.example.app.model.entity;

import com.google.common.base.Objects;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "payment")
@Table(name = "payment", schema = "sakila", indexes = {
        @Index(name = "idx_fk_staff_id", columnList = "staff_id"),
        @Index(name = "idx_fk_customer_id", columnList = "customer_id")
})
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "payment_id", nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @Basic
    @Column(name = "customer_id", nullable = false, insertable = false, updatable = false, columnDefinition = "SMALLINT UNSIGNED")
    @NonNull
    private Integer customerId;

    @Basic
    @Column(name = "staff_id", nullable = false, insertable = false, updatable = false, columnDefinition = "TINYINT UNSIGNED")
    @NonNull
    private Integer staffId;

    @Basic
    @Column(name = "rental_id", nullable = true, insertable = false, updatable = false, columnDefinition = "INT")
    @ColumnDefault("NULL")
    private Integer rentalId;

    @Basic
    @Column(name = "amount", nullable = false, precision = 2, columnDefinition = "DECIMAL(5,2)")
    @NonNull
    private BigDecimal amount;

    @Basic
    @Column(name = "payment_date", nullable = false, columnDefinition = "DATETIME")
    @NonNull
    private LocalDateTime paymentDate;

    @Basic
    @Column(name = "last_update", nullable = true, columnDefinition = "TIMESTAMP")
    @ColumnDefault("CURRENT_TIMESTAMP")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private CustomerEntity customerByCustomerId;

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id", nullable = false)
    @NonNull
    @ToString.Exclude
    private StaffEntity staffByStaffId;

    @ManyToOne
    @JoinColumn(name = "rental_id", referencedColumnName = "rental_id")
    @ToString.Exclude
    private RentalEntity rentalByRentalId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEntity that = (PaymentEntity) o;
        return Objects.equal(paymentId, that.paymentId)
                && Objects.equal(customerId, that.customerId)
                && Objects.equal(staffId, that.staffId)
                && Objects.equal(rentalId, that.rentalId)
                && Objects.equal(amount, that.amount)
                && Objects.equal(paymentDate, that.paymentDate)
                && Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(paymentId, customerId, staffId, rentalId, amount,
                paymentDate, lastUpdate);
    }
}
