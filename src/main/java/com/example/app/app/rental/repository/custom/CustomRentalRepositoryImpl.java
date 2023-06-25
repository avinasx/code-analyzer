package com.example.app.app.rental.repository.custom;

import com.example.app.app.rental.domain.entity.QRentalEntity;
import com.example.app.app.rental.domain.entity.RentalEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomRentalRepositoryImpl implements CustomRentalRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public RentalEntity findRentedDvdRentalId(Integer customerId, Integer inventoryId) {
        var rental = QRentalEntity.rentalEntity;
        var query = jpaQueryFactory
                .select(rental)
                .from(rental)
                .where(rental.customerId.eq(customerId))
                .where(rental.inventoryId.eq(inventoryId))
                .where(rental.returnDate.isNull());
        return query.fetchFirst();
    }
}
