package com.example.app.services.store.repository.custom;

import com.example.app.common.constant.Country;
import com.example.app.services.location.domain.entity.QAddressEntity;
import com.example.app.services.location.domain.entity.QCityEntity;
import com.example.app.services.staff.domain.dto.StaffDto;
import com.example.app.services.staff.domain.entity.QStaffEntity;
import com.example.app.services.store.domain.dto.StoreDetailsDto;
import com.example.app.services.store.domain.entity.QStoreEntity;
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
public class CustomStoreRepositoryImpl implements CustomStoreRepository {
    private final JPAQueryFactory jpaQueryFactory;

    private static void replaceCountryIdToCountry(StoreDetailsDto.StoreDetails result) {
        final var replacedStore = Country.replaceCountryIdToCountryInString(result.getStore());
        final var replacedAddress = Country.replaceCountryIdToCountryInString(result.getAddress());
        result.setStore(replacedStore);
        result.setAddress(replacedAddress);
    }

    @Override
    public List<StoreDetailsDto.StoreDetails> findAllStoreDetailsList() {
        final var query = findStoreDetail(null);
        final var result = query.fetch();
        result.forEach(CustomStoreRepositoryImpl::replaceCountryIdToCountry);
        return result;
    }

    @Override
    public Optional<StoreDetailsDto.StoreDetails> findStoreDetailsById(Integer storeId) {
        final var query = findStoreDetail(storeId);
        final var result = query.fetchFirst();
        replaceCountryIdToCountry(result);
        return Optional.of(result);
    }

    private JPAQuery<StoreDetailsDto.StoreDetails> findStoreDetail(Integer id) {
        final var address = QAddressEntity.addressEntity;
        final var city = QCityEntity.cityEntity;
        final var staff = QStaffEntity.staffEntity;
        final var store = QStoreEntity.storeEntity;

        final var query = jpaQueryFactory
                .select(Projections.constructor(StoreDetailsDto.StoreDetails.class,
                        store.storeId.as("id"),
                        Expressions.asString(city.city).concat(",")
                                .concat(city.countryId.stringValue()).as("store"),
                        Expressions.asString(staff.fullName.firstName).concat(" ")
                                .concat(staff.fullName.lastName).as("manager"),
                        Expressions.asString(address.address).concat(", ")
                                .concat(address.district).concat(", ")
                                .concat(city.city).concat(", ")
                                .concat(city.countryId.stringValue()).as("address")))
                .from(store)
                .innerJoin(staff).on(staff.staffId.eq(store.managerStaffId))
                .innerJoin(address).on(address.addressId.eq(staff.addressId))
                .innerJoin(city).on(city.cityId.eq(address.cityId));
        if (id != null) {
            query.where(store.storeId.eq(id));
        }
        return query;
    }

    @Override
    public List<StaffDto.Staff> findAllStoreStaffList(Integer storeId) {
        final var query = findStoreStaff(storeId, null);
        return query.fetch();
    }

    @Override
    public Optional<StaffDto.Staff> findStoreStaffById(Integer storeId, Integer staffId) {
        final var query = findStoreStaff(storeId, staffId);
        return Optional.of(query.fetchFirst());
    }

    private JPAQuery<StaffDto.Staff> findStoreStaff(Integer storeId, Integer staffId) {
        final var staff = QStaffEntity.staffEntity;
        final var store = QStoreEntity.storeEntity;

        final var query = jpaQueryFactory
                .select(Projections.constructor(StaffDto.Staff.class))
                .from(staff)
                .innerJoin(store).on(store.storeId.eq(staff.storeId))
                .where(store.storeId.eq(storeId));
        if (staffId != null) {
            query.where(staff.staffId.eq(staffId));
        }
        return query;
    }
}
