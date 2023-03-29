package com.example.app.repository.custom;

import com.example.app.model.entity.QAddressEntity;
import com.example.app.model.entity.QCityEntity;
import com.example.app.model.entity.QCustomerEntity;
import com.example.app.model.entity.QPaymentEntity;
import com.example.app.model.internal.core.PaymentModel;
import com.example.app.model.internal.core.RentalModel;
import com.example.app.model.internal.extra.CustomerDetailModel;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CustomerDetailModel> findAllCustomersDetail() {
        var query = findCustomerDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<CustomerDetailModel> findCustomerDetailById(Integer customerId) {
        var query = findCustomerDetail(customerId);
        return Optional.ofNullable(query.fetchFirst());
    }

    private JPAQuery<CustomerDetailModel> findCustomerDetail(Integer id) {
        var cu = new QCustomerEntity("cu");
        var a = new QAddressEntity("a");
        var city = new QCityEntity("city");
        var query = jpaQueryFactory
                .select(Projections.constructor(CustomerDetailModel.class,
                        cu.customerId.as("id"),
                        Expressions.asString(cu.fullName.firstName).concat(" ")
                                .concat(cu.fullName.lastName).as("name"),
                        a.address.as("address"),
                        a.postalCode.as("zipCode"),
                        a.phone.as("phone"),
                        city.city.as("city"),
                        city.countryId.as("country"),
                        Expressions.stringTemplate("IF({0}, 'active', '')", cu.active).as("notes"),
                        cu.storeId.as("sid")))
                .from(cu)
                .join(a).on(cu.addressId.eq(a.addressId))
                .join(city).on(a.cityId.eq(city.cityId));
        if (id != null) {
            query.where(cu.customerId.eq(id));
        }
        return query;
    }

    @Override
    public List<PaymentModel> findAllCustomerPaymentsById(Integer customerId) {
        var query = findCustomerPayments(customerId, null, null);
        return query.fetch();
    }

    @Override
    public List<PaymentModel> findAllCustomerPaymentsByIdWithFilter(Integer customerId, LocalDate startDate, LocalDate endDate) {
        var query = findCustomerPayments(customerId, startDate, endDate);
        return query.fetch();
    }

    private JPAQuery<PaymentModel> findCustomerPayments(Integer customerId, LocalDate startDate, LocalDate endDate) {
        var customer = QCustomerEntity.customerEntity;
        var payment = QPaymentEntity.paymentEntity;
        var query = jpaQueryFactory
                .select(Projections.constructor(PaymentModel.class,
                        payment.paymentId.as("id"),
                        payment.customerId.as("customerId"),
                        payment.staffId.as("staffId"),
                        payment.rentalId.as("rentalId"),
                        payment.amount.as("amount"),
                        payment.paymentDate.as("paymentDate"),
                        payment.lastUpdate.as("lastUpdate")))
                .from(payment)
                .join(customer).on(payment.customerId.eq(customer.customerId))
                .where(customer.customerId.eq(customerId));
        if (startDate != null && endDate != null) {
            query.where(payment.paymentDate.between(startDate.atStartOfDay(), endDate.atStartOfDay()));
        }
        return query;
    }

    @Override
    public List<RentalModel> findAllCustomerRentalsById(Integer customerId) {
        var query = findCustomerRentals(customerId, null, null, null);
        return query.fetch();
    }

    @Override
    public List<RentalModel> findAllCustomerRentalsByIdWithFilter(Integer customerId, String status, LocalDate startDate, LocalDate endDate) {
        var query = findCustomerRentals(customerId, status, startDate, endDate);
        return query.fetch();
    }

    private JPAQuery<RentalModel> findCustomerRentals(Integer customerId, String status, LocalDate startDate, LocalDate endDate) {
        var customer = QCustomerEntity.customerEntity;
        var rental = QPaymentEntity.paymentEntity;
        var query = jpaQueryFactory
                .select(Projections.constructor(RentalModel.class,
                        rental.paymentId.as("id"),
                        rental.customerId.as("customerId"),
                        rental.staffId.as("staffId"),
                        rental.rentalId.as("rentalId"),
                        rental.amount.as("amount"),
                        rental.paymentDate.as("paymentDate"),
                        rental.lastUpdate.as("lastUpdate")))
                .from(rental)
                .join(customer).on(rental.customerId.eq(customer.customerId))
                .where(customer.customerId.eq(customerId));
        if (startDate != null && endDate != null) {
            query.where(rental.paymentDate.between(startDate.atStartOfDay(), endDate.atStartOfDay()));
        }
        return query;
    }
}
