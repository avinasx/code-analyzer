package com.example.app.repository.custom;

import com.example.app.model.constant.Country;
import com.example.app.model.entity.QAddressEntity;
import com.example.app.model.entity.QCityEntity;
import com.example.app.model.entity.QStaffEntity;
import com.example.app.model.entity.QStoreEntity;
import com.example.app.model.internal.core.StaffModel;
import com.example.app.model.internal.extra.StoreDetailsModel;
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
public class CustomStoreRepositoryImpl implements CustomStoreRepository {
    private JPAQueryFactory jpaQueryFactory;

    private static void replaceCountryIdToCountry(StoreDetailsModel result) {
        var replacedStore = Country.replaceCountryIdToCountryInString(result.getStore());
        var replacedAddress = Country.replaceCountryIdToCountryInString(result.getAddress());
        result.setStore(replacedStore);
        result.setAddress(replacedAddress);
    }

    @Override
    public List<StoreDetailsModel> findAllStoreDetailsList() {
        var query = findStoreDetail(null);
        var result = query.fetch();
        result.forEach(CustomStoreRepositoryImpl::replaceCountryIdToCountry);
        return result;
    }

    @Override
    public Optional<StoreDetailsModel> findStoreDetailsById(Integer storeId) {
        var query = findStoreDetail(storeId);
        var result = query.fetchFirst();
        replaceCountryIdToCountry(result);
        return Optional.of(result);
    }

    private JPAQuery<StoreDetailsModel> findStoreDetail(Integer id) {
        var address = QAddressEntity.addressEntity;
        var city = QCityEntity.cityEntity;
        var staff = QStaffEntity.staffEntity;
        var store = QStoreEntity.storeEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(StoreDetailsModel.class,
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
    public List<StaffModel> findAllStoreStaffList(Integer storeId) {
        var query = findStoreStaff(storeId, null);
        return query.fetch();
    }

    @Override
    public Optional<StaffModel> findStoreStaffById(Integer storeId, Integer staffId) {
        var query = findStoreStaff(storeId, staffId);
        return Optional.of(query.fetchFirst());
    }

    private JPAQuery<StaffModel> findStoreStaff(Integer storeId, Integer staffId) {
        var staff = QStaffEntity.staffEntity;
        var store = QStoreEntity.storeEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(StaffModel.class))
                .from(staff)
                .innerJoin(store).on(store.storeId.eq(staff.storeId))
                .where(store.storeId.eq(storeId));
        if (staffId != null) {
            query.where(staff.staffId.eq(staffId));
        }
        return query;
    }
}
