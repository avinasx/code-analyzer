package com.example.app.app.admin.domain.entity;

import com.example.app.app.admin.domain.converter.UserAuthorityConverter;
import com.example.app.app.admin.domain.vo.UserAuthority;
import com.example.app.app.customer.domain.entity.CustomerEntity;
import com.google.common.base.Objects;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.EnumSet;

@Entity(name = "user")
@Table(name = "user", schema = "sakila")
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "customer_id", columnDefinition = "SMALLINT UNSIGNED", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Basic
    @Column(name = "password_hash", nullable = false)
    @Size(min = 1, max = 45)
    private String passwordHash;

    @Basic
    @Column(name = "authority", columnDefinition = "SET('ROLE_USER', 'ROLE_STAFF', 'ROLE_ADMIN')", nullable = false)
    @Convert(converter = UserAuthorityConverter.class)
    private EnumSet<UserAuthority> authority;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    @ToString.Exclude
    private CustomerEntity customerByCustomerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equal(customerId, that.customerId)
                && Objects.equal(passwordHash, that.passwordHash)
                && Objects.equal(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customerId, passwordHash, authority);
    }
}
