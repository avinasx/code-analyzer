package com.example.app.repository.custom;

import com.example.app.model.entity.QAddressEntity;
import com.example.app.model.entity.QCityEntity;
import com.example.app.model.entity.QStaffEntity;
import com.example.app.model.internal.extra.StaffDetailModel;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomStaffRepositoryImpl implements CustomStaffRepository {
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StaffDetailModel> findAllStaffsDetail() {
        var query = findStaffDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<StaffDetailModel> findStaffDetailById(Integer staffId) {
        var query = findStaffDetail(staffId);
        return Optional.ofNullable(query.fetchFirst());
    }

    private JPAQuery<StaffDetailModel> findStaffDetail(Integer id) {
        var staff = QStaffEntity.staffEntity;
        var address = QAddressEntity.addressEntity;
        var city = QCityEntity.cityEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(StaffDetailModel.class,
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
