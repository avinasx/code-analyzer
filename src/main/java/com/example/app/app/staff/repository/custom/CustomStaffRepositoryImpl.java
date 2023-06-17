package com.example.app.app.staff.repository.custom;

import com.example.app.app.location.domain.entity.QAddressEntity;
import com.example.app.app.location.domain.entity.QCityEntity;
import com.example.app.app.staff.domain.dto.StaffDetailsDto;
import com.example.app.app.staff.domain.entity.QStaffEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CustomStaffRepositoryImpl implements CustomStaffRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StaffDetailsDto.StaffDetails> findAllStaffDetailsList() {
        var query = findStaffDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<StaffDetailsDto.StaffDetails> findStaffDetailsById(Integer staffId) {
        var query = findStaffDetail(staffId);
        return Optional.of(query.fetchFirst());
    }

    private JPAQuery<StaffDetailsDto.StaffDetails> findStaffDetail(Integer id) {
        var staff = QStaffEntity.staffEntity;
        var address = QAddressEntity.addressEntity;
        var city = QCityEntity.cityEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(StaffDetailsDto.StaffDetails.class,
                        staff.staffId.as("id"),
                        Expressions.asString(staff.fullName.firstName).concat(" ")
                                .concat(staff.fullName.lastName).as("name"),
                        address.address.as("address"),
                        address.postalCode.as("zipCode"),
                        address.phone.as("phone"),
                        city.city.as("city"),
                        city.countryId.as("countryId"),
                        staff.storeId.as("sid")))
                .from(staff)
                .join(address).on(address.addressId.eq(staff.addressId))
                .join(city).on(city.cityId.eq(address.cityId));
        if (id != null) {
            query.where(staff.staffId.eq(id));
        }
        return query;
    }
}
