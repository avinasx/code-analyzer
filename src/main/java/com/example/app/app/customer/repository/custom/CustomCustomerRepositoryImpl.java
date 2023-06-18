package com.example.app.app.customer.repository.custom;

import com.example.app.app.customer.domain.dto.CustomerDetailsDto;
import com.example.app.app.customer.domain.entity.QCustomerEntity;
import com.example.app.app.location.domain.entity.QAddressEntity;
import com.example.app.app.location.domain.entity.QCityEntity;
import com.example.app.app.payment.domain.dto.PaymentDto;
import com.example.app.app.payment.domain.entity.QPaymentEntity;
import com.example.app.app.rental.domain.dto.RentalDto;
import com.example.app.app.rental.domain.entity.QRentalEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.app.common.repository.ExpressionUtils.filterBetween;
import static com.example.app.common.repository.ExpressionUtils.filterEquals;

@Repository
@RequiredArgsConstructor
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CustomerDetailsDto.CustomerDetails> findAllCustomerDetailsList() {
        var query = findCustomerDetail(null);
        return query.fetch();
    }

    @Override
    public Optional<CustomerDetailsDto.CustomerDetails> findCustomerDetailsById(Integer customerId) {
        var query = findCustomerDetail(customerId);
        return Optional.of(query.fetchFirst());
    }

    private JPAQuery<CustomerDetailsDto.CustomerDetails> findCustomerDetail(Integer id) {
        var customer = QCustomerEntity.customerEntity;
        var address = QAddressEntity.addressEntity;
        var city = QCityEntity.cityEntity;
        return jpaQueryFactory
                .select(Projections.constructor(CustomerDetailsDto.CustomerDetails.class,
                        customer.customerId.as(CustomerDetailsDto.CustomerDetails.Fields.id),
                        Expressions.asString(customer.fullName.firstName).concat(" ")
                                .concat(customer.fullName.lastName).as(CustomerDetailsDto.CustomerDetails.Fields.name),
                        address.address.as(CustomerDetailsDto.CustomerDetails.Fields.address),
                        address.postalCode.as(CustomerDetailsDto.CustomerDetails.Fields.zipCode),
                        address.phone.as(CustomerDetailsDto.CustomerDetails.Fields.phone),
                        city.city.as(CustomerDetailsDto.CustomerDetails.Fields.city),
                        city.countryId.as(CustomerDetailsDto.CustomerDetails.Fields.country),
                        Expressions.stringTemplate("IF({0}, 'active', '')", customer.active)
                                .as(CustomerDetailsDto.CustomerDetails.Fields.notes),
                        customer.storeId.as(CustomerDetailsDto.CustomerDetails.Fields.sid)))
                .from(customer)
                .join(address).on(customer.addressId.eq(address.addressId))
                .join(city).on(address.cityId.eq(city.cityId))
                .where(filterEquals(customer.customerId, id));
    }

    @Override
    public List<PaymentDto.Payment> findAllCustomerPaymentListById(Integer customerId) {
        var query = findCustomerPayments(customerId, null, null);
        return query.fetch();
    }

    @Override
    public List<PaymentDto.Payment> findAllCustomerPaymentListByIdWithFilter(Integer customerId, LocalDate startDate, LocalDate endDate) {
        var query = findCustomerPayments(customerId, startDate, endDate);
        return query.fetch();
    }

    private JPAQuery<PaymentDto.Payment> findCustomerPayments(Integer customerId, LocalDate startDate, LocalDate endDate) {
        var customer = QCustomerEntity.customerEntity;
        var payment = QPaymentEntity.paymentEntity;
        return jpaQueryFactory
                .select(Projections.constructor(PaymentDto.Payment.class,
                        payment.paymentId.as(PaymentDto.Payment.Fields.paymentId),
                        payment.customerId.as(PaymentDto.Payment.Fields.customerId),
                        payment.staffId.as(PaymentDto.Payment.Fields.staffId),
                        payment.rentalId.as(PaymentDto.Payment.Fields.rentalId),
                        payment.amount.as(PaymentDto.Payment.Fields.amount),
                        payment.paymentDate.as(PaymentDto.Payment.Fields.paymentDate),
                        payment.lastUpdate.as(PaymentDto.Payment.Fields.lastUpdate)))
                .from(payment)
                .join(customer).on(payment.customerId.eq(customer.customerId))
                .where(customer.customerId.eq(customerId))
                .where(filterBetween(payment.paymentDate,
                        Optional.ofNullable(startDate).map(LocalDate::atStartOfDay).orElse(null),
                        Optional.ofNullable(endDate).map(LocalDate::atStartOfDay).orElse(null)));
    }

    @Override
    public List<RentalDto.Rental> findAllCustomerRentalListById(Integer customerId) {
        var query = findCustomerRentals(customerId, null, null, null);
        return query.fetch();
    }

    @Override
    public List<RentalDto.Rental> findAllCustomerRentalListByIdWithFilter(Integer customerId, String status, LocalDate startDate, LocalDate endDate) {
        var query = findCustomerRentals(customerId, status, startDate, endDate);
        return query.fetch();
    }

    private JPAQuery<RentalDto.Rental> findCustomerRentals(Integer customerId, String status, LocalDate startDate, LocalDate endDate) {
        var customer = QCustomerEntity.customerEntity;
        var rental = QRentalEntity.rentalEntity;
        return jpaQueryFactory
                .select(Projections.constructor(RentalDto.Rental.class,
                        rental.rentalId.as(RentalDto.Rental.Fields.rentalId),
                        rental.customerId.as(RentalDto.Rental.Fields.customerId),
                        rental.staffId.as(RentalDto.Rental.Fields.staffId),
                        rental.rentalId.as(RentalDto.Rental.Fields.rentalId),
                        rental.rentalDate.as(RentalDto.Rental.Fields.rentalDate),
                        rental.returnDate.as(RentalDto.Rental.Fields.returnDate),
                        rental.lastUpdate.as(RentalDto.Rental.Fields.lastUpdate)))
                .from(rental)
                .join(customer).on(rental.customerId.eq(customer.customerId))
                .where(customer.customerId.eq(customerId))
                .where(filterBetween(rental.rentalDate,
                        Optional.ofNullable(startDate).map(LocalDate::atStartOfDay).orElse(null),
                        Optional.ofNullable(endDate).map(LocalDate::atStartOfDay).orElse(null)));
    }
}
