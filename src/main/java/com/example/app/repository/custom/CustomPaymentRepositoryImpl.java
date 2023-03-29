package com.example.app.repository.custom;

import com.example.app.model.constant.Country;
import com.example.app.model.entity.*;
import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.internal.extra.CategorySalesModel;
import com.example.app.model.internal.extra.StoreSalesModel;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomPaymentRepositoryImpl implements CustomPaymentRepository {
    private JPAQueryFactory jpaQueryFactory;

    private static void replaceCountryIdToCountry(StoreSalesModel result) {
        var replacedStore = Country.replaceCountryIdToCountryInString(result.getStore());
        result.setStore(replacedStore);
    }

    @Override
    public List<PaymentModel> findAllPaymentsDetail() {
        return null;
    }

    @Override
    public Optional<PaymentModel> findPaymentDetailById(Integer paymentId) {
        return Optional.empty();
    }

    @Override
    public List<CategorySalesModel> calculateSalesByCategory() {
        var payment = QPaymentEntity.paymentEntity;
        var rental = QRentalEntity.rentalEntity;
        var inventory = QInventoryEntity.inventoryEntity;
        var film = QFilmEntity.filmEntity;
        var filmCategory = QFilmCategoryEntity.filmCategoryEntity;

        var totalSales = payment.amount.sum();
        var query = jpaQueryFactory
                .select(Projections.constructor(CategorySalesModel.class,
                        filmCategory.categoryId.as("category"),
                        totalSales.as("totalSales")))
                .from(payment)
                .innerJoin(rental).on(rental.rentalId.eq(payment.rentalId))
                .innerJoin(inventory).on(inventory.inventoryId.eq(rental.inventoryId))
                .innerJoin(film).on(film.filmId.eq(inventory.filmId))
                .innerJoin(filmCategory).on(filmCategory.filmId.eq(film.filmId))
                .groupBy(filmCategory.categoryId)
                .orderBy(totalSales.desc());
        return query.fetch();
    }

    @Override
    public List<StoreSalesModel> calculateSalesByStore() {
        var payment = QPaymentEntity.paymentEntity;
        var rental = QRentalEntity.rentalEntity;
        var inventory = QInventoryEntity.inventoryEntity;
        var store = QStoreEntity.storeEntity;
        var address = QAddressEntity.addressEntity;
        var city = QCityEntity.cityEntity;
        var staff = QStaffEntity.staffEntity;

        var query = jpaQueryFactory
                .select(Projections.constructor(StoreSalesModel.class,
                        Expressions.asString(city.city).concat(",")
                                .concat(city.countryId.stringValue()).as("store"),
                        Expressions.asString(staff.fullNameEmbed.firstName).concat(" ")
                                .concat(staff.fullNameEmbed.lastName).as("manager"),
                        payment.amount.sum().as("totalSales")))
                .from(payment)
                .innerJoin(rental).on(rental.rentalId.eq(payment.rentalId))
                .innerJoin(inventory).on(inventory.inventoryId.eq(rental.inventoryId))
                .innerJoin(store).on(store.storeId.eq(inventory.storeId))
                .innerJoin(address).on(address.addressId.eq(store.addressId))
                .innerJoin(city).on(city.cityId.eq(address.cityId))
                .innerJoin(staff).on(staff.staffId.eq(store.managerStaffId))
                .groupBy(store.storeId)
                .orderBy(store.storeId.asc());
        var result = query.fetch();
        result.forEach(CustomPaymentRepositoryImpl::replaceCountryIdToCountry);
        return result;
    }
}
